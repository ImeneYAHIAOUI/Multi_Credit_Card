package fr.univcotedazur.simpletcfs.entities;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Gift {
    @Id
    @GeneratedValue
    @Column(name="Gift_id", nullable=false)
    private Long giftId;
    @ManyToOne
    @JoinColumn(name="Shop_id", nullable=false)
    public Shop shop;
    @OneToOne(mappedBy = "gift")
    private UsePoints usePoints;
    private int pointsNeeded;

    private String description;

    private AccountStatus requiredStatus;
    public Gift(int pointsNeeded, String description, AccountStatus requiredStatus) {
        this.pointsNeeded = pointsNeeded;
        this.description = description;
        this.requiredStatus = requiredStatus;
    }
    public Gift(){

    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Gift gift)) return false;
        return Objects.equals(pointsNeeded, gift.pointsNeeded) &&
                Objects.equals(requiredStatus, gift.requiredStatus)
                && Objects.equals(description, gift.description);
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
    @Override
    public String toString(){
        return "gift id= " + giftId +
                ", pointsNeeded= " + pointsNeeded +
                ", description= " + description + '\'' +
                ", requiredStatus= " + requiredStatus.getAccountStatusName();
    }
}

