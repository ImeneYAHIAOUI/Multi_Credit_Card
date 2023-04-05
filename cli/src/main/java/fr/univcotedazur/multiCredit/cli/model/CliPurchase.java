package fr.univcotedazur.multiCredit.cli.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CliPurchase extends CliTransaction
{
    int earnedPoints;
    double totalPrice;
    private String creditCardNumber;
    long[] items;
    int[] quantities;
    String paymentMethod;

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

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }





    public CliPurchase(  long memberId,long shop,String creditCardNumber, long[] items, int[] quantitie)
    {
        super(memberId,shop);
        this.creditCardNumber = creditCardNumber;
        this.items = items;
        this.quantities = quantitie;

    }

    public long[] getItems() {
        return items;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @JsonCreator
    public CliPurchase(@JsonProperty("memberId")long memberId,
                       @JsonProperty("shop") long shop,
                       @JsonProperty("earnedPoints") int earnedPoints,
                       @JsonProperty("totalPrice") double totalPrice,
                       @JsonProperty("creditCardNumber") String creditCardNumber,
                       @JsonProperty("items") long[] items,
                       @JsonProperty("quantities") int[] quantities,
                       @JsonProperty("paymentMethod") String paymentMethod) {
        super(memberId, shop);
        this.earnedPoints = earnedPoints;
        this.totalPrice = totalPrice;
        this.creditCardNumber = creditCardNumber;
        this.items = items;
        this.quantities = quantities;
        this.paymentMethod = paymentMethod;
    }

    public int[] getQuantities() {
        return quantities;
    }

    public CliPurchase(long memberId,long shop, long[] items, int[] quantitie)
    {
        super(memberId,shop);
        this.items = items;
        this.quantities = quantitie;
    }

    @Override
    public String toString() {
        return "purchase{" +
                "memberId=" + getMemberAccount() +
                ", shop=" + getShop() +
                "earnedPoints=" + earnedPoints +
                ", totalPrice=" + totalPrice +
                ", creditCardNumber='" + creditCardNumber + '\'' +
                ", items=" + List.of(items) +
                ", quantities=" + List.of(quantities) +
                '}';
    }



}
