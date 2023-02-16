package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.entities.*;
import fr.univcotedazur.simpletcfs.exceptions.MissingInformationException;
import fr.univcotedazur.simpletcfs.interfaces.*;
import fr.univcotedazur.simpletcfs.repositories.AdminAccountRepository;
import fr.univcotedazur.simpletcfs.repositories.ShopKeeperAccountRepository;
import fr.univcotedazur.simpletcfs.repositories.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class AdminManager implements ShopRegistration, ShopkeeperRegistration, AdminRegistration, AdminFinder{

    private final AdminAccountRepository adminAccountRepository;
    private final ShopRepository shopRepository;
    private final ShopKeeperAccountRepository shopKeeperAccountRepository;
    private final ShopManager shopManager;



    @Autowired
    public AdminManager(AdminAccountRepository adminAccountRepository, ShopRepository shopRepository, ShopKeeperAccountRepository shopKeeperAccountRepository, ShopManager shopManager) {
        this.adminAccountRepository = adminAccountRepository;
        this.shopRepository = shopRepository;
        this.shopKeeperAccountRepository = shopKeeperAccountRepository;
        this.shopManager = shopManager;
    }

    @Override
    public AdminAccount findAdminById(UUID id) {
        while (adminAccountRepository.findAll().iterator().hasNext()) {
            AdminAccount adminAccount = adminAccountRepository.findAll().iterator().next();
            if (adminAccount.getId().equals(id)) {
                return adminAccount;
            }
        }
        return null;
    }

    @Override
    public AdminAccount createAdminAccount(Form form) throws MissingInformationException {
        if (form.getName() == null || form.getMail() == null || form.getPassword() == null || form.getBirthDate() == null) {
            throw new MissingInformationException();
        }
        AdminAccount adminAccount = new AdminAccount(UUID.randomUUID(), form.getName(), form.getMail(), form.getPassword(), form.getBirthDate());
        adminAccountRepository.save(adminAccount, adminAccount.getId());
        return adminAccount;
    }

    @Override
    public void deleteAdminAccount(AdminAccount account) {
        if(findAdminById(account.getId()) != null){
            adminAccountRepository.deleteById(account.getId());
        }
    }

    @Override
    public Shop addShop(String name, String address, Map<WeekDay, Planning> planning) throws MissingInformationException{
        if (name == null || address == null || planning == null) {
            throw new MissingInformationException();
        }
        //TODO mettre le planning correctement
        Shop shop = new Shop(UUID.randomUUID(), name, address, planning);
        shopRepository.save(shop,shop.getId());
        return shop;
    }

    @Override
    public void removeShop(Shop shop) {
        if(shopManager.findShopById(shop.getId()).isPresent()){
            shopRepository.deleteById(shop.getId());
        }
    }

    @Override
    public ShopKeeperAccount createShopKeeperAccount(Form form, Shop shop) throws MissingInformationException {
        if (form.getName() == null || form.getMail() == null || form.getPassword() == null || form.getBirthDate() == null) {
            throw new MissingInformationException();
        }
        ShopKeeperAccount shopKeeperAccount = new ShopKeeperAccount(UUID.randomUUID(), form.getName(), form.getMail(), form.getPassword(), form.getBirthDate(), shop);
        shopKeeperAccountRepository.save(shopKeeperAccount, shopKeeperAccount.getId());
        return shopKeeperAccount;
    }

    @Override
    public void deleteShopKeeperAccount(ShopKeeperAccount account) {
        if(shopManager.findShopKeeperAccountById(account.getId()).isPresent()){
            shopRepository.deleteById(account.getId());
        }
    }

}
