package fr.univcotedazur.multicredit.interfaces;

import fr.univcotedazur.multicredit.entities.Shop;
import fr.univcotedazur.multicredit.exceptions.AlreadyExistingShopException;
import fr.univcotedazur.multicredit.exceptions.MissingInformationException;
import fr.univcotedazur.multicredit.exceptions.ShopNotFoundException;

public interface ShopRegistration {
    Shop addShop(String name, String address) throws MissingInformationException, AlreadyExistingShopException;
    void removeShop(Long id) throws ShopNotFoundException;
}
