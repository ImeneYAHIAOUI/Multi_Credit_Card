package fr.univcotedazur.simpletcfs.cli.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

public class CliMember extends CliAccount{


    public CliMember( String name, String mail, String password, String birthDate) {
        super(name, mail, password, birthDate);
    }



}
