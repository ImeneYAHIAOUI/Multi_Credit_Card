package fr.univcotedazur.multiCredit.entities;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
public class AdminAccount extends Account{
    public AdminAccount( String name, String mail, String password, LocalDate birthday) {
        super(name, mail, password, birthday);
    }
    public AdminAccount() {

    }
}
