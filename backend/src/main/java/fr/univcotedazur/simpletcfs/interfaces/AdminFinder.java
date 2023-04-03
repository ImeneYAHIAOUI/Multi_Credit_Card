package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.AdminAccount;
import fr.univcotedazur.simpletcfs.entities.ShopKeeperAccount;

import java.util.Optional;

public interface AdminFinder {
    AdminAccount findAdminByMail(String mail);

    Optional<AdminAccount> findAdminById(Long id);
    ShopKeeperAccount findShopkeeperAccountByMail(String mail);

}
