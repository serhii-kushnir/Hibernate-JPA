package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.*;
import org.apache.log4j.Logger;
import org.example.data.Apartment;
import org.example.data.House;
import org.example.data.MemberOsbb;
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
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("OSBB");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);

        Root<Resident> residentRoot = criteriaQuery.from(Resident.class);
        Join<Resident, MemberOsbb> memberOsbbJoin = residentRoot.join("memberOsbb");
        Join<Resident, Apartment> apartmentJoin = residentRoot.join("apartment");
        Join<Apartment, House> houseJoin = apartmentJoin.join("house");

        Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
        Root<Resident> subqueryRoot = subquery.from(Resident.class);

        subquery.select(criteriaBuilder.count(subqueryRoot))
                .where(criteriaBuilder.equal(subqueryRoot.get("memberOsbb").get("id"), memberOsbbJoin.get("id")));

        criteriaQuery.multiselect(
                        memberOsbbJoin.get("surname"),
                        memberOsbbJoin.get("name"),
                        memberOsbbJoin.get("patronymic"),
                        memberOsbbJoin.get("email"),
                        houseJoin.get("number"),
                        houseJoin.get("address"),
                        apartmentJoin.get("number"),
                        apartmentJoin.get("square")
                )
                .where(
                        criteriaBuilder.and(
                                criteriaBuilder.lessThan(subquery, 2L),
                                criteriaBuilder.isFalse(residentRoot.get("entryRightsTerritory"))
                        )
                );

        List<Object[]> resultList = entityManager.createQuery(criteriaQuery).getResultList();

        for (Object[] result : resultList) {
            String surname = (String) result[0];
            String name = (String) result[1];
            String patronymic = (String) result[2];
            String email = (String) result[3];
            Integer houseNumber = (Integer) result[4];
            String houseAddress = (String) result[5];
            Integer apartmentNumber = (Integer) result[6];
            float apartmentSquare = (float) result[7];

            System.out.println("Прізвище: " + surname);
            System.out.println("Ім'я: " + name);
            System.out.println("По батькові: " + patronymic);
            System.out.println("Електронна пошта: " + email);
            System.out.println("Номер будинку: " + houseNumber);
            System.out.println("Адреса будинку: " + houseAddress);
            System.out.println("Номер квартири: " + apartmentNumber);
            System.out.println("Площа квартири: " + apartmentSquare);
            System.out.println("-----------------------");
        }

        entityManager.close();
        entityManagerFactory.close();
    }
}

