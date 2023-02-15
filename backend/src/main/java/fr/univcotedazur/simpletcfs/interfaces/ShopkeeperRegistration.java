package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.Form;
import fr.univcotedazur.simpletcfs.entities.ShopKeeperAccount;
import fr.univcotedazur.simpletcfs.exceptions.MissingInformationException;

public interface ShopkeeperRegistration {
    ShopKeeperAccount createShopKeeperAccount(Form form) throws MissingInformationException;
    void deleteShopKeeperAccount(ShopKeeperAccount account);
}
