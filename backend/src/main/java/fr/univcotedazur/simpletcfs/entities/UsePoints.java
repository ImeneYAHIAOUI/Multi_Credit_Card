package fr.univcotedazur.simpletcfs.entities;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
public class UsePoints extends Transaction{

    public int usedPoints;
    @OneToOne
    @JoinColumn( name="Gift_id" )
    public Gift gift;

    public UsePoints() {
        super();
    }


    public int getUsedPoints() {
        return usedPoints;
    }

    public UsePoints(LocalDate date, MemberAccount memberAccount) {
        super(date,memberAccount);

    }

    public UsePoints(LocalDate date, MemberAccount memberAccount,  int usedPoints, Gift gift) {
        super(date,memberAccount);
    this.usedPoints = usedPoints;
    this.gift = gift;
    }

    public Gift getGift() {
        return gift;
    }

    public void setUsedPoints(int usedPoints) {
        this.usedPoints = usedPoints;
    }

    public void setGift(Gift gift) {
        this.gift = gift;
    }

}
