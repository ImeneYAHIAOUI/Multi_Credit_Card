package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.Gift;
import fr.univcotedazur.simpletcfs.entities.Shop;
import fr.univcotedazur.simpletcfs.entities.WeekDay;
import fr.univcotedazur.simpletcfs.exceptions.GiftNotFoundException;

import java.time.LocalTime;

public interface ShopHandler {
    void modifyAdress(Shop shop, String adress);
    void modifyPlanning(Shop shop, WeekDay day, LocalTime OpeningHours, LocalTime ClosingHours);
    void addGift(Shop shop, Gift gift);
    void removeGift(Shop shop, Gift gift) throws GiftNotFoundException;

}