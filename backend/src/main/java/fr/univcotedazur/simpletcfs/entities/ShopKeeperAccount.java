package fr.univcotedazur.simpletcfs.entities;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class ShopKeeperAccount extends Account{
    public ShopKeeperAccount(UUID id, String name, String mail, String phoneNumber, LocalDate birthday) {
        super(id,name, mail, phoneNumber, birthday);
    }


}
