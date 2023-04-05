package fr.univcotedazur.multiCredit.interfaces;

import fr.univcotedazur.multiCredit.entities.Shop;
import fr.univcotedazur.multiCredit.entities.WeekDay;

import java.time.LocalTime;

public interface ShopHandler {
    void modifyAddress(Shop shop, String address);
    void modifyPlanning(Shop shop, WeekDay day, LocalTime OpeningHours, LocalTime ClosingHours);

     void modifyName(Shop shop, String name);

}
