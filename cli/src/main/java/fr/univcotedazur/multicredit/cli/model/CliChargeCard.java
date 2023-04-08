package fr.univcotedazur.multicredit.cli.model;

public class CliChargeCard {

    long memberId;
    double amount;
    String cardNumber;

    public CliChargeCard(long memberId, double amount, String cardNumber) {
        this.memberId = memberId;
        this.amount = amount;
        this.cardNumber = cardNumber;
    }

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
}
