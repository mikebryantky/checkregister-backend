package com.mikebryant.checkregister;

import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;

@Profile("test")
@Configuration
public class FlywayConfigurer {

    @Bean
    public FlywayMigrationStrategy cleanMigrateStrategy() {
        FlywayMigrationStrategy strategy = flyway -> {
            flyway.clean();
            flyway.migrate();
        };

        return strategy;
    }

}
