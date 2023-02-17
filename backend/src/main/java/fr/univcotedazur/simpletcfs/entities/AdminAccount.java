package fr.univcotedazur.simpletcfs.entities;

import java.time.LocalDate;
import java.util.UUID;

public class AdminAccount extends Account{
    public AdminAccount(UUID id, String name, String mail, String password, LocalDate birthday) {
        super(id,name, mail, password, birthday);
    }


}
