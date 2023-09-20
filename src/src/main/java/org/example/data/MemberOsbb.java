package org.example.data;



import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "members_osbb")
public class MemberOsbb {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "phone_number")
    private int phoneNumber;
    private String surname;
    private String name;
    private String patronymic;
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "memberOsbb", cascade = CascadeType.ALL)
    private List<Resident> residents;

    @OneToMany(mappedBy = "memberOsbb", cascade = CascadeType.ALL)
    private List<Ownership> ownerships;

    public MemberOsbb() {
    }

    public MemberOsbb(int id, int phoneNumber, String surname, String name, String patronymic, String email, Role role, List<Resident> residents, List<Ownership> ownerships) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.email = email;
        this.role = role;
        this.residents = residents;
        this.ownerships = ownerships;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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
    public String toString() {
        return "MemberOsbb{" +
                "id=" + id +
                ", phoneNumber=" + phoneNumber +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", residents=" + residents +
                ", ownerships=" + ownerships +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberOsbb that = (MemberOsbb) o;
        return id == that.id && phoneNumber == that.phoneNumber && Objects.equals(surname, that.surname) && Objects.equals(name, that.name) && Objects.equals(patronymic, that.patronymic) && Objects.equals(email, that.email) && role == that.role && Objects.equals(residents, that.residents) && Objects.equals(ownerships, that.ownerships);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, phoneNumber, surname, name, patronymic, email, role, residents, ownerships);
    }
}
