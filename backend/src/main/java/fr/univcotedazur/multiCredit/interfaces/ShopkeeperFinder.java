package fr.univcotedazur.multiCredit.interfaces;

import fr.univcotedazur.multiCredit.entities.ShopKeeperAccount;

import java.util.Optional;

public interface ShopkeeperFinder {

    Optional<ShopKeeperAccount> findShopKeeperAccountByName(String name);

    Optional<ShopKeeperAccount> findShopkeeperAccountById(Long id);

    ShopKeeperAccount findShopkeeperAccountByMail(String mail);
}

