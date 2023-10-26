package com.prueba.homeworkapp.containers;

import org.testcontainers.containers.PostgreSQLContainer;

public class MockPostgreSqlContainer extends PostgreSQLContainer<MockPostgreSqlContainer> {
    private static final String IMAGE_VERSION = "postgres:14.1-alpine";

    private static final String POSTGRES_DB_NAME = "homework-test-db";
    private static final String POSTGRES_DB_USER = "postgres";
    private static final String POSTGRES_DB_PASSWORD = "password";

    private static MockPostgreSqlContainer container;

    private MockPostgreSqlContainer() {
        super(IMAGE_VERSION);
    }

    public static MockPostgreSqlContainer getInstance() {
        if (container == null) {
            container = new MockPostgreSqlContainer()
                    .withDatabaseName(POSTGRES_DB_NAME)
                    .withUsername(POSTGRES_DB_USER)
                    .withPassword(POSTGRES_DB_PASSWORD);
        }
        return container;
    }

    @Override
    public void stop() {
        // Do nothing, JVM handles shut down
    }
}
