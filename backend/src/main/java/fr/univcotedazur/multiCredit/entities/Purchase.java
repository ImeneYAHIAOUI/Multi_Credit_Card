package fr.univcotedazur.multiCredit.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Purchase extends Transaction {
    @OneToMany(targetEntity = Item.class, mappedBy = "purchase", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    public List<Item> item = new ArrayList<>();
    private int earnedPoints;
    private double totalPrice;
    private PaymentMethod paymentMethod;
    private String creditCardNumber;

    public Purchase() {
        super();
    }

    public Purchase(LocalDate date, MemberAccount memberAccount, List<Item> items) {
        super(date, memberAccount);
        this.earnedPoints = 0;
        this.totalPrice = 0;
        items.forEach(ite -> {
            this.item.add(ite);
            this.earnedPoints += ite.getProduct().getPoints() * ite.getAmount();
            this.totalPrice += ite.getProduct().getPrice() * ite.getAmount();
        });
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public void addItem(Item item) {
        this.item.add(item);
        this.earnedPoints += item.getProduct().getPoints() * item.getAmount();
        this.totalPrice += item.getProduct().getPrice() * item.getAmount();
    }

    public int getEarnedPoints() {
        return earnedPoints;
    }

    public void setEarnedPoints(int earnedPoints) {
        this.earnedPoints = earnedPoints;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
