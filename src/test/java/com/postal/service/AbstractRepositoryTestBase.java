package com.postal.service;

import io.r2dbc.spi.ConnectionFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@RunWith(SpringRunner.class)
@DataR2dbcTest
@Testcontainers
public abstract class AbstractRepositoryTestBase {
    @Container
    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:14.2");

    @Autowired
    private ConnectionFactory connectionFactory;

    /*@After
    public void after() {
        String deleteQuery = "DELETE FROM " + getTable();
        R2dbcEntityTemplate template = new R2dbcEntityTemplate(connectionFactory);
        template.getDatabaseClient().sql(deleteQuery).fetch().rowsUpdated().block();
    }

    protected abstract String getTable();*/

    @DynamicPropertySource
    static void resgiterDynamicProperties(DynamicPropertyRegistry registry) {
        postgreSQLContainer.start();
        registry.add("spring.r2dbc.url", () -> "r2dbc:postgresql://"
                + postgreSQLContainer.getHost() + ":" + postgreSQLContainer.getFirstMappedPort()
                + "/" + postgreSQLContainer.getDatabaseName());
        registry.add("spring.r2dbc.username", () -> postgreSQLContainer.getUsername());
        registry.add("spring.r2dbc.password", () -> postgreSQLContainer.getPassword());
    }
}
