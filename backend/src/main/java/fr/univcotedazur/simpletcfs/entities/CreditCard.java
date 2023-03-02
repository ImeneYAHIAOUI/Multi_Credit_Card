package fr.univcotedazur.simpletcfs.entities;


import java.time.LocalDate;

public class CreditCard {
    String owner;
    String number;
    LocalDate expirationDate;
    String cvv;
    public String ToString(){
        return "Owner: " + owner + " Number: " + number + " Expiration Date: " + expirationDate + " CVV: " + cvv;
    }

    public CreditCard(String owner, String number, LocalDate expirationDate, String cvv) {
        this.owner = owner;
        this.number = number;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }
}
