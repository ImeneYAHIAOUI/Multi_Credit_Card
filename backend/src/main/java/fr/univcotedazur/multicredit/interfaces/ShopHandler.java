package fr.univcotedazur.multicredit.interfaces;

import fr.univcotedazur.multicredit.entities.Shop;
import fr.univcotedazur.multicredit.entities.WeekDay;

import java.time.LocalTime;

public interface ShopHandler {
    void modifyAddress(Shop shop, String address);

    void modifyPlanning(Shop shop, WeekDay day, LocalTime openingHours, LocalTime closingHours);

    void modifyName(Shop shop, String name);
}
