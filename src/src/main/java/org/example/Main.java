package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
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
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("OSBB");
        EntityManager em = emf.createEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Resident> cq = cb.createQuery(Resident.class);

        Root<Resident> root = cq.from(Resident.class);
        Join<Resident, Apartment> apartmentJoin = root.join("apartment");
        Join<Apartment, House> houseJoin = apartmentJoin.join("house");
        Join<Resident, MemberOsbb> memberOsbbJoin = root.join("memberOsbb");

        Predicate entryRightsTerritoryPredicate = cb.isFalse(root.get("entryRightsTerritory"));
        Predicate residentCountPredicate = cb.lessThan(cb.size(memberOsbbJoin.get("residents")), 2);

        cq.multiselect(
                memberOsbbJoin.get("surname"),
                memberOsbbJoin.get("name"),
                memberOsbbJoin.get("patronymic"),
                memberOsbbJoin.get("email"),
                houseJoin.get("number"),
                houseJoin.get("address"),
                apartmentJoin.get("number"),
                apartmentJoin.get("square")
        );

        cq.where(cb.and(entryRightsTerritoryPredicate, residentCountPredicate));

        TypedQuery<Resident> query = em.createQuery(cq);
        List<Resident> residents = query.getResultList();

        for (Resident resident : residents) {
            System.out.println("______________________");
            System.out.println("ПІБ: " + resident.getMemberOsbb().getSurname() + " " +
                    resident.getMemberOsbb().getName() + " " +
                    resident.getMemberOsbb().getPatronymic());
            System.out.println("Email: " + resident.getMemberOsbb().getEmail());
            System.out.println("Номер будинку: " + resident.getApartment().getHouse().getNumber());
            System.out.println("Адреса будинку: " + resident.getApartment().getHouse().getAddress());
            System.out.println("Номер квартири: " + resident.getApartment().getNumber());
            System.out.println("Площа квартири: " + resident.getApartment().getSquare());
        }

        em.close();
        emf.close();
    }
}