package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.ShopKeeperAccount;

import java.util.Optional;
import java.util.UUID;

public interface ShopkeeperFinder {

    Optional<ShopKeeperAccount> findShopKeeperAccountById(Long id);

    public Optional<ShopKeeperAccount>findShopKeeperAccountByName(String name);

    public Optional<ShopKeeperAccount> findShopKeeperAccountByMail(String mail);

}
