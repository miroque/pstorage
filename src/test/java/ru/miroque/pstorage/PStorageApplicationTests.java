package ru.miroque.pstorage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
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
    private RepositoryPersonality repositoryPersonality;

    @DynamicPropertySource
    static void datasourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    void testOfConfigurationDatasourceConnection(@Autowired Environment env) {
        System.out.println("#test - spring.datasource.url=" + env.getProperty("spring.datasource.url"));
    }

//    @Disabled
    @Test
    void testCreate20OfPersonalities() {
        IntStream.rangeClosed(1, 20).mapToObj(i -> new Personality()).forEach(item -> {
            item.setNid(UUID.randomUUID());
            item.setBirthDate(LocalDate.now());
            repositoryPersonality.save(item);
        });
        assertEquals(20,repositoryPersonality.findAll().size());
    }

}
