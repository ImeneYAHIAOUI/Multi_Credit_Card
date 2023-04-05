package fr.univcotedazur.simpletcfs.interfaces;


import fr.univcotedazur.simpletcfs.entities.Shop;
import fr.univcotedazur.simpletcfs.entities.ShopKeeperAccount;

import java.util.Optional;
import java.util.UUID;

public interface ShopFinder {
    Optional<Shop> findShopById(Long id);
}
