package org.example.data;


import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "ownerships")
@IdClass(Ownership.class)
public class Ownership {
    @Id
    @ManyToOne
    @JoinColumn(name = "members_osbb_id")
    private MemberOsbb memberOsbb;

    @Id
    @ManyToOne
    @JoinColumn(name = "apartments_id")
    private Apartment apartment;

    public Ownership(MemberOsbb memberOsbb, Apartment apartment) {
        this.memberOsbb = memberOsbb;
        this.apartment = apartment;
    }

    public MemberOsbb getMemberOsbb() {
        return memberOsbb;
    }

    public void setMemberOsbb(MemberOsbb memberOsbb) {
        this.memberOsbb = memberOsbb;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ownership ownership = (Ownership) o;
        return Objects.equals(memberOsbb, ownership.memberOsbb) && Objects.equals(apartment, ownership.apartment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberOsbb, apartment);
    }
}
