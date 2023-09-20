package org.example.data;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "residents")
@IdClass(Resident.class)
final public class Resident {

    @Id
    @ManyToOne
    @JoinColumn(name = "members_osbb_id")
    private MemberOsbb memberOsbb;

    @Id
    @ManyToOne
    @JoinColumn(name = "apartments_id")
    private Apartment apartment;

    @Column(name = "entry_rights_territory")
    private boolean entryRightsTerritory;

    public Resident() {
    }

    public Resident(final MemberOsbb memberOsbb, final Apartment apartment, final boolean entryRightsTerritory) {
        this.memberOsbb = memberOsbb;
        this.apartment = apartment;
        this.entryRightsTerritory = entryRightsTerritory;
    }

    public MemberOsbb getMemberOsbb() {
        return memberOsbb;
    }

    public void setMemberOsbb(final MemberOsbb memberOsbb) {
        this.memberOsbb = memberOsbb;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(final Apartment apartment) {
        this.apartment = apartment;
    }

    public boolean isEntryRightsTerritory() {
        return entryRightsTerritory;
    }

    public void setEntryRightsTerritory(final boolean entryRightsTerritory) {
        this.entryRightsTerritory = entryRightsTerritory;
    }

    @Override
    public String toString() {
        return "Resident{"
                + "memberOsbb="
                + memberOsbb
                + ", apartment="
                + apartment
                + ", entryRightsTerritory=" + entryRightsTerritory
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

        Resident resident = (Resident) o;
        return entryRightsTerritory == resident.entryRightsTerritory && Objects.equals(memberOsbb, resident.memberOsbb) && Objects.equals(apartment, resident.apartment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberOsbb, apartment, entryRightsTerritory);
    }
}
