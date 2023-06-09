package fr.univcotedazur.multicredit.components;

import fr.univcotedazur.multicredit.entities.AdminAccount;
import fr.univcotedazur.multicredit.entities.Form;
import fr.univcotedazur.multicredit.entities.Shop;
import fr.univcotedazur.multicredit.entities.ShopKeeperAccount;
import fr.univcotedazur.multicredit.exceptions.*;
import fr.univcotedazur.multicredit.interfaces.*;
import fr.univcotedazur.multicredit.repositories.AdminAccountRepository;
import fr.univcotedazur.multicredit.repositories.ShopKeeperAccountRepository;
import fr.univcotedazur.multicredit.repositories.ShopRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminManagerTest {

    @Autowired
    ShopKeeperAccountRepository shopKeeperAccountRepository;
    @Autowired
    private AdminRegistration adminRegistration;
    @Autowired
    private AdminFinder adminFinder;
    @Autowired
    private ShopRegistration shopRegistration;
    @Autowired
    private ShopkeeperRegistration shopkeeperRegistration;
    private AdminAccount accountCreated;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private MemberHandler memberHandler;
    @Autowired
    private AdminAccountRepository adminAccountRepository;
    @Autowired
    private MemberFinder memberFinder;

    @BeforeEach
    void setUp() {
        adminAccountRepository.deleteAll();
        shopKeeperAccountRepository.deleteAll();
        shopRepository.deleteAll();
    }

    @Test
    void createNewAdminAccount() throws MissingInformationException, AlreadyExistingAdminException {
        LocalDate birthday = LocalDate.of(2002, 3, 24);
        Form form = new Form("Sacha", "sachatouille@gmail.com", "1234", birthday);
        accountCreated = adminRegistration.createAdminAccount(form);
        assertNotNull(adminRegistration);
    }

    @Test
    void badlyCreateAdminAccount() {
        LocalDate birthday = LocalDate.of(2002, 3, 24);
        Form form = new Form("Sacha", "sachatouille@gmail.com", null, birthday);
        assertThrows(MissingInformationException.class, () -> {
            adminRegistration.createAdminAccount(form);
        });
    }

    @Test
    void findAdminByExistingId() throws MissingInformationException, AlreadyExistingAdminException {

        LocalDate birthday = LocalDate.of(2002, 3, 24);
        Form form = new Form("Sacha", "sachatouille@gmail.com", "1234", birthday);
        AdminAccount adminAccount = adminRegistration.createAdminAccount(form);
        assertNotNull(adminFinder.findAdminById(adminAccount.getId()));
    }

    @Test
    void deleteAdminAccountTest() throws AlreadyExistingAdminException, MissingInformationException {
        LocalDate birthday = LocalDate.of(2002, 3, 24);
        Form form = new Form("Sacha", "sachatouille@gmail.com", "1234", birthday);
        AdminAccount adminAccount = adminRegistration.createAdminAccount(form);
        assertNotNull(adminFinder.findAdminById(adminAccount.getId()));
        adminRegistration.deleteAdminAccount(adminAccount);
        assertTrue(adminFinder.findAdminById(adminAccount.getId()).isEmpty());
    }

    @Test
    void addShopTest1() {
        assertThrows(MissingInformationException.class, () -> {
            shopRegistration.addShop(null, "adresse");
        });
        assertThrows(MissingInformationException.class, () -> {
            shopRegistration.addShop("name", null);
        });
        assertThrows(MissingInformationException.class, () -> {
            shopRegistration.addShop(null, null);
        });
    }

    @Test
    void addShopTest2() throws MissingInformationException, AlreadyExistingShopException {
        Shop shop = shopRegistration.addShop("nom", "adresse");
        assertNotNull(shop);
    }


    @Test
    void createShopKeeperAccount() throws MissingInformationException, AlreadyExistingMemberException, UnderAgeException, ShopNotFoundException, AlreadyExistingShopException {
        Shop shop = shopRegistration.addShop("sephora", "adresse");

        assertNotNull(shop);
        LocalDate birthday = LocalDate.of(2002, 3, 24);
        Form form = new Form("Sacha", "sachatouille@gmail.com", "1234", birthday);
        ShopKeeperAccount s = shopkeeperRegistration.createShopKeeperAccount(form, shop.getId());
        assertNotNull(s);
        assertThrows(AlreadyExistingMemberException.class, () -> shopkeeperRegistration.createShopKeeperAccount(form, shop.getId()));
    }

    @Test
    void createShopKeeperAccount1() throws MissingInformationException, AlreadyExistingMemberException, UnderAgeException, AlreadyExistingShopException {
        Shop shop = shopRegistration.addShop("sephora", "adresse");

        assertNotNull(shop);
        LocalDate birthday = LocalDate.of(2019, 3, 24);
        Form form = new Form("Sacha", "sachatouille@gmail.com", "1234", birthday);
        assertThrows(UnderAgeException.class, () -> shopkeeperRegistration.createShopKeeperAccount(form, shop.getId()));
    }

    @Test
    void createShopKeeperAccount2() throws MissingInformationException, AlreadyExistingMemberException, UnderAgeException, AlreadyExistingShopException {
        Shop shop = shopRegistration.addShop("sephora", "adresse");
        assertNotNull(shop);
        LocalDate birthday = LocalDate.of(2019, 3, 24);
        assertThrows(MissingInformationException.class, () -> shopkeeperRegistration.createShopKeeperAccount(
                new Form(null, "sachatouille@gmail.com", "1234", birthday), shop.getId()));
        assertThrows(MissingInformationException.class, () -> shopkeeperRegistration.createShopKeeperAccount(
                new Form("Sacha", null, "1234", birthday), shop.getId()));
        assertThrows(MissingInformationException.class, () -> shopkeeperRegistration.createShopKeeperAccount(
                new Form("Sacha", "sachatouille@gmail.com", null, birthday), shop.getId()));

        assertThrows(MissingInformationException.class, () -> shopkeeperRegistration.createShopKeeperAccount(
                new Form("Sacha", "sachatouille@gmail.com", "sdsd", null), shop.getId()));
        assertThrows(MissingInformationException.class, () -> shopkeeperRegistration.createShopKeeperAccount(
                new Form(null, null, null, null), shop.getId()));
        assertThrows(ShopNotFoundException.class, () -> shopkeeperRegistration.createShopKeeperAccount(
                new Form("name", "sachatouille@gmail.com", "1234", birthday), 100L));
    }

    @Test
    void deleteShopKeeperAccountTest() throws MissingInformationException, AlreadyExistingMemberException, UnderAgeException, ShopNotFoundException, ShopKeeperNotFoundException, AlreadyExistingShopException {
        Shop shop = shopRegistration.addShop("sephora", "adresse");
        assertNotNull(shop);
        LocalDate birthday = LocalDate.of(2002, 3, 24);
        Form form = new Form("Sacha", "sachatouille@gmail.com", "1234", birthday);
        ShopKeeperAccount s = shopkeeperRegistration.createShopKeeperAccount(form, shop.getId());
        assertNotNull(s);
        assertTrue(shopKeeperAccountRepository.existsById(s.getId()));
        shopkeeperRegistration.deleteShopKeeperAccount(s.getId());
        assertFalse(shopKeeperAccountRepository.existsById(s.getId()));
        assertFalse(shopRepository.existsById(shop.getId()));
    }

    @Test
    void removeShopTest() throws MissingInformationException, AlreadyExistingShopException, ShopNotFoundException {
        Shop shop = shopRegistration.addShop("sephora", "adresse");
        assertNotNull(shop);
        shopRegistration.removeShop(shop.getId());
        assertTrue(shopRepository.findById(shop.getId()).isEmpty());
    }
}