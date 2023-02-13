package fr.univcotedazur.simpletcfs.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
 public class Account {
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
