package org.example.data.repository;

import java.util.Objects;

public final class MemberOsbbRepository {
    private String surname;
    private String name;
    private String patronymic;
    private int phoneNumber;
    private String email;
    private int houseNumber;
    private String houseAddress;
    private int apartmentNumber;
    private float apartmentSquare;

    public String getSurname() {
        return surname;
    }

    public void setSurname(final String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(final String patronymic) {
        this.patronymic = patronymic;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(final int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(final int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getHouseAddress() {
        return houseAddress;
    }

    public void setHouseAddress(final String houseAddress) {
        this.houseAddress = houseAddress;
    }

    public int getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(final int apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public float getApartmentSquare() {
        return apartmentSquare;
    }

    public void setApartmentSquare(final float apartmentSquare) {
        this.apartmentSquare = apartmentSquare;
    }

    @Override
    public String toString() {
        return "MemberOsbbRepository{"
                + "surname='" + surname
                + '\''
                + ", name='"
                + name
                + '\''
                + ", patronymic='"
                + patronymic
                + '\''
                + ", phoneNumber="
                + phoneNumber
                + ", email='"
                + email
                + '\''
                + ", houseNumber="
                + houseNumber
                + ", houseAddress='"
                + houseAddress
                + '\''
                + ", apartmentNumber="
                + apartmentNumber
                + ", apartmentSquare="
                + apartmentSquare
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

        MemberOsbbRepository that = (MemberOsbbRepository) o;

        return phoneNumber == that.phoneNumber
                && houseNumber == that.houseNumber
                && apartmentNumber == that.apartmentNumber
                && Float.compare(apartmentSquare, that.apartmentSquare) == 0
                && Objects.equals(surname, that.surname)
                && Objects.equals(name, that.name)
                && Objects.equals(patronymic, that.patronymic)
                && Objects.equals(email, that.email)
                && Objects.equals(houseAddress, that.houseAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(surname, name, patronymic, phoneNumber, email, houseNumber, houseAddress, apartmentNumber, apartmentSquare);
    }
}
