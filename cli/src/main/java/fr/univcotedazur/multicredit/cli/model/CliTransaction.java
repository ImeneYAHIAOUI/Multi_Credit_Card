package fr.univcotedazur.multicredit.cli.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CliTransaction {
    private long id;
    private String date;
    private long memberAccount;
    private long shop;
    private int[] quantities;
    private long[] items;
    private int earnedPoints;
    private double totalPrice;
    private String creditCardNumber;
    private int pointsUsed;
    private long gift;
    private String paymentMethod;


    public long getShop() {
        return shop;
    }


    @JsonCreator
    public CliTransaction(
            @JsonProperty("id")long id,
            @JsonProperty("date")String date,
            @JsonProperty("memberAccount")long memberAccount,
            @JsonProperty("shopId")long shop,
            @JsonProperty("earnedPoints") int earnedPoints,
            @JsonProperty("totalPrice") double totalPrice,
            @JsonProperty("creditCardNumber") String creditCardNumber,
            @JsonProperty("items") long[] items,
            @JsonProperty("quantities") int[] quantities,
            @JsonProperty("pointsUsed")int pointsUsed,
            @JsonProperty("gift")long gift,
            @JsonProperty("paymentMethod")String paymentMethod
            ) {
        this.id = id;
        this.date = date;
        this.memberAccount = memberAccount;
        this.shop = shop;
        this.earnedPoints = earnedPoints;
        this.totalPrice = totalPrice;
        this.creditCardNumber = creditCardNumber;
        this.items = items;
        this.quantities = quantities;
        this.pointsUsed = pointsUsed;
        this.gift = gift;
        this.paymentMethod = paymentMethod;
    }

    private void setItems(long[] items) {
    }

    private void setQuantities(int[] quantities) {
        this.quantities = quantities;
    }

    public void setShop(long shop) {
        this.shop = shop;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }


    public void setDate(String date) {
        this.date = date;
    }

    public long getMemberAccount() {
        return memberAccount;
    }

    public void setMemberAccount(long memberAccount) {
        this.memberAccount = memberAccount;
    }

    public CliTransaction( long memberId,long shop) {
        this.memberAccount = memberId;
        this.shop = shop;
    }

    public String toString()
    {
        if (earnedPoints > 0)
            return "purchase{" +
                    "memberId=" + getMemberAccount() +
                    ", shop=" + getShop() +
                    "earnedPoints=" + earnedPoints +
                    ", totalPrice=" + totalPrice +
                    ", creditCardNumber='" + creditCardNumber + '\'' +
                    ", items=" + List.of(items) +
                    ", quantities=" + List.of(quantities)+
                    ", paymentMethod='" + paymentMethod + '\'' +
                    "}\n";
        else
            return "usePoints{" +
                    "memberId=" + getMemberAccount() +
                    ", shop=" + getShop() +
                    ", pointsUsed=" + pointsUsed +
                    ", gift=" + gift +
                    "}\n";
    }

}
