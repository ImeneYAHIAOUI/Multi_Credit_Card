package fr.univcotedazur.simpletcfs.cli.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class CliParking {
    @Getter
    @Setter
    private String carRegistrationNumber;
    @Getter
    @Setter
    private String mail;


}
