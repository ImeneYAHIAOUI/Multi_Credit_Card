package fr.univcotedazur.multiCredit.interfaces;

import fr.univcotedazur.multiCredit.entities.Form;
import fr.univcotedazur.multiCredit.entities.ShopKeeperAccount;
import fr.univcotedazur.multiCredit.exceptions.*;

public interface ShopkeeperRegistration {
    ShopKeeperAccount createShopKeeperAccount(Form form, long shopId) throws ShopNotFoundException,MissingInformationException, AlreadyExistingMemberException, UnderAgeException;

    void deleteShopKeeperAccount(Long id)throws ShopKeeperNotFoundException;
}
