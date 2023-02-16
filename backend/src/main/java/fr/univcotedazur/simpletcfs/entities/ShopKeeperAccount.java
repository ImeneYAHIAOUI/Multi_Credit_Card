package fr.univcotedazur.simpletcfs.entities;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class ShopKeeperAccount extends Account{

    @Getter
    @Setter
    private Shop shop;
    public ShopKeeperAccount(UUID id, String name, String mail, String phoneNumber, LocalDate birthday, Shop shop) {
        super(id,name, mail, phoneNumber, birthday);
        this.shop = shop;
    }


}
