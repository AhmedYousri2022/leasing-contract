package com.allane.leasing;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public class DatabaseContainer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final String DATABASE_NAME = "db_test";

    private static final List<String> DB_SETTINGS = new ArrayList<>(List.of("--character-set-server=utf8mb4"));

    private static final DockerImageName DOCKER_IMAGE = DockerImageName.parse(
            org.testcontainers.utility.TestcontainersConfiguration.getInstance()
                    .getEnvVarOrProperty("datasource.image", "mysql:8.0"));

    private static org.testcontainers.containers.JdbcDatabaseContainer<?> DB_CONTAINER;

    static {
        DB_CONTAINER = new org.testcontainers.containers.MySQLContainer<>(DOCKER_IMAGE);
        DB_CONTAINER.setCommandParts(DB_SETTINGS.toArray(new String[0]));
        DB_CONTAINER.withDatabaseName(DATABASE_NAME);
        DB_CONTAINER.start();
    }

    @Override
    public void initialize(@NotNull ConfigurableApplicationContext configurableApplicationContext) {
        TestPropertyValues.of(
                        "spring.datasource.url=" + DB_CONTAINER.getJdbcUrl(),
                        "spring.datasource.username=" + DB_CONTAINER.getUsername(),
                        "spring.datasource.password=" + DB_CONTAINER.getPassword(),
                        "application.datasource.enabled=false",
                        "spring.datasource.hikari.connectionTestQuery=SELECT 1")
                .applyTo(configurableApplicationContext.getEnvironment());
    }
}
