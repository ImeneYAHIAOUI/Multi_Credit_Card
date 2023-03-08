package fr.univcotedazur.simpletcfs.entities;



import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@PrimaryKeyJoinColumn( name = "idTransaction" )
public class Purchase extends Transaction{

    public  int earnedPoints;


    public double totalPrice;

    @OneToOne
    public CreditCard creditCard;

    @OneToMany( cascade = CascadeType.ALL)
    public List<Item> item=new ArrayList<>();

    public Purchase() {
        super();

    }

    public Purchase(LocalDate date, MemberAccount memberAccount, List<Item> items){
        super(date,memberAccount);
        this.item = items;
        this.earnedPoints = 0;
        this.totalPrice = 0;
        items.forEach(item -> {
            this.earnedPoints += item.getProduct().getPoints() * item.getAmount();
            this.totalPrice += item.getProduct().getPrice() * item.getAmount();
        });

    }

    public int getEarnedPoints() {
        return earnedPoints;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public List<Item> getItem() {
        return item;
    }

    public void setEarnedPoints(int earnedPoints) {
        this.earnedPoints = earnedPoints;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }
}
