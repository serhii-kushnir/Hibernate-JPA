package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.apache.log4j.Logger;
import org.example.data.Apartment;
import org.example.data.House;
import org.example.data.MemberOsbb;
import org.example.data.Resident;

import java.util.List;

public class HibernateService {

    private static final Logger LOGGER = Logger.getLogger(Main.class);

    private static final String LINE = """
         |----------|------|-------------|--------------|------------------|---------|---------|----------|---------|
         """;
    private static final String NAMES_COLUMNS = """
         | Прізвище | Ім'я | По батькові | Моб.Телефон  | Електрона пошта  | Вулиця  | Будинок | Квартира | Площадь |
         |----------|------|-------------|--------------|------------------|---------|---------|----------|---------|
         """;

    public void getOwnerWithNotEnteTheTerritory(final EntityManagerFactory entityManagerFactory, final EntityManager entityManager) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);

        Root<Resident> residentRoot = criteriaQuery.from(Resident.class);
        Root<MemberOsbb> memberOsbbRoot = criteriaQuery.from(MemberOsbb.class);
        Root<Apartment> apartmentRoot = criteriaQuery.from(Apartment.class);
        Root<House> houseRoot = criteriaQuery.from(House.class);

        Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
        Root<Resident> subqueryRoot = subquery.from(Resident.class);

        subquery.select(criteriaBuilder.count(subqueryRoot))
                .where(criteriaBuilder.equal(subqueryRoot.get("memberOsbb").get("id"), memberOsbbRoot.get("id")));

        criteriaQuery.multiselect(
                        memberOsbbRoot.get("surname"),
                        memberOsbbRoot.get("name"),
                        memberOsbbRoot.get("patronymic"),
                        memberOsbbRoot.get("phoneNumber"),
                        memberOsbbRoot.get("email"),
                        houseRoot.get("number"),
                        houseRoot.get("address"),
                        apartmentRoot.get("number"),
                        apartmentRoot.get("square")
                )
                .where(
                        criteriaBuilder.and(
                                criteriaBuilder.lessThan(subquery, 2L),
                                criteriaBuilder.isFalse(residentRoot.get("entryRightsTerritory")),
                                criteriaBuilder.equal(residentRoot.get("memberOsbb").get("id"), memberOsbbRoot.get("id")),
                                criteriaBuilder.equal(residentRoot.get("apartment").get("id"), apartmentRoot.get("id")),
                                criteriaBuilder.equal(apartmentRoot.get("house").get("id"), houseRoot.get("id"))
                        )
                );

        List<Object[]> resultList = entityManager.createQuery(criteriaQuery).getResultList();

        printAllOwnersWithNotEnteTheTerritoryToConsole(resultList);

        closable(entityManagerFactory, entityManager);
    }

    private void printAllOwnersWithNotEnteTheTerritoryToConsole(final List<Object[]> resultList) {
        System.out.print(LINE);
        System.out.print(NAMES_COLUMNS);

        for (Object[] result : resultList) {
            System.out.println(formatOwnersColunms(result));
        }

        System.out.print(LINE);
    }

    private String formatOwnersColunms(final Object[] objects) {
        return "|   "
                + objects[0]
                + "   | "
                + objects[1]
                + " |     "
                + objects[2]
                + "    |  "
                + objects[3]
                + "  | "
                + objects[4]
                + "   | "
                + objects[6]
                + " |    "
                + objects[5]
                + "    |    "
                + objects[7]
                + "   |   "
                + objects[8]
                + "  |   ";
    }

    private void closable(final EntityManagerFactory entityManagerFactory, final EntityManager entityManager) {
        entityManagerFactory.close();
        entityManager.close();
    }

}
