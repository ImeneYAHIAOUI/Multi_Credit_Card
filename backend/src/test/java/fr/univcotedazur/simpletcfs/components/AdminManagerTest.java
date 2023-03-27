package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.entities.AdminAccount;
import fr.univcotedazur.simpletcfs.entities.Form;
import fr.univcotedazur.simpletcfs.exceptions.AlreadyExistingAdminException;
import fr.univcotedazur.simpletcfs.exceptions.MissingInformationException;
import fr.univcotedazur.simpletcfs.interfaces.AdminFinder;
import fr.univcotedazur.simpletcfs.interfaces.AdminRegistration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AdminManagerTest {

    private AdminRegistration adminRegistration;

    private AdminFinder adminFinder;

    private AdminAccount accountCreated;

    @BeforeEach
    void setUp() {

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
        MissingInformationException exception = null;
        try {
            adminRegistration.createAdminAccount(form);
        } catch (MissingInformationException e) {
            exception = e;
        } catch (AlreadyExistingAdminException e) {
            throw new RuntimeException(e);
        }
        assertNotNull(exception);
    }

    @Test
    void findAdminByExistingId() throws MissingInformationException, AlreadyExistingAdminException {
        LocalDate birthday = LocalDate.of(2002, 3, 24);
        Form form = new Form("Sacha", "sachatouille@gmail.com", "1234", birthday);
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