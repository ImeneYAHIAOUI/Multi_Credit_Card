package fr.univcotedazur.multicredit.interfaces;

import fr.univcotedazur.multicredit.entities.Form;
import fr.univcotedazur.multicredit.entities.ShopKeeperAccount;
import fr.univcotedazur.multicredit.exceptions.*;

public interface ShopkeeperRegistration {
    ShopKeeperAccount createShopKeeperAccount(Form form, long shopId) throws ShopNotFoundException,MissingInformationException, AlreadyExistingMemberException, UnderAgeException;

    void deleteShopKeeperAccount(Long id)throws ShopKeeperNotFoundException;
}
