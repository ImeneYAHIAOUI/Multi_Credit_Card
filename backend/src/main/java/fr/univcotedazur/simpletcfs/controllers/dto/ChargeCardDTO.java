package fr.univcotedazur.simpletcfs.controllers.dto;

public class ChargeCardDTO {

    long memberId;
    double amount;
    String cardNumber;

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public ChargeCardDTO(long memberId, double amount, String cardNumber) {
        this.memberId = memberId;
        this.amount = amount;
        this.cardNumber = cardNumber;
    }
}
