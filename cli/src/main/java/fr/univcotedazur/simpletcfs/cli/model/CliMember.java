package fr.univcotedazur.simpletcfs.cli.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

public class CliMember extends CliAccount{
    @Setter
    @Getter
    private String membershipCard;

    public CliMember(UUID id, String name, String mail, String password, LocalDate birthDate, String membershipCard) {
        super(id, name, mail, password, birthDate);
        this.membershipCard = membershipCard;
    }
}
