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

    public Form(String name, String mail, String password, LocalDate birthDate) {
        this.name = name;
        this.mail = mail;
        this.password = password;
        this.birthDate = birthDate;
    }
}

