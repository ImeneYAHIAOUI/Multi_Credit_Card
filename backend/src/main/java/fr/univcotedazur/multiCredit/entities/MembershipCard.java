package fr.univcotedazur.multiCredit.entities;


import javax.persistence.Embeddable;
import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

@Embeddable
public class MembershipCard {
    private String number;
    private LocalDate creationDate;
    private LocalDate expirationDate;

    public MembershipCard() {
    }

    public MembershipCard(LocalDate creationDate, LocalDate expirationDate) {
        this.number = String.valueOf(ThreadLocalRandom.current().nextInt(100000000, 1000000000));
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }
}
