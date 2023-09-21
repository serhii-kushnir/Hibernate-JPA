package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.apache.log4j.Logger;

final class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class);
    private static final HibernateService service = new HibernateService();
    private Main() {
    }

    public static void main(final String[] args) throws RuntimeException {
        new Config();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("OSBB");
        EntityManager em = emf.createEntityManager();

        service.getOwnerWithNotEnteTheTerritory(emf, em);
    }


}

