package org.example;

import org.apache.log4j.Logger;

final class Flyway {
    private static final Logger LOGGER = Logger.getLogger(Flyway.class);
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/osbb";
    private static final String BD_USERNAME = "root";
    private static final String BD_PASSWORD = "root";

    Flyway() {
        this.init();
    }

    private void init() {
        LOGGER.trace("Started Flyway");

        org.flywaydb.core.Flyway.configure()
                .dataSource(JDBC_URL, BD_USERNAME, BD_PASSWORD)
                .locations("classpath:flyway")
                .baselineOnMigrate(true)
                .load()
                .migrate();

        LOGGER.trace("Flyway installed: Ok");
    }
}