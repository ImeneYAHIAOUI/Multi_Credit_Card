package fr.univcotedazur.simpletcfs.entities;


import java.time.LocalDate;
import java.util.UUID;

public class UsePoints extends Transaction{

    public int usedPoints;
    public Gift gift;

    public int getUsedPoints() {
        return usedPoints;
    }

    public UsePoints(LocalDate date, UUID id, MemberAccount memberAccount, Shop shop) {
        super(date,id,memberAccount,shop);

    }

    public UsePoints(LocalDate date, UUID id, MemberAccount memberAccount, Shop shop, int usedPoints, Gift gift) {
        super(date,id,memberAccount,shop);

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
