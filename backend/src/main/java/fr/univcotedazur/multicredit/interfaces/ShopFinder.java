package fr.univcotedazur.multicredit.interfaces;


import fr.univcotedazur.multicredit.entities.Shop;

import java.util.List;
import java.util.Optional;

public interface ShopFinder {

    Optional<Shop> findShopById(Long id);
    List<Shop> findShopByAddress(String address);
}
