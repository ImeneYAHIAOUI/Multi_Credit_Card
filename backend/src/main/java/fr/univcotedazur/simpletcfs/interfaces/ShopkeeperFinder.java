package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.ShopKeeperAccount;

import java.util.Optional;
import java.util.UUID;

public interface ShopkeeperFinder {
    public Optional<ShopKeeperAccount> findShopKeeperAccountById(UUID id);
    public Optional<ShopKeeperAccount>findShopKeeperAccountByName(String name);

}
