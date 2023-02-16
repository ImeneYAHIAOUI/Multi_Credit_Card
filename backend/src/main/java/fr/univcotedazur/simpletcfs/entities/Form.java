package fr.univcotedazur.simpletcfs.entities;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Form {
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

