package com.allane.leasing;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@TestConfiguration
@Testcontainers
public class TestContainerConfiguration {
    @Container
    private static final MySQLContainer<?> MYSQL_CONTAINER = new MySQLContainer<>("mysql:latest")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    @Bean
    @Primary
    public DataSource dataSource() {
        return DataSourceBuilder
                .create()
                .url(MYSQL_CONTAINER.getJdbcUrl())
                .username(MYSQL_CONTAINER.getUsername())
                .password(MYSQL_CONTAINER.getPassword())
                .build();
    }
}
