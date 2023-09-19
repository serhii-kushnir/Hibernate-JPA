package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.apache.log4j.Logger;
import org.example.data.Resident;



import java.util.List;

final class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class);

    private Main() {
    }

    public static void main(final String[] args) throws RuntimeException {
        LOGGER.info("Started program");
        new Config();

        sql();
    }

    private static void sql() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("OSBB");
        EntityManager em = emf.createEntityManager();

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Resident> residentRoot = criteriaQuery.from(Resident.class);

        criteriaQuery.multiselect(
                residentRoot.get("memberOsbb").get("surname").alias("Прізвище"),
                residentRoot.get("memberOsbb").get("name").alias("Ім'я"),
                residentRoot.get("memberOsbb").get("patronymic").alias("По батькові"),
                residentRoot.get("memberOsbb").get("phoneNumber").alias("Телефон"),
                residentRoot.get("memberOsbb").get("email").alias("Електрона пошта"),

                residentRoot.get("apartment").get("house").get("address").alias("Вулиця"),
                residentRoot.get("apartment").get("house").get("number").alias("Будинок"),

                residentRoot.get("apartment").get("number").alias("Квартира"),
                residentRoot.get("apartment").get("square").alias("Площадь")
        );

        Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
        Root<Resident> subqueryRoot = subquery.from(Resident.class);
        subquery.select(criteriaBuilder.count(subqueryRoot.get("id")));
        subquery.where(criteriaBuilder.equal(subqueryRoot.get("memberOsbb"), residentRoot.get("memberOsbb")));

        Predicate condition = criteriaBuilder.and(
                criteriaBuilder.lessThan(subquery, 2L),
                criteriaBuilder.isFalse(residentRoot.get("entryRightsTerritory"))
        );

        criteriaQuery.where(condition);
        criteriaQuery.orderBy(criteriaBuilder.asc(residentRoot.get("memberOsbb").get("id")));

        TypedQuery<Object[]> typedQuery = em.createQuery(criteriaQuery);
        List<Object[]> results = typedQuery.getResultList();

        em.close();
        emf.close();
    }
}