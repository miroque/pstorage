package ru.miroque.pstorage;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.miroque.pstorage.entity.Personality;
import ru.miroque.pstorage.entity.RepositoryPersonality;

import java.time.LocalDate;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
class PStorageApplicationTests {
    @Container
    public static final PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:12.4-alpine");

    @Autowired
    private RepositoryPersonality rPersonality;

    @DynamicPropertySource
    static void datasourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @DisplayName("Проверка конфигурации докера")
    @Test
    void testOfConfigurationDatasourceConnection(@Autowired Environment env) {
        System.out.println("#test - spring.datasource.url=" + env.getProperty("spring.datasource.url"));
    }

    @BeforeEach
    void before() {
        Personality item = new Personality(UUID.fromString("00000000-0000-0000-0000-000000000001"), LocalDate.now());
        rPersonality.save(item);
    }

    @AfterEach
    void after() {
        rPersonality.deleteAll();
    }

    @DisplayName("Создать пользователя")
    @Test
    void createItem() {
        UUID uuid = UUID.fromString("00000000-0000-0000-0000-000000000002");
        Personality item = new Personality(uuid, LocalDate.now());
        item = rPersonality.save(item);
        assertNotNull(item.getId());
        assertEquals(uuid, item.getNid());
    }

    @DisplayName("Вернуть пользователя по ай-ди")
    @Test
    void findItem() {
        UUID uuid = UUID.fromString("00000000-0000-0000-0000-000000000001");
        assertEquals(uuid, rPersonality.findByNid(uuid).get().getNid());
    }

    @DisplayName("Удалить пользователя")
    @Test
    void deleteItem() {
        UUID uuid = UUID.fromString("00000000-0000-0000-0000-000000000002");
        Personality item = new Personality(uuid, LocalDate.now());
        item = rPersonality.save(item);
        assertNotNull(item.getId());
        assertEquals(uuid, item.getNid());
        rPersonality.delete(item);
        assertFalse(rPersonality.findByNid(uuid).isPresent());
    }
}
