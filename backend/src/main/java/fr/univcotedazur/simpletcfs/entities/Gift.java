package fr.univcotedazur.simpletcfs.entities;


import javax.persistence.*;

@Entity
public class Gift {

    @ManyToOne
    @JoinColumn(name="Shop_id", nullable=false)
    public Shop shop;
    @Id
    @GeneratedValue
    private Long giftId;
    public int pointsNeeded;

    public String description;

    public AccountStatus RequiredStatus;

    public Gift(){

    }

    public int getPointsNeeded() {
        return pointsNeeded;
    }

    public void setPointsNeeded(int pointsNeeded) {
        this.pointsNeeded = pointsNeeded;
    }

    public String getDescription() {
        return description;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AccountStatus getRequiredStatus() {
        return RequiredStatus;
    }

    public void setRequiredStatus(AccountStatus requiredStatus) {
        RequiredStatus = requiredStatus;
    }
}

