package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.entities.*;
import fr.univcotedazur.simpletcfs.exceptions.*;
import fr.univcotedazur.simpletcfs.interfaces.MemberFinder;
import fr.univcotedazur.simpletcfs.interfaces.MemberHandler;
import fr.univcotedazur.simpletcfs.interfaces.ParkingHandler;
import fr.univcotedazur.simpletcfs.repositories.TransactionRepository;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.TestPropertySource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(properties = {"VFP.updateRate.cron=*/1 * * * * *","VFP.MinPurchasesNumber=5"})
@Commit
public class MemberManagerTests {

    @Autowired
    TransactionRepository transactionRepository;
    @SpyBean
    MemberHandler memberHandler;
    @Autowired
    MemberFinder memberFinder;
    @SpyBean
    ParkingHandler parkingHandler;




    @BeforeEach
    void setUp(){
        try {
            memberHandler.deleteAccount(memberFinder.findByMail("John.Doe@mail.com").orElse(null));
        } catch (AccountNotFoundException ignored) {
        }
        try {
            memberHandler.deleteAccount(memberFinder.findByMail("John.Doe2@mail.com").orElse(null));
        } catch (AccountNotFoundException ignored) {
        }
    }
    @Test
    public void testCreateAccount() throws AlreadyExistingMemberException, MissingInformationException, UnderAgeException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        assertNull(memberFinder.findByMail("John.Doe@mail.com").orElse(null));
        MemberAccount account = memberHandler.createAccount("John Doe", "John.Doe@mail.com", "password", LocalDate.parse("11/04/2001", formatter));
        assertNotNull(memberFinder.findById(account.getId()));
        assertThrows(AlreadyExistingMemberException.class, () -> memberHandler.createAccount("John Doe", "John.Doe@mail.com", "password", LocalDate.parse("11/04/2001", formatter)));
        assertThrows(MissingInformationException.class, () -> memberHandler.createAccount(null, "John.Doe2@mail.com", "password", LocalDate.parse("11/04/2001", formatter)));
        assertThrows(MissingInformationException.class, () -> memberHandler.createAccount("John Doe", null, "password", LocalDate.parse("11/04/2001", formatter)));
        assertThrows(MissingInformationException.class, () -> memberHandler.createAccount("John Doe", "John2.Doe@mail.com", null, LocalDate.parse("11/04/2001", formatter)));
        assertThrows(MissingInformationException.class, () -> memberHandler.createAccount("John Doe", "John2.Doe@mail.com", "password", null));
        assertThrows(UnderAgeException.class, () -> memberHandler.createAccount("John Doe", "John2.Doe@mail.com", "password", LocalDate.parse("11/04/2010", formatter)));
    }

    @Test
    public void testArchiveAccount() throws AlreadyExistingMemberException, UnderAgeException, MissingInformationException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        MemberAccount account = new MemberAccount("John Doe", "John.Doe@mail.com", "password", LocalDate.parse("11/04/2001", formatter),0,0);
        assertThrows( AccountNotFoundException.class, () -> memberHandler.archiveAccount(account));
        MemberAccount account2 = memberHandler.createAccount("John Doe", "John.Doe@mail.com", "password", LocalDate.parse("11/04/2001", formatter));
        assertDoesNotThrow(() -> memberHandler.archiveAccount(account2));
        assertEquals(account2.getStatus(), AccountStatus.EXPIRED);
    }

    @Test
    public void testRestoreAccount() throws AlreadyExistingMemberException, UnderAgeException, MissingInformationException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        MemberAccount account = new MemberAccount("John Doe", "John.Doe@mail.com", "password", LocalDate.parse("11/04/2001", formatter),0,0);
        assertThrows( AccountNotFoundException.class, () -> memberHandler.restoreAccount(account));
        MemberAccount account2 = memberHandler.createAccount("John Doe", "John.Doe@mail.com", "password", LocalDate.parse("11/04/2001", formatter));
        account2.setStatus(AccountStatus.EXPIRED);
        assertDoesNotThrow(() -> memberHandler.restoreAccount(account2));
        assertEquals(account2.getStatus(), AccountStatus.REGULAR);
    }

    @Test
    public void testDeleteAccount() throws AlreadyExistingMemberException, MissingInformationException, UnderAgeException, AccountNotFoundException, AccountNotFoundException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        MemberAccount account = memberHandler.createAccount("John Doe", "John.Doe@mail.com", "password", LocalDate.parse("11/04/2001", formatter));
        assertNotNull(memberFinder.findById(account.getId()));
        memberHandler.deleteAccount(account);
        assertNull(memberFinder.findById(account.getId()).orElse(null));
    }

    @Test
    public void testStartParkingTime() throws AlreadyExistingMemberException, UnderAgeException, MissingInformationException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        MemberAccount account = memberHandler.createAccount("John Doe", "John.Doe@mail.com", "password", LocalDate.parse("11/04/2001", formatter));
        assertThrows(NotVFPException.class, () -> memberHandler.useParkingTime(account, "123456789"));
        account.setStatus(AccountStatus.VFP);
        try {
            memberHandler.useParkingTime(account, "123456789");
        } catch (Exception ignored) {

            verify(parkingHandler).registerParking("123456789");
        }
    }

    @Test
    public void testUpdateAccounts() throws AlreadyExistingMemberException, UnderAgeException, MissingInformationException, AccountNotFoundException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        memberHandler.createAccount("John Doe", "John.Doe@mail.com", "password", LocalDate.parse("11/04/2001", formatter));
        memberHandler.createAccount("John Doe2", "John.Doe2@mail.com", "password", LocalDate.parse("11/04/2001", formatter));

        transactionRepository.save(new Purchase(LocalDate.now(),memberFinder.findByMail("John.Doe@mail.com").orElse(null),new ArrayList<>()));
        transactionRepository.save(new Purchase(LocalDate.now(),memberFinder.findByMail("John.Doe@mail.com").orElse(null),new ArrayList<>()));
        transactionRepository.save(new Purchase(LocalDate.now(),memberFinder.findByMail("John.Doe@mail.com").orElse(null),new ArrayList<>()));
        transactionRepository.save(new Purchase(LocalDate.now(),memberFinder.findByMail("John.Doe@mail.com").orElse(null),new ArrayList<>()));
        transactionRepository.save(new Purchase(LocalDate.now(),memberFinder.findByMail("John.Doe@mail.com").orElse(null),new ArrayList<>()));
        transactionRepository.save(new Purchase(LocalDate.now(),memberFinder.findByMail("John.Doe2@mail.com").orElse(null),new ArrayList<>()));
        transactionRepository.save(new Purchase(LocalDate.now(),memberFinder.findByMail("John.Doe2@mail.com").orElse(null),new ArrayList<>()));
        transactionRepository.save(new Purchase(LocalDate.now(),memberFinder.findByMail("John.Doe2@mail.com").orElse(null),new ArrayList<>()));


        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() ->
                verify(memberHandler, Mockito.atLeast(4)).updateAccountsStatus());
        assertEquals(memberFinder.findByMail("John.Doe@mail.com").orElse(null).getStatus(), AccountStatus.VFP);
        assertEquals(memberFinder.findByMail("John.Doe2@mail.com").orElse(null).getStatus(), AccountStatus.REGULAR);
        transactionRepository.deleteAll();
        memberHandler.deleteAccount(memberFinder.findByMail("John.Doe@mail.com").orElse(null));
        memberHandler.deleteAccount(memberFinder.findByMail("John.Doe2@mail.com").orElse(null));

    }

    @Test
    public void testUpdateAccounts2() throws AlreadyExistingMemberException, UnderAgeException, MissingInformationException, AccountNotFoundException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        memberHandler.createAccount("John Doe", "John.Doe@mail.com", "password", LocalDate.parse("11/04/2001", formatter));
        memberHandler.createAccount("John Doe2", "John.Doe2@mail.com", "password", LocalDate.parse("11/04/2001", formatter));
        memberHandler.updateAccountStatus(memberFinder.findByMail("John.Doe2@mail.com").orElse(null),AccountStatus.VFP);

        transactionRepository.save(new Purchase(LocalDate.now(),memberFinder.findByMail("John.Doe@mail.com").orElse(null),new ArrayList<>()));
        transactionRepository.save(new Purchase(LocalDate.now(),memberFinder.findByMail("John.Doe@mail.com").orElse(null),new ArrayList<>()));
        transactionRepository.save(new Purchase(LocalDate.now(),memberFinder.findByMail("John.Doe@mail.com").orElse(null),new ArrayList<>()));
        transactionRepository.save(new Purchase(LocalDate.now(),memberFinder.findByMail("John.Doe@mail.com").orElse(null),new ArrayList<>()));
        transactionRepository.save(new Purchase(LocalDate.now(),memberFinder.findByMail("John.Doe@mail.com").orElse(null),new ArrayList<>()));
        transactionRepository.save(new Purchase(LocalDate.now(),memberFinder.findByMail("John.Doe2@mail.com").orElse(null),new ArrayList<>()));
        transactionRepository.save(new Purchase(LocalDate.now(),memberFinder.findByMail("John.Doe2@mail.com").orElse(null),new ArrayList<>()));
        transactionRepository.save(new Purchase(LocalDate.now(),memberFinder.findByMail("John.Doe2@mail.com").orElse(null),new ArrayList<>()));


        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() ->
                verify(memberHandler, Mockito.atLeast(4)).updateAccountsStatus());
        assertEquals(memberFinder.findByMail("John.Doe@mail.com").orElse(null).getStatus(), AccountStatus.VFP);
        assertEquals(memberFinder.findByMail("John.Doe2@mail.com").orElse(null).getStatus(), AccountStatus.REGULAR);
        transactionRepository.deleteAll();
        memberHandler.deleteAccount(memberFinder.findByMail("John.Doe@mail.com").orElse(null));
        memberHandler.deleteAccount(memberFinder.findByMail("John.Doe2@mail.com").orElse(null));
    }



}
