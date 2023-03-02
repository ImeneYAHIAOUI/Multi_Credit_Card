package fr.univcotedazur.simpletcfs.components;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import fr.univcotedazur.simpletcfs.entities.AdminAccount;
import fr.univcotedazur.simpletcfs.entities.Form;
import fr.univcotedazur.simpletcfs.exceptions.AlreadyExistingAdminException;
import fr.univcotedazur.simpletcfs.exceptions.MissingInformationException;
import fr.univcotedazur.simpletcfs.interfaces.AdminFinder;
import fr.univcotedazur.simpletcfs.interfaces.AdminRegistration;
import fr.univcotedazur.simpletcfs.interfaces.ShopRegistration;
import fr.univcotedazur.simpletcfs.interfaces.ShopkeeperRegistration;
import fr.univcotedazur.simpletcfs.repositories.AdminAccountRepository;
import fr.univcotedazur.simpletcfs.repositories.ShopKeeperAccountRepository;
import fr.univcotedazur.simpletcfs.repositories.ShopRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminManagerTest {
    @Autowired
    private ShopRegistration shopRegistration;

    @Autowired
    private ShopkeeperRegistration shopkeeperRegistration;

    @Autowired
    private AdminRegistration adminRegistration;

    @Autowired
    private AdminFinder adminFinder;

    private AdminAccount accountCreated ;

    @BeforeEach
    void setUp() {

    }

    @Test
    void createNewAdminAccount() {
        LocalDate birthday = LocalDate.of(2002, 3, 24);
        Form form = new Form("Sacho","sachotouille@gmail.com", "1234", birthday);
        try {
            adminRegistration.createAdminAccount(form);
        } catch (MissingInformationException | AlreadyExistingAdminException e) {

        }
        assertNotNull(adminRegistration);
    }

    @Test
    void badlyCreateAdminAccount() {
        LocalDate birthday = LocalDate.of(2002, 3, 24);
        Form form = new Form("Sacha","sachatouille@gmail.com", null, birthday);
        MissingInformationException exception = null;
        try {
            adminRegistration.createAdminAccount(form);
        } catch (MissingInformationException e) {
            exception = e;
        } catch ( AlreadyExistingAdminException e) {
        }
        assertNotNull(exception);
    }

    @Test
    void findAdminByExistingId() throws MissingInformationException, AlreadyExistingAdminException {
        LocalDate birthday = LocalDate.of(2002, 3, 24);
        Form form = new Form("Sachi","sachatouille@gmail.com", "1234", birthday);
        AdminAccount adminAccount = adminRegistration.createAdminAccount(form);
        assertNotNull(adminFinder.findAdminById(adminAccount.getId()));
    }

    @Test
    void deleteAdminAccount() {

    }

    @Test
    void addShop() {
    }

    @Test
    void removeShop() {
    }

    @Test
    void createShopKeeperAccount() {
    }

    @Test
    void deleteShopKeeperAccount() {
    }
}