package com.prueba.flyway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Import;

import static org.springframework.boot.WebApplicationType.NONE;

/**
 * Utility to run flyway migration without starting service
 */
@SpringBootConfiguration
@Import({DataSourceAutoConfiguration.class, FlywayAutoConfiguration.class})
public class FlywayMigrationRunner {

    public static void main(String[] args) {

        final SpringApplication application =
                new SpringApplicationBuilder(FlywayMigrationRunner.class)
                        .web(NONE).build();

        application.run(args);
    }
}
