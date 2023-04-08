package fr.univcotedazur.multiCredit.controllers.dto;


public class PurchaseDTO extends TransactionDTO {
    int earnedPoints;
    double totalPrice;
    private String creditCardNumber;
    private long[] items;
    private int[] quantities;
    private String paymentMethod;

    public PurchaseDTO(long id, String date, long memberAccount, long shop, int earnedPoints, double totalPrice, String creditCardNumber, long[] items, int[] quantities, String paymentMethod) {
        super(id, date, memberAccount, shop);
        this.earnedPoints = earnedPoints;
        this.totalPrice = totalPrice;
        this.creditCardNumber = creditCardNumber;
        this.items = items;
        this.quantities = quantities;
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
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

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public long[] getItems() {
        return items;
    }

    public void setItems(long[] items) {
        this.items = items;
    }

    public int[] getQuantities() {
        return quantities;
    }

    public void setQuantities(int[] quantities) {
        this.quantities = quantities;
    }
}
