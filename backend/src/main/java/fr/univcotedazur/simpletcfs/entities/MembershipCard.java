package fr.univcotedazur.simpletcfs.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

public class MembershipCard {
    @Getter
    private String number;
    @Getter
    @Setter
    private boolean isValide;
    @Getter
    @Setter
    private LocalDate creationDate;
    @Getter
    @Setter
    private LocalDate expirationDate;

    public MembershipCard( LocalDate creationDate, LocalDate expirationDate) {
        this.number = String.valueOf(ThreadLocalRandom.current().nextInt(100000000, 1000000000));
        this.isValide = true;
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
    }

}
