package fr.univcotedazur.multicredit.components;

import fr.univcotedazur.multicredit.entities.*;
import fr.univcotedazur.multicredit.exceptions.*;
import fr.univcotedazur.multicredit.interfaces.Bank;
import fr.univcotedazur.multicredit.interfaces.MemberFinder;
import fr.univcotedazur.multicredit.interfaces.MemberHandler;
import fr.univcotedazur.multicredit.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource(properties = {"VFP.MinPurchasesNumber=5"})
@Transactional
class TransactionHandlerTest {
    @Autowired
    MemberFinder memberFinder;
    MemberAccount account;
    @Autowired
    TransactionHandler transactionHandler;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    GiftRepository giftRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ShopRepository shopRepository;
    @Autowired
    CatalogRepository catalogRepository;
    @Autowired
    PurchaseRepository purchaseRepository;
    @Autowired
    UsePointsRepository usePointsRepository;
    Purchase purchaseOfJohn;
    MemberAccount john;
    MemberAccount pat;
    Purchase purchaseOfPat;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
    @Autowired
    MemberRepository memberAccoutRepository;
    @Autowired
    MemberHandler memberHandler;
    @MockBean
    private Bank bankMock;

    void setUp(String mail, String name) throws MissingInformationException, UnderAgeException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        transactionRepository.deleteAll();
        itemRepository.deleteAll();
        giftRepository.deleteAll();
        memberAccoutRepository.deleteAll();
        shopRepository.deleteAll();
        catalogRepository.deleteAll();
        try {
            account = memberHandler.createAccount(name, mail, "password", LocalDate.parse("11/04/2001", formatter));
            assertNotNull(memberFinder.findById(account.getId()));
        } catch (AlreadyExistingMemberException e) {
            account = memberFinder.findByMail(mail).orElse(null);
        }
    }

    @Test
    void removePointsTest() throws MissingInformationException, UnderAgeException {
        setUp("John.Doe@mail.com", "John");
        account.setPoints(100);
        UsePoints transaction = new UsePoints(LocalDate.now(), account);
        transaction.setUsedPoints(50);
        assertDoesNotThrow(() -> transactionHandler.removePoints(account, transaction));
        assertEquals(50, account.getPoints());
    }

    @Test
    void removePointsTest1() throws UnderAgeException, MissingInformationException {
        setUp("John.Doe@mail.com", "John");
        account = memberFinder.findByMail("John.Doe@mail.com").orElse(null);
        if (account == null) throw new AssertionError();

        account.setPoints(10);
        UsePoints transaction = new UsePoints(LocalDate.now(), account);
        transaction.setUsedPoints(50);
        assertThrows(InsufficientPointsException.class, () -> transactionHandler.removePoints(account, transaction));
        assertEquals(10, account.getPoints());
    }

    @Test
    void addPointsTest() throws AlreadyExistingMemberException, UnderAgeException, MissingInformationException {
        setUp("John.Doe@mail.com", "John");
        account = memberFinder.findByMail("John.Doe@mail.com").orElse(null);
        if (account == null) throw new AssertionError();

        account.setPoints(100);
        Product product3 = new Product("ring", 1.0, 10, 0.0);
        Purchase transaction = new Purchase(LocalDate.now(), account, List.of(new Item(product3, 2)));
        assertDoesNotThrow(() -> transactionHandler.addPoints(account, transaction));
        assertEquals(120, account.getPoints());
    }

    @Test
    void processPointsUsageTest() throws AlreadyExistingMemberException, MissingInformationException, UnderAgeException {
        setUp("John.Doe@mail.com", "John");
        account.setPoints(100);
        UsePoints transaction = new UsePoints(LocalDate.now(), account);
        Gift gift = new Gift();
        gift.setRequiredStatus(AccountStatus.VFP);
        transaction.setGift(gift);
        transaction.setUsedPoints(50);
        account.setStatus(AccountStatus.VFP);
        Product product3 = new Product("ring", 1.0, 10, 0.0);
        Shop shop = new Shop("A", "1 rue de la paix");
        product3.setShop(shop);
        gift.setShop(shop);
        shopRepository.save(shop);
        shop.addProduct(product3);
        catalogRepository.save(product3);
        shop.addGift(gift);
        giftRepository.save(gift);
        Item item = new Item(product3, 2);
        Purchase tran = new Purchase(LocalDate.now(), account, List.of(item));
        tran.setMemberAccount(account);
        item.setPurchase(tran);
        tran.addItem(item);
        tran.setShop(shop);
        account.getTransactions().add(tran);
        purchaseRepository.save(tran);
        itemRepository.save(item);
        transaction.setShop(shop);
        assertDoesNotThrow(() -> transactionHandler.processPointsUsage(account, transaction));
        assertEquals(50, account.getPoints());
        assertTrue(transactionRepository.existsById(transaction.getId()));
        assertTrue(transactionRepository.findById(transaction.getId()).isPresent());
        assertTrue(usePointsRepository.existsById(transaction.getId()));
        assertTrue(usePointsRepository.findById(transaction.getId()).isPresent());
    }

    @Test
    void getStatisticsOnClientUsageTest() throws UnderAgeException, MissingInformationException {
        setUp("John.Doe@mail.com", "John");
        assertTrue(transactionHandler.getStatisticsOnClientUsage(account).isEmpty());
        account.setPoints(100);
        UsePoints transaction = new UsePoints(LocalDate.now(), account);
        Gift gift = new Gift();
        gift.setRequiredStatus(AccountStatus.VFP);
        transaction.setGift(gift);
        transaction.setUsedPoints(50);
        account.setStatus(AccountStatus.VFP);
        Product product3 = new Product("ring", 1.0, 10, 0.0);
        Shop shop = new Shop("A", "1 rue de la paix");
        product3.setShop(shop);
        gift.setShop(shop);
        shopRepository.save(shop);
        shop.addProduct(product3);
        catalogRepository.save(product3);
        shop.addGift(gift);
        giftRepository.save(gift);
        Item item = new Item(product3, 2);
        Purchase tran = new Purchase(LocalDate.now(), account, List.of(item));
        tran.setMemberAccount(account);
        item.setPurchase(tran);
        tran.addItem(item);
        tran.setShop(shop);
        account.getTransactions().add(tran);
        purchaseRepository.save(tran);
        itemRepository.save(item);
        transaction.setMemberAccount(account);
        transaction.setShop(shop);
        transactionRepository.save(transaction);
        assertEquals(2, (long) transactionHandler.getStatisticsOnClientUsage(account).size());
    }

    @Test
    void getStatisticsOnClientUsageTest2() throws UnderAgeException, MissingInformationException {
        setUp("John.Doe@mail.com", "John");
        assertTrue(transactionHandler.getStatisticsOnClientUsage(account).isEmpty());
        account.setPoints(100);
        UsePoints transaction = new UsePoints(LocalDate.now(), account);
        Gift gift = new Gift();
        gift.setRequiredStatus(AccountStatus.VFP);
        transaction.setGift(gift);
        transaction.setUsedPoints(50);
        account.setStatus(AccountStatus.VFP);
        Product product3 = new Product("ring", 1.0, 10, 0.0);
        Shop shop = new Shop("A", "1 rue de la paix");
        product3.setShop(shop);
        gift.setShop(shop);
        shopRepository.save(shop);
        shop.addProduct(product3);
        catalogRepository.save(product3);
        shop.addGift(gift);
        giftRepository.save(gift);
        transaction.setMemberAccount(account);
        transaction.setShop(shop);
        transactionRepository.save(transaction);
        assertEquals(1, (long) transactionHandler.getStatisticsOnClientUsage(account).size());
    }

    @Test
    void getStatisticsOnClientUsageAtShopTest3() throws UnderAgeException, MissingInformationException {
        setUp("John.Doe@mail.com", "John");
        Shop shop = new Shop("A", "1 rue de la paix");
        shopRepository.save(shop);
        Shop shop1 = new Shop("4", "5 rue de la paix");
        shopRepository.save(shop1);
        assertTrue(transactionHandler.getStatisticsOnClientUsageAtShop(shop, account).isEmpty());
        assertTrue(transactionHandler.getStatisticsOnClientUsageAtShop(shop1, account).isEmpty());

        account.setPoints(100);
        UsePoints transaction = new UsePoints(LocalDate.now(), account);
        Gift gift = new Gift();
        gift.setRequiredStatus(AccountStatus.VFP);
        transaction.setGift(gift);
        transaction.setUsedPoints(50);
        account.setStatus(AccountStatus.VFP);
        Product product3 = new Product("ring", 1.0, 10, 0.0);
        product3.setShop(shop);
        gift.setShop(shop);
        shop.addProduct(product3);
        catalogRepository.save(product3);
        shop.addGift(gift);
        giftRepository.save(gift);
        transaction.setShop(shop);
        Item item = new Item(product3, 2);
        Purchase tran = new Purchase(LocalDate.now(), account, List.of(item));
        tran.setMemberAccount(account);
        item.setPurchase(tran);
        tran.addItem(item);
        tran.setShop(shop1);
        account.getTransactions().add(tran);
        purchaseRepository.save(tran);
        itemRepository.save(item);
        transaction.setMemberAccount(account);
        transaction.setShop(shop);
        transactionRepository.save(transaction);
        assertEquals(1, (long) transactionHandler.getStatisticsOnClientUsageAtShop(shop, account).size());
        assertEquals(1, (long) transactionHandler.getStatisticsOnClientUsageAtShop(shop1, account).size());
    }

    @Test
    void processPointsUsageTest1() throws MissingInformationException, UnderAgeException {

        setUp("joel.Doe@mail.com", "joel");
        account.setPoints(10);
        UsePoints transaction = new UsePoints(LocalDate.now(), account);
        Gift gift = new Gift();
        gift.setRequiredStatus(AccountStatus.VFP);
        transaction.setGift(gift);
        transaction.setUsedPoints(100);
        account.setStatus(AccountStatus.VFP);
        Product product3 = new Product("ring", 1.0, 10, 0.0);
        Shop shop = new Shop("A", "1 rue de la paix");
        product3.setShop(shop);
        gift.setShop(shop);
        shopRepository.save(shop);
        shop.addProduct(product3);
        catalogRepository.save(product3);
        shop.addGift(gift);
        giftRepository.save(gift);
        Item item = new Item(product3, 2);
        Purchase tran = new Purchase(LocalDate.now(), account, List.of(item));
        tran.setMemberAccount(account);
        tran.setShop(shop);
        purchaseRepository.save(tran);
        item.setPurchase(tran);
        tran.addItem(item);
        itemRepository.save(item);
        account.getTransactions().add(tran);
        assertThrows(InsufficientPointsException.class, () -> transactionHandler.processPointsUsage(account, transaction));
        assertEquals(10, account.getPoints());
        assertNull(transaction.getId());
    }

    @Test
    void processPointsUsageTest2() throws MissingInformationException, UnderAgeException {

        setUp("joel.Doe@mail.com", "joel");
        account.setPoints(150);
        UsePoints transaction = new UsePoints(LocalDate.now(), account);
        Gift gift = new Gift();
        gift.setRequiredStatus(AccountStatus.VFP);
        transaction.setGift(gift);
        transaction.setUsedPoints(100);
        account.setStatus(AccountStatus.REGULAR);
        Product product3 = new Product("ring", 1.0, 10, 0.0);
        Shop shop = new Shop("A", "1 rue de la paix");
        product3.setShop(shop);
        gift.setShop(shop);
        shopRepository.save(shop);
        shop.addProduct(product3);
        catalogRepository.save(product3);
        shop.addGift(gift);
        giftRepository.save(gift);
        Item item = new Item(product3, 2);
        Purchase tran = new Purchase(LocalDate.now(), account, List.of(item));
        tran.setMemberAccount(account);
        tran.addItem(item);
        item.setPurchase(tran);
        tran.setShop(shop);
        transactionRepository.save(tran);
        itemRepository.save(item);
        assertThrows(DeclinedTransactionException.class, () -> transactionHandler.processPointsUsage(account, transaction));
        assertEquals(150, account.getPoints());
        assertNull(transaction.getId());
    }

    @Test
    void processPointsUsageTest3() {
        account = new MemberAccount("john", "mail", "pass", LocalDate.of(2001, 11, 04), 0, 0);
        UsePoints transaction = new UsePoints(LocalDate.now(), account);
        transaction.setUsedPoints(100);
        assertThrows(AccountNotFoundException.class, () -> transactionHandler.processPointsUsage(account, transaction));
    }

    @Test
    void processPurchaseTest() throws Exception {
        Product product3 = new Product("ring", 1.0, 10, 0.0);
        Shop shop = new Shop("A", "1 rue de la paix");
        product3.setShop(shop);
        shopRepository.save(shop);
        catalogRepository.save(product3);
        Item item = new Item(product3, 2);
        purchaseOfJohn = new Purchase(LocalDate.now(), account, List.of(item));
        assertNull(memberFinder.findByMail("john.d@gmail.com").orElse(null));
        john = memberHandler.createAccount("john", "john.d@gmail.com", "password", LocalDate.parse("11/04/2001", formatter));
        assertNotNull(memberFinder.findById(john.getId()));
        assertEquals(0, john.getPoints());
        purchaseOfJohn.setMemberAccount(john);
        item.setPurchase(purchaseOfJohn);
        purchaseOfJohn.addItem(item);
        purchaseOfJohn.setShop(shop);
        transactionRepository.save(purchaseOfJohn);
        itemRepository.save(item);
        // Mocking the bank proxy
        when(bankMock.pay(eq("1234567999123456"), anyDouble())).thenReturn(true);
        transactionHandler.processPurchaseWithCreditCard(john, purchaseOfJohn, "1234567999123456");
        assertEquals(purchaseOfJohn.getEarnedPoints(), john.getPoints());
        assertTrue(transactionRepository.existsById(purchaseOfJohn.getId()));
        assertTrue(transactionRepository.findById(purchaseOfJohn.getId()).isPresent());
        assertTrue(purchaseRepository.existsById(purchaseOfJohn.getId()));
        assertTrue(purchaseRepository.findById(purchaseOfJohn.getId()).isPresent());
    }

    @Test
    void attributeVFPStatus() throws AlreadyExistingMemberException, UnderAgeException, MissingInformationException, AccountNotFoundException, PaymentException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        MemberAccount account = memberHandler.createAccount("John Doe", "John.Doe@mail.com", "password", LocalDate.parse("11/04/2001", formatter));

        when(bankMock.pay(anyString(), anyDouble())).thenReturn(true);

        Product product = new Product("ring", 1.0, 10, 0.0);
        Shop shop = new Shop("A", "1 rue de la paix");
        product.setShop(shop);
        shopRepository.save(shop);
        catalogRepository.save(product);
        Item item = new Item(product, 2);
        Purchase purchase = new Purchase(LocalDate.now(), account, List.of(item));
        purchase.setMemberAccount(account);
        item.setPurchase(purchase);
        purchase.addItem(item);
        purchase.setShop(shop);


        assertEquals(AccountStatus.REGULAR, Objects.requireNonNull(memberFinder.findById(account.getId()).orElse(null)).getStatus());
        transactionHandler.processPurchaseWithCreditCard(account, purchase, "123456456789");
        assertEquals(AccountStatus.REGULAR, Objects.requireNonNull(memberFinder.findById(account.getId()).orElse(null)).getStatus());
        transactionHandler.processPurchaseWithCreditCard(account, purchase, "123456456789");
        assertEquals(AccountStatus.REGULAR, Objects.requireNonNull(memberFinder.findById(account.getId()).orElse(null)).getStatus());
        transactionHandler.processPurchaseWithCreditCard(account, purchase, "123456456789");
        assertEquals(AccountStatus.REGULAR, Objects.requireNonNull(memberFinder.findById(account.getId()).orElse(null)).getStatus());
        transactionHandler.processPurchaseWithCreditCard(account, purchase, "123456456789");
        assertEquals(AccountStatus.REGULAR, Objects.requireNonNull(memberFinder.findById(account.getId()).orElse(null)).getStatus());
        transactionHandler.processPurchaseWithCreditCard(account, purchase, "123456456789");
        assertEquals(AccountStatus.VFP, Objects.requireNonNull(memberFinder.findById(account.getId()).orElse(null)).getStatus());
        memberHandler.deleteAccount(account);
    }

    @Test
    void processPurchaseTest1() throws Exception {
        Product product = new Product("cake", 1.0, 10, 0.0);
        purchaseOfPat = new Purchase(LocalDate.now(), account, List.of(new Item(product, 5)));
        pat = memberHandler.createAccount("pat", "pat.d@gmail.com", "password", LocalDate.parse("11/04/2001", formatter));
        assertNotNull(memberFinder.findById(pat.getId()));
        when(bankMock.pay(eq("1234567999123456"), anyDouble())).thenReturn(false);
        assertThrows(PaymentException.class, () -> transactionHandler.processPurchaseWithCreditCard(pat, purchaseOfPat, "1234567999123456"));
        assertNull(purchaseOfPat.getId());
        assertEquals(0, pat.getPoints());
        assertNotEquals(purchaseOfPat.getEarnedPoints(), pat.getPoints());
    }

    @Test
    void processPurchaseTest2() {
        assertThrows(AccountNotFoundException.class, () -> transactionHandler.processPurchaseWithCreditCard(new MemberAccount("john", "mail", "pass", LocalDate.of(2001, 11, 04), 0, 0), purchaseOfPat, "1234567999123456"));
    }

    @Test
    void processPurchaseWithCash() throws AlreadyExistingMemberException, UnderAgeException, MissingInformationException, AccountNotFoundException {
        Product product = new Product("cake", 1.0, 10, 0.0);
        purchaseOfPat = new Purchase(LocalDate.now(), account, List.of(new Item(product, 5)));
        Shop shop = new Shop("A", "1 rue de la paix");
        product.setShop(shop);
        shopRepository.save(shop);
        catalogRepository.save(product);
        purchaseOfPat.setShop(shop);
        pat = memberHandler.createAccount("pat", "pat.d@gmail.com", "password", LocalDate.parse("11/04/2001", formatter));
        assertNotNull(memberFinder.findById(pat.getId()));
        transactionHandler.processPurchaseWithCash(pat, purchaseOfPat);
        assertEquals(50, pat.getPoints());
        assertTrue(pat.getTransactions().contains(purchaseOfPat));
    }

    @Test
    void processPurchaseWithMembershipCard() throws AlreadyExistingMemberException, UnderAgeException, MissingInformationException, AccountNotFoundException {
        Product product = new Product("cake", 1.0, 10, 0.0);
        purchaseOfPat = new Purchase(LocalDate.now(), account, List.of(new Item(product, 5)));
        Shop shop = new Shop("A", "1 rue de la paix");
        product.setShop(shop);
        shopRepository.save(shop);
        catalogRepository.save(product);
        purchaseOfPat.setShop(shop);
        pat = memberHandler.createAccount("pat", "pat.d@gmail.com", "password", LocalDate.parse("11/04/2001", formatter));
        assertNotNull(memberFinder.findById(pat.getId()));
        assertThrows(PaymentException.class, () -> transactionHandler.processPurchaseWithMemberCard(pat, purchaseOfPat));
        pat.setBalance(5.0);
        assertDoesNotThrow(() -> transactionHandler.processPurchaseWithMemberCard(pat, purchaseOfPat));
        assertEquals(50, pat.getPoints());
        assertEquals(0.0, pat.getBalance());
        assertTrue(pat.getTransactions().contains(purchaseOfPat));
    }
}
