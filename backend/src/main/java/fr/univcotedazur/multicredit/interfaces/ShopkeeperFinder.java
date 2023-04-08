package fr.univcotedazur.multicredit.interfaces;

import fr.univcotedazur.multicredit.entities.ShopKeeperAccount;

import java.util.Optional;

public interface ShopkeeperFinder {

    Optional<ShopKeeperAccount> findShopKeeperAccountByName(String name);

    Optional<ShopKeeperAccount> findShopkeeperAccountById(Long id);

    ShopKeeperAccount findShopkeeperAccountByMail(String mail);
}

