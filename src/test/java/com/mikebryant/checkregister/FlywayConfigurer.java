package com.mikebryant.checkregister;

import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class FlywayConfigurer {

    @Bean
    @Profile("test")
    public FlywayMigrationStrategy cleanMigrateStrategy() {
        FlywayMigrationStrategy strategy = flyway -> {
            flyway.clean();
            flyway.migrate();
        };

        return strategy;
    }

}
