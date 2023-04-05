package fr.univcotedazur.multiCredit.interfaces;

import fr.univcotedazur.multiCredit.entities.*;
import fr.univcotedazur.multiCredit.exceptions.MissingInformationException;

public interface ShopRegistration {
    Shop addShop(String name, String address) throws MissingInformationException;
    void removeShop(Shop shop);
}
