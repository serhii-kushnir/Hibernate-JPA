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

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

final class HibernateService {
    private static final Logger LOGGER = Logger.getLogger(HibernateService.class);
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final int FOUR = 4;
    private static final int FIVE = 5;
    private static final int SIX = 6;
    private static final int SEVEN = 7;
    private static final int EIGHT = 8;
    private static final String LINE = """
         |----------|------|-------------|--------------|------------------|---------|---------|----------|---------|
         """;
    private static final String NAMES_COLUMNS = """
         | Прізвище | Ім'я | По батькові | Моб.Телефон  | Електрона пошта  | Вулиця  | Будинок | Квартира | Площадь |
         """;

    public void printOwnersToConsole() {
        List<Object[]> owners = getOwnersWithNotEnterTheTerritory();

        capPrinting();

        for (Object[] result : owners) {
            System.out.println(formatOwnersColumns(result));
        }

        System.out.print(LINE);

        LOGGER.trace("The result is saved in the console");
    }

    public void printOwnersToFile(final String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            List<Object[]> owners = getOwnersWithNotEnterTheTerritory();

            capPrinting(writer);

            for (Object[] result : owners) {
                writer.write(formatOwnersColumns(result));
                writer.newLine();
            }

            writer.write(LINE);

            LOGGER.trace("The result is saved in the file: " + filePath);
        } catch (IOException e) {
            LOGGER.fatal("Failed to write to file: " + e.getMessage());
        }
    }

    private static void capPrinting() {
        System.out.print(LINE);
        System.out.print(NAMES_COLUMNS);
        System.out.print(LINE);
    }

    private static void capPrinting(final BufferedWriter writer) throws IOException {
        writer.write(LINE);
        writer.write(NAMES_COLUMNS);
        writer.write(LINE);
    }

    private List<Object[]> getOwnersWithNotEnterTheTerritory() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("OSBB");
        EntityManager em = emf.createEntityManager();

        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);

            Root<Resident> residentRoot = criteriaQuery.from(Resident.class);
            Root<MemberOsbb> memberOsbbRoot = criteriaQuery.from(MemberOsbb.class);
            Root<Apartment> apartmentRoot = criteriaQuery.from(Apartment.class);
            Root<House> houseRoot = criteriaQuery.from(House.class);

            buildCriteriaQuery(criteriaQuery, criteriaBuilder, memberOsbbRoot, houseRoot, apartmentRoot, residentRoot);

            return em.createQuery(criteriaQuery).getResultList();
        } finally {
            closeEntityManager(emf, em);
        }
    }

    private void buildCriteriaQuery(final CriteriaQuery<Object[]> criteriaQuery,
                                    final CriteriaBuilder criteriaBuilder,
                                    final Root<MemberOsbb> memberOsbbRoot,
                                    final Root<House> houseRoot,
                                    final Root<Apartment> apartmentRoot,
                                    final Root<Resident> residentRoot) {
        Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
        Root<Resident> subqueryResidentRoot = subquery.from(Resident.class);

        subquery.select(criteriaBuilder.count(subqueryResidentRoot))
                .where(criteriaBuilder.equal(subqueryResidentRoot.get("memberOsbb").get("id"), memberOsbbRoot.get("id")));

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
    }

    private String formatOwnersColumns(final Object[] objects) {
        return "|   "
                + objects[ZERO]
                + "   | "
                + objects[ONE]
                + " |     "
                + objects[TWO]
                + "    |  "
                + objects[THREE]
                + "  | "
                + objects[FOUR]
                + "   | "
                + objects[SIX]
                + " |    "
                + objects[FIVE]
                + "    |    "
                + objects[SEVEN]
                + "   |   "
                + objects[EIGHT]
                + "  |   ";
    }

    private void closeEntityManager(final EntityManagerFactory entityManagerFactory, final EntityManager entityManager) {
        entityManagerFactory.close();
        entityManager.close();
    }
}
