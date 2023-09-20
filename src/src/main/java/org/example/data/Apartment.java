package org.example.data;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "apartments")
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int number;
    private float square;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "house_id")
    private House house;

    public Apartment(int id, int number, float square, House house, List<Resident> residents, List<Ownership> ownerships) {
        this.id = id;
        this.number = number;
        this.square = square;
        this.house = house;
        this.residents = residents;
        this.ownerships = ownerships;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    @OneToMany(mappedBy = "apartment")
    private List<Resident> residents;

    @OneToMany(mappedBy = "apartment")
    private List<Ownership> ownerships;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public float getSquare() {
        return square;
    }

    public void setSquare(float square) {
        this.square = square;
    }

    public House getHouse() {
        return house;
    }

    public List<Resident> getResidents() {
        return residents;
    }

    public void setResidents(List<Resident> residents) {
        this.residents = residents;
    }

    public List<Ownership> getOwnerships() {
        return ownerships;
    }

    public void setOwnerships(List<Ownership> ownerships) {
        this.ownerships = ownerships;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Apartment apartment = (Apartment) o;
        return id == apartment.id && number == apartment.number && Float.compare(square, apartment.square) == 0 && Objects.equals(house, apartment.house) && Objects.equals(residents, apartment.residents) && Objects.equals(ownerships, apartment.ownerships);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, square, house, residents, ownerships);
    }
}
