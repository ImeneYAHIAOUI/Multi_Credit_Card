package fr.univcotedazur.simpletcfs.entities;



import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

public class MembershipCard {
    private String number;

    private boolean isValide;

    private LocalDate creationDate;

    private LocalDate expirationDate;

    public MembershipCard( LocalDate creationDate, LocalDate expirationDate) {
        this.number = String.valueOf(ThreadLocalRandom.current().nextInt(100000000, 1000000000));
        this.isValide = true;
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isValide() {
        return isValide;
    }

    public void setValide(boolean valide) {
        isValide = valide;
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
