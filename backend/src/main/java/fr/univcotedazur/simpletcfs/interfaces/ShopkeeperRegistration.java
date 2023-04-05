package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.Form;
import fr.univcotedazur.simpletcfs.entities.Shop;
import fr.univcotedazur.simpletcfs.entities.ShopKeeperAccount;
import fr.univcotedazur.simpletcfs.exceptions.AlreadyExistingMemberException;
import fr.univcotedazur.simpletcfs.exceptions.MissingInformationException;
import fr.univcotedazur.simpletcfs.exceptions.UnderAgeException;

public interface ShopkeeperRegistration {
    ShopKeeperAccount createShopKeeperAccount(Form form, Long shopId) throws MissingInformationException, AlreadyExistingMemberException, UnderAgeException;
    void deleteShopKeeperAccount(ShopKeeperAccount account);
}
