package fr.univcotedazur.multiCredit.interfaces;


import fr.univcotedazur.multiCredit.entities.Shop;

import java.util.List;
import java.util.Optional;

public interface ShopFinder {

    Optional<Shop> findShopById(Long id);
    List<Shop> findShopByAddress(String address);
}
