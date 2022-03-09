package com.postal.service.config;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

public class TestConfigurations {
    @TestConfiguration
    public static class DatabaseConfig {
        @Bean
        public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
            ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
            initializer.setConnectionFactory(connectionFactory);
            /*R2dbcEntityTemplate template = new R2dbcEntityTemplate(connectionFactory);

            String query = "CREATE TABLE public.postal_details (\n" +
                    "                                       id bigserial NOT NULL,\n" +
                    "                                       city_name text NULL,\n" +
                    "                                       area_name text NULL,\n" +
                    "                                       postal_code text NULL,\n" +
                    "                                       CONSTRAINT postal_details_pkey PRIMARY KEY (id)\n" +
                    ");\n" +
                    "CREATE INDEX postal_details_city_name_idx ON public.postal_details (city_name,area_name,postal_code);";
            template.getDatabaseClient().sql(query).fetch().rowsUpdated().block();*/
            CompositeDatabasePopulator populator = new CompositeDatabasePopulator();
            populator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));
            //populator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("data.sql")));
            initializer.setDatabasePopulator(populator);

            return initializer;
        }
    }
}
