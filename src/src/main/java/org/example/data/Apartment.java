package org.example.data;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "apartments")
public final class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int number;
    private float square;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "houses_id")
    private House house;

    public Apartment() {
    }

    public Apartment(final int id, final int number, final float square, final House house, final List<Resident> residents, final List<Ownership> ownerships) {
        this.id = id;
        this.number = number;
        this.square = square;
        this.house = house;
        this.residents = residents;
        this.ownerships = ownerships;
    }

    public void setHouse(final House house) {
        this.house = house;
    }

    @OneToMany(mappedBy = "apartment")
    private List<Resident> residents;

    @OneToMany(mappedBy = "apartment")
    private List<Ownership> ownerships;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(final int number) {
        this.number = number;
    }

    public float getSquare() {
        return square;
    }

    public void setSquare(final float square) {
        this.square = square;
    }

    public House getHouse() {
        return house;
    }

    public List<Resident> getResidents() {
        return residents;
    }

    public void setResidents(final List<Resident> residents) {
        this.residents = residents;
    }

    public List<Ownership> getOwnerships() {
        return ownerships;
    }

    public void setOwnerships(final List<Ownership> ownerships) {
        this.ownerships = ownerships;
    }

    @Override
    public String toString() {
        return "Apartment{"
                + "id=" + id
                + ", number="
                + number
                + ", square="
                + square
                + ", house="
                + house
                + ", residents="
                + residents
                + ", ownerships="
                + ownerships
                + '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Apartment apartment = (Apartment) o;

        return id == apartment.id
                && number == apartment.number
                && Float.compare(square, apartment.square) == 0
                && Objects.equals(house, apartment.house)
                && Objects.equals(residents, apartment.residents)
                && Objects.equals(ownerships, apartment.ownerships);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, square, house, residents, ownerships);
    }
}
