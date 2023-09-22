package org.example.data;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "houses")
public final class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String address;
    private int number;

    @OneToMany(mappedBy = "house", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Apartment> apartments = new ArrayList<>();

    public House() {
    }

    public House(final int id,
                 final String address,
                 final int number,
                 final List<Apartment> apartments) {
        this.id = id;
        this.address = address;
        this.number = number;
        this.apartments = apartments;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(final int number) {
        this.number = number;
    }

    public List<Apartment> getApartments() {
        return apartments;
    }

    public void setApartments(final List<Apartment> apartments) {
        this.apartments = apartments;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        House house = (House) o;

        return id == house.id
                && number == house.number
                && Objects.equals(address, house.address)
                && Objects.equals(apartments, house.apartments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, address, number, apartments);
    }

    @Override
    public String toString() {
        return "House{"
                + "id="
                + id
                + ", address='"
                + address
                + '\''
                + ", number=" + number
                + ", apartments="
                + apartments
                + '}';
    }
}
