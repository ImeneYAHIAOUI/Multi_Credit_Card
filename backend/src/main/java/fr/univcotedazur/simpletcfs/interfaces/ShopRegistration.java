package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.MemberAccount;
import fr.univcotedazur.simpletcfs.entities.Shop;

import java.time.LocalTime;
import java.util.UUID;

public interface ShopRegistration {
    Shop addShop(String name, String address, LocalTime openningHour, LocalTime closingHour);
    void removeShop(Shop shop);
}
