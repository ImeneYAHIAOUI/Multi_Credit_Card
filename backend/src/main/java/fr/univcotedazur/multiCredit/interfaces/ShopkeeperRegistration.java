package fr.univcotedazur.multiCredit.interfaces;

import fr.univcotedazur.multiCredit.entities.Form;
import fr.univcotedazur.multiCredit.entities.ShopKeeperAccount;
import fr.univcotedazur.multiCredit.exceptions.AlreadyExistingMemberException;
import fr.univcotedazur.multiCredit.exceptions.MissingInformationException;
import fr.univcotedazur.multiCredit.exceptions.UnderAgeException;

public interface ShopkeeperRegistration {
    ShopKeeperAccount createShopKeeperAccount(Form form, long shopId) throws MissingInformationException, AlreadyExistingMemberException, UnderAgeException;
    void deleteShopKeeperAccount(ShopKeeperAccount account);
}
