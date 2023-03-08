package fr.univcotedazur.simpletcfs.entities;


import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class CreditCard {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    String owner;
    String number;
    LocalDate expirationDate;
    String cvv;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CreditCard() {
    }
    public String ToString(){
        return "Owner: " + owner + " Number: " + number + " Expiration Date: " + expirationDate + " CVV: " + cvv;
    }

    public CreditCard(String owner, String number, LocalDate expirationDate, String cvv) {
        this.owner = owner;
        this.number = number;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}
