package org.example;

import org.apache.log4j.Logger;

final class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class);
    private static final HibernateService SERVICE = new HibernateService();
    private Main() {
    }

    public static void main(final String[] args) throws RuntimeException {
        LOGGER.trace("Started program");

        new Flyway();
        SERVICE.printOwnersToFile("OwnerWithNotEnteTheTerritory.txt");
        SERVICE.printOwnersToConsole();

        LOGGER.trace("The program is completed");
    }
}

