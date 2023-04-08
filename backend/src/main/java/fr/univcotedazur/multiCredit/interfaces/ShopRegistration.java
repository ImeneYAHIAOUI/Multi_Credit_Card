package fr.univcotedazur.multiCredit.interfaces;

import fr.univcotedazur.multiCredit.entities.*;
import fr.univcotedazur.multiCredit.exceptions.AlreadyExistingShopException;
import fr.univcotedazur.multiCredit.exceptions.MissingInformationException;
import fr.univcotedazur.multiCredit.exceptions.ShopNotFoundException;

public interface ShopRegistration {
    Shop addShop(String name, String address) throws MissingInformationException, AlreadyExistingShopException;
    void removeShop(Long id) throws ShopNotFoundException;
}
