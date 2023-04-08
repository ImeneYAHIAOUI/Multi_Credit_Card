package fr.univcotedazur.multicredit.entities;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.time.LocalDate;

@Entity
public class UsePoints extends Transaction {

    int usedPoints;
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "Gift_id")
    Gift gift;

    public UsePoints() {
        super();
    }


    public UsePoints(LocalDate date, MemberAccount memberAccount) {
        super(date, memberAccount);
    }

    public UsePoints(LocalDate date, MemberAccount memberAccount, int usedPoints, Gift gift) {
        super(date, memberAccount);
        this.usedPoints = usedPoints;
        this.gift = gift;
    }

    public int getUsedPoints() {
        return usedPoints;
    }

    public void setUsedPoints(int usedPoints) {
        this.usedPoints = usedPoints;
    }

    public Gift getGift() {
        return gift;
    }

    public void setGift(Gift gift) {
        this.gift = gift;
    }
}