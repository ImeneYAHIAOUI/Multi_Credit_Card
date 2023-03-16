package fr.univcotedazur.simpletcfs.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.UUID;

@Entity
public class AdminAccount extends Account{
    public AdminAccount( String name, String mail, String password, LocalDate birthday) {
        super(name, mail, password, birthday);
    }


    public AdminAccount() {

    }
}
