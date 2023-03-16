package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.*;
import fr.univcotedazur.simpletcfs.exceptions.MissingInformationException;

import java.util.List;

public interface ShopRegistration {
    Shop addShop(String name, String address) throws MissingInformationException;
    void removeShop(Shop shop);
}
