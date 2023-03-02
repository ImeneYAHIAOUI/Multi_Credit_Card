package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.AdminAccount;
import fr.univcotedazur.simpletcfs.entities.MemberAccount;
import fr.univcotedazur.simpletcfs.entities.ShopKeeperAccount;

import java.util.UUID;

public interface AdminFinder {
    AdminAccount findAdminById(UUID id);
    ShopKeeperAccount findShopkeeperAccountByMail(String mail);

}
