package fr.univcotedazur.simpletcfs.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

public class MemberAccount extends Account {
    @Getter
    @Setter
    private MembershipCard membershipCard;
    @Getter
    @Setter
    int points = 0;
    @Getter
    @Setter
    double balance = 0;

    @Getter
    @Setter
    AccountStatus status;

    public MemberAccount(UUID id, String name, String mail, String password, LocalDate birthDate, int points, double balance) {
        super(id, mail, password, name, birthDate);
        this.membershipCard =  new MembershipCard(LocalDate.now(), LocalDate.now().plusYears(2));
        this.points = points;
        this.balance = balance;
    }



}
