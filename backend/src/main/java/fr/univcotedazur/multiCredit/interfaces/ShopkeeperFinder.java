package fr.univcotedazur.multiCredit.interfaces;

import fr.univcotedazur.multiCredit.entities.ShopKeeperAccount;

import java.util.Optional;

public interface ShopkeeperFinder {

    public Optional<ShopKeeperAccount>findShopKeeperAccountByName(String name);

    public Optional<ShopKeeperAccount> findShopkeeperAccountById(Long id);
    ShopKeeperAccount findShopkeeperAccountByMail(String mail);

}

