package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.ShopKeeperAccount;

import java.util.Optional;
import java.util.UUID;

public interface ShopkeeperFinder {

    public Optional<ShopKeeperAccount>findShopKeeperAccountByName(String name);

    public Optional<ShopKeeperAccount> findShopkeeperAccountById(Long id);
    ShopKeeperAccount findShopkeeperAccountByMail(String mail);

}

