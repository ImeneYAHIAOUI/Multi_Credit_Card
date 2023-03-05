package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.entities.*;
import fr.univcotedazur.simpletcfs.exceptions.AlreadyExistingMemberException;
import fr.univcotedazur.simpletcfs.exceptions.MissingInformationException;
import fr.univcotedazur.simpletcfs.exceptions.UnderAgeException;
import fr.univcotedazur.simpletcfs.interfaces.*;
import fr.univcotedazur.simpletcfs.repositories.AdminAccountRepository;
import fr.univcotedazur.simpletcfs.repositories.ShopKeeperAccountRepository;
import fr.univcotedazur.simpletcfs.repositories.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
@Transactional
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
    public Optional<AdminAccount> findAdminById(Long id) {
        return adminAccountRepository.findById(id);
    }

    @Override
    public AdminAccount createAdminAccount(Form form) throws MissingInformationException {
        if (form.getName() == null || form.getMail() == null || form.getPassword() == null || form.getBirthDate() == null) {
            throw new MissingInformationException();
        }
        AdminAccount adminAccount = new AdminAccount( form.getName(), form.getMail(), form.getPassword(), form.getBirthDate());
         adminAccountRepository.save(adminAccount, adminAccount.getId());
        return adminAccount;
    }

    @Override
    public void deleteAdminAccount(AdminAccount account) {
        if(findAdminById(account.getId()).isPresent()){
            adminAccountRepository.deleteById(account.getId());
        }
    }

    @Override
    public Shop addShop(String name, String address, Map<WeekDay, Planning> planning, List<Product> productList,List<Gift> giftList) throws MissingInformationException{
        if (name == null || address == null || planning == null) {
            throw new MissingInformationException();
        }
        //TODO mettre le planning correctement
        Shop shop = new Shop(UUID.randomUUID(), name, address, planning,productList,giftList);
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
    public ShopKeeperAccount createShopKeeperAccount(Form form, Shop shop) throws MissingInformationException,AlreadyExistingMemberException, UnderAgeException {
        if (form.getName() == null || form.getMail() == null || form.getPassword() == null || form.getBirthDate() == null) {
            throw new MissingInformationException();
        }
        ShopKeeperAccount shopKeeperAccount = findShopkeeperAccountByMail(form.getMail());
        if(shopKeeperAccount != null ){
            throw new AlreadyExistingMemberException();
        }
        if(form.getBirthDate().isAfter(LocalDate.now().minusYears(16))){
            throw new UnderAgeException();
        }
        shopKeeperAccount = new ShopKeeperAccount( form.getName(), form.getMail(), form.getPassword(), form.getBirthDate(), shop);
        shopKeeperAccountRepository.save(shopKeeperAccount, shopKeeperAccount.getId());
        return shopKeeperAccount;
    }

    @Override
    public void deleteShopKeeperAccount(ShopKeeperAccount account) {
        if(account!=null && shopManager.findShopKeeperAccountById(account.getId()).isPresent()){
            shopKeeperAccountRepository.deleteById(account.getId());
        }
    }
    @Override
    public  ShopKeeperAccount findShopkeeperAccountByMail(String mail) {
        for (ShopKeeperAccount  shopkeeperAccount : shopKeeperAccountRepository.findAll()) {
            if (shopkeeperAccount.getMail().equals(mail)) {
                return shopkeeperAccount;
            }
        }
        return null;
    }

}
