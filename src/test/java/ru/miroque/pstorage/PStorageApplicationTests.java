package ru.miroque.pstorage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

@SpringBootTest
@Testcontainers
class PStorageApplicationTests {
    @Container
    public static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:12.4-alpine");

    @Autowired
    private RepositoryPersonality rPersonality;

    @DynamicPropertySource
    static void datasourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @DisplayName("Проверка конфигурации докера")
    @Disabled
    @Test
    void testOfConfigurationDatasourceConnection(@Autowired Environment env) {
        System.out.println("#test - spring.datasource.url=" + env.getProperty("spring.datasource.url"));
    }

//    @Disabled
    @DisplayName("Создание 20 записей")
    @Test
    void testCreate20OfPersonalities() {
        IntStream.rangeClosed(1, 20).mapToObj(i -> new Personality()).forEach(item -> {
            item.setNid(UUID.randomUUID());
            item.setBirthDate(LocalDate.now());
            rPersonality.save(item);
        });
        assertEquals(21, rPersonality.findAll().size());
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
        Personality item = new Personality(UUID.fromString("00000000-0000-0000-0000-000000000002"), LocalDate.now());
        item = rPersonality.save(item);
        assertNotNull(item.getId());
        assertEquals(2, rPersonality.findAll().size());
    }

    @DisplayName("Вернуть пользователя по ай-ди")
    @Test
    void findItem() {
        Personality item = rPersonality.findByNid(UUID.fromString("00000000-0000-0000-0000-000000000001"));
        assertEquals(UUID.fromString("00000000-0000-0000-0000-000000000001"), item.getNid());
        assertEquals(1, rPersonality.findAll().size());
    }

    @DisplayName("Удалить пользователя")
    @Test
    void deleteItem() {
//        Personality item = rPersonality.findByNid(UUID.fromString("00000000-0000-0000-0000-000000000001"));
        Personality item = new Personality(UUID.fromString("00000000-0000-0000-0000-000000000002"), LocalDate.now());
        item = rPersonality.save(item);
        rPersonality.delete(item);
        rPersonality.flush();
        assertEquals(1, rPersonality.findAll().size());
    }
}
