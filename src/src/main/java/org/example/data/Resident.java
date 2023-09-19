package org.example.data;


import jakarta.persistence.*;

@Entity
@IdClass(Resident.class)
public class Resident {

    @Id
    @ManyToOne
    @JoinColumn(name = "members_osbb_id")
    private MemberOsbb memberOsbb;

    @Id
    @ManyToOne
    @JoinColumn(name = "apartments_id")
    private Apartment apartment;

    private boolean entryRightsTerritory;

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

    public boolean isEntryRightsTerritory() {
        return entryRightsTerritory;
    }

    public void setEntryRightsTerritory(boolean entryRightsTerritory) {
        this.entryRightsTerritory = entryRightsTerritory;
    }
}
