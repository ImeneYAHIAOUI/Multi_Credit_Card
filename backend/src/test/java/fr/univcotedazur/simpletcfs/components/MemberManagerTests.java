package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.entities.*;
import fr.univcotedazur.simpletcfs.exceptions.*;
import fr.univcotedazur.simpletcfs.interfaces.*;
import fr.univcotedazur.simpletcfs.repositories.CatalogRepository;
import fr.univcotedazur.simpletcfs.repositories.ShopRepository;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.TestPropertySource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(properties = {"VFP.updateRate.cron=*/1 * * * * *","VFP.MinPurchasesNumber=5"})
@Commit
@Transactional
public class MemberManagerTests {

    @Autowired
    TransactionProcessor transactionProcessor;
    @SpyBean
    MemberHandler memberHandler;
    @Autowired
    MemberFinder memberFinder;
    @MockBean
    ISWUPLS iswupls;

    @MockBean
    Bank bank;

    @Autowired
    ShopRepository shopRepository;

    @Autowired
    CatalogRepository catalogRepository;





    @Test
    public void testCreateAccount() throws AlreadyExistingMemberException, MissingInformationException, UnderAgeException, AccountNotFoundException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        MemberAccount account;
        try {
             account = memberHandler.createAccount("John Doe", "John.Doe@mail.com", "password", LocalDate.parse("11/04/2001", formatter));
        }catch (AlreadyExistingMemberException e){
            memberHandler.deleteAccount(memberFinder.findByMail("John.Doe@mail.com").get());
             account = memberHandler.createAccount("John Doe", "John.Doe@mail.com", "password", LocalDate.parse("11/04/2001", formatter));
        }
        assertNotNull(memberFinder.findById(account.getId()));
        assertThrows(AlreadyExistingMemberException.class, () -> memberHandler.createAccount("John Doe", "John.Doe@mail.com", "password", LocalDate.parse("11/04/2001", formatter)));
        assertThrows(MissingInformationException.class, () -> memberHandler.createAccount(null, "John.Doe2@mail.com", "password", LocalDate.parse("11/04/2001", formatter)));
        assertThrows(MissingInformationException.class, () -> memberHandler.createAccount("John Doe", null, "password", LocalDate.parse("11/04/2001", formatter)));
        assertThrows(MissingInformationException.class, () -> memberHandler.createAccount("John Doe", "John2.Doe@mail.com", null, LocalDate.parse("11/04/2001", formatter)));
        assertThrows(MissingInformationException.class, () -> memberHandler.createAccount("John Doe", "John2.Doe@mail.com", "password", null));
        assertThrows(UnderAgeException.class, () -> memberHandler.createAccount("John Doe", "John2.Doe@mail.com", "password", LocalDate.parse("11/04/2010", formatter)));
    }
    @Test
    public void testArchiveAccount() throws AlreadyExistingMemberException, UnderAgeException, MissingInformationException, AccountNotFoundException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        MemberAccount account = new MemberAccount("John Doe", "John.Doe@mail.com", "password", LocalDate.parse("11/04/2001", formatter),0,0);
        assertThrows( AccountNotFoundException.class, () -> memberHandler.archiveAccount(account));
        MemberAccount account2;
        try {
            account2 = memberHandler.createAccount("John Doe", "John.Doe@mail.com", "password", LocalDate.parse("11/04/2001", formatter));
        }catch (AlreadyExistingMemberException e){
            memberHandler.deleteAccount(memberFinder.findByMail("John.Doe@mail.com").get());
            account2 = memberHandler.createAccount("John Doe", "John.Doe@mail.com", "password", LocalDate.parse("11/04/2001", formatter));
        }
        MemberAccount finalAccount = account2;
        assertDoesNotThrow(() -> memberHandler.archiveAccount(finalAccount));
        assertEquals(account2.getStatus(), AccountStatus.EXPIRED);
    }
    @Test
    public void testRestoreAccount() throws AlreadyExistingMemberException, UnderAgeException, MissingInformationException, AccountNotFoundException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        MemberAccount account = new MemberAccount("John Doe", "John.Doe@mail.com", "password", LocalDate.parse("11/04/2001", formatter),0,0);
        assertThrows( AccountNotFoundException.class, () -> memberHandler.restoreAccount(account));
        MemberAccount account2;
        try {
            account2 = memberHandler.createAccount("John Doe", "John.Doe@mail.com", "password", LocalDate.parse("11/04/2001", formatter));
        }catch (AlreadyExistingMemberException e){
            memberHandler.deleteAccount(memberFinder.findByMail("John.Doe@mail.com").get());
            account2 = memberHandler.createAccount("John Doe", "John.Doe@mail.com", "password", LocalDate.parse("11/04/2001", formatter));
        }
        account2.setStatus(AccountStatus.EXPIRED);
        MemberAccount finalAccount = account2;
        assertDoesNotThrow(() -> memberHandler.restoreAccount(finalAccount));
        assertEquals(account2.getStatus(), AccountStatus.REGULAR);
    }
    @Test
    public void testDeleteAccount() throws AlreadyExistingMemberException, MissingInformationException, UnderAgeException, AccountNotFoundException, AccountNotFoundException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        MemberAccount account;
        try {
            account = memberHandler.createAccount("John Doe", "John.Doe@mail.com", "password", LocalDate.parse("11/04/2001", formatter));
        }catch (AlreadyExistingMemberException e){
            memberHandler.deleteAccount(memberFinder.findByMail("John.Doe@mail.com").get());
            account = memberHandler.createAccount("John Doe", "John.Doe@mail.com", "password", LocalDate.parse("11/04/2001", formatter));
        }
        assertNotNull(memberFinder.findById(account.getId()));
        memberHandler.deleteAccount(account);
        assertNull(memberFinder.findById(account.getId()).orElse(null));
        memberHandler.deleteAccount(account);
    }




    @Test
    public void testUpdateAccounts2() throws AlreadyExistingMemberException, UnderAgeException, MissingInformationException, AccountNotFoundException, PaymentException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

        when(bank.pay(anyString(),anyDouble())).thenReturn(true);

        MemberAccount account;
        try {
            account = memberHandler.createAccount("John Doe", "John.Doe@mail.com", "password", LocalDate.parse("11/04/2001", formatter));
        }catch (AlreadyExistingMemberException e){
            memberHandler.deleteAccount(memberFinder.findByMail("John.Doe@mail.com").get());
            account = memberHandler.createAccount("John Doe", "John.Doe@mail.com", "password", LocalDate.parse("11/04/2001", formatter));
        }
        MemberAccount account2;
        try {
            account2 = memberHandler.createAccount("John Doe", "John.Doe2@mail.com", "password", LocalDate.parse("11/04/2001", formatter));
        }catch (AlreadyExistingMemberException e){
            memberHandler.deleteAccount(memberFinder.findByMail("John.Doe2@mail.com").get());
            account2 = memberHandler.createAccount("John Doe", "John.Doe2@mail.com", "password", LocalDate.parse("11/04/2001", formatter));
        }
        memberHandler.updateAccountStatus(account,AccountStatus.VFP);
        memberHandler.updateAccountStatus(account2,AccountStatus.VFP);

        Product product=new Product("ring",1.0,10,0.0);
        Shop shop=new Shop("A", "1 rue de la paix");
        product.setShop(shop);
        shopRepository.save(shop);
        catalogRepository.save(product);
        Item item=new Item(product,2);
        Purchase purchase =new Purchase(LocalDate.now(),account, List.of(item));
        purchase.setMemberAccount(account);
        item.setPurchase(purchase);
        purchase.addItem(item);
        purchase.setShop(shop);


        Purchase purchase2 =new Purchase(LocalDate.now(),account, List.of(item));
        purchase2.setMemberAccount(account2);
        item.setPurchase(purchase2);
        purchase2.addItem(item);
        purchase2.setShop(shop);

        transactionProcessor.processPurchaseWithCreditCard(account,purchase,"123456456789");
        transactionProcessor.processPurchaseWithCreditCard(account, purchase,"123456456789");
        transactionProcessor.processPurchaseWithCreditCard(account, purchase,"123456456789");
        transactionProcessor.processPurchaseWithCreditCard(account,purchase,"123456456789");
        transactionProcessor.processPurchaseWithCreditCard(account,purchase,"123456456789");

        transactionProcessor.processPurchaseWithCreditCard(account2,purchase2,"123456456789");
        transactionProcessor.processPurchaseWithCreditCard(account2, purchase2,"123456456789");
        transactionProcessor.processPurchaseWithCreditCard(account2, purchase2,"123456456789");
        transactionProcessor.processPurchaseWithCreditCard(account2,purchase2,"123456456789");

        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() ->
                verify(memberHandler, Mockito.atLeast(4)).updateAccountsStatus());

        memberHandler.updateAccountsStatus();

        assertEquals(memberFinder.findById(account.getId()).orElse(null).getStatus(), AccountStatus.VFP);
        assertEquals(memberFinder.findById(account2.getId()).orElse(null).getStatus(), AccountStatus.REGULAR);

        memberHandler.deleteAccount(account);
        memberHandler.deleteAccount(account2);


    }





}
