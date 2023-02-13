package fr.univcotedazur.simpletcfs.cli.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

// A cli side class being equivalent to the backend AccountDTO, in terms of attributes
// so that the automatic JSON (de-)/serialization will make the two compatible on each side
@AllArgsConstructor
public class CliAccount {

    @Getter
    @Setter
    private UUID id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String mail;
    @Getter
    @Setter
    private String password;
    @Getter
    @Setter
    private LocalDate birthDate;


}
