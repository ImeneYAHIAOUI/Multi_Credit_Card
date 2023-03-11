package fr.univcotedazur.simpletcfs.entities;


import javax.persistence.*;

@Entity
public class Gift {


    @Id
    @GeneratedValue
    @Column(name="Gift_id", nullable=false)
    private Long giftId;
    @ManyToOne
    @JoinColumn(name="Shop_id", nullable=false)
    public Shop shop;
    private int pointsNeeded;

    private String description;

    private AccountStatus requiredStatus;

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

    public Long getGiftId() {
        return giftId;
    }

    public void setGiftId(Long giftId) {
        this.giftId = giftId;
    }

    public Shop getShop() {
        return shop;
    }


    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AccountStatus getRequiredStatus() {
        return requiredStatus;
    }

    public void setRequiredStatus(AccountStatus requiredStatus) {
        this.requiredStatus = requiredStatus;
    }
}

