package fr.univcotedazur.simpletcfs.entities;



public class UsePoints extends Transaction{

    public int usedPoints;
    public Gift gift;

    public int getUsedPoints() {
        return usedPoints;
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
