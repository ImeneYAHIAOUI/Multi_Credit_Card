package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.entities.*;
import fr.univcotedazur.simpletcfs.exceptions.*;
import fr.univcotedazur.simpletcfs.interfaces.Bank;
import fr.univcotedazur.simpletcfs.interfaces.MemberFinder;
import fr.univcotedazur.simpletcfs.interfaces.MemberHandler;
import fr.univcotedazur.simpletcfs.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource(properties = {"VFP.MinPurchasesNumber=5"})
@Transactional
public class TransactionHandlerTest {
    @Autowired
    MemberFinder memberFinder;
    @MockBean
    private Bank bankMock;
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
     void setUp(String mail,String name)throws AlreadyExistingMemberException, MissingInformationException, UnderAgeException {
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
        }
        catch (AlreadyExistingMemberException e){
            account = memberFinder.findByMail(mail).orElse(null);
        }

    }
    @Test
    public void removePointsTest()throws AlreadyExistingMemberException, MissingInformationException, UnderAgeException{
        setUp("John.Doe@mail.com","John");
        account.setPoints(100);
        UsePoints transaction=new UsePoints(LocalDate.now(),account);
        transaction.setUsedPoints(50);
        assertDoesNotThrow(()-> transactionHandler.removePoints(account,transaction));
        assertEquals(50, account.getPoints());
    }
    @Test
    public void removePointsTest1() throws AlreadyExistingMemberException, UnderAgeException, MissingInformationException {
        setUp("John.Doe@mail.com","John");
        account=memberFinder.findByMail("John.Doe@mail.com").orElse(null);
        account.setPoints(10);
        UsePoints transaction=new UsePoints(LocalDate.now(),account);
        transaction.setUsedPoints(50);
        assertThrows(InsufficientPointsException.class,()-> transactionHandler.removePoints(account,transaction));
        assertEquals(10, account.getPoints());
    }

    @Test
    public void addPointsTest() throws AlreadyExistingMemberException, UnderAgeException, MissingInformationException {
        setUp("John.Doe@mail.com","John");
        account=memberFinder.findByMail("John.Doe@mail.com").orElse(null);
        account.setPoints(100);
        Product product3=new Product("ring",1.0,10,0.0);
        Purchase transaction=new Purchase(LocalDate.now(),account,List.of(new Item(product3,2)));
        assertDoesNotThrow(()-> transactionHandler.addPoints(account,transaction));
        assertEquals(120, account.getPoints());
    }
    @Test
    public void processPointsUsageTest() throws AlreadyExistingMemberException, MissingInformationException, UnderAgeException{
         setUp("John.Doe@mail.com","John");
        account.setPoints(100);
        UsePoints transaction=new UsePoints(LocalDate.now(),account);
        Gift gift=new Gift();
        gift.setRequiredStatus(AccountStatus.VFP);
        transaction.setGift(gift);
        transaction.setUsedPoints(50);
        account.setStatus(AccountStatus.VFP);
        Product product3=new Product("ring",1.0,10,0.0);
        Shop shop=new Shop("A", "1 rue de la paix");
        product3.setShop(shop);
        gift.setShop(shop);
        shopRepository.save(shop);
        shop.addProduct(product3);
        catalogRepository.save(product3);
        shop.addGift(gift);
        giftRepository.save(gift);
        Item item=new Item(product3,2);
        Purchase tran=new Purchase(LocalDate.now(),account,List.of(item));
        tran.setMemberAccount(account);
        item.setPurchase(tran);
        tran.addItem(item);
        tran.setShop(shop);
        account.getTransactions().add(tran);
        purchaseRepository.save(tran);
        itemRepository.save(item);
        transaction.setShop(shop);
        assertDoesNotThrow(()-> transactionHandler.processPointsUsage(account,transaction));
        assertEquals(50, account.getPoints());
        assertTrue(transactionRepository.existsById(transaction.getId()));
        assertTrue(transactionRepository.findById(transaction.getId()).isPresent());
        assertTrue(usePointsRepository.existsById(transaction.getId()));
        assertTrue(usePointsRepository.findById(transaction.getId()).isPresent());

    }
    @Test
    public void processPointsUsageTest1()throws AlreadyExistingMemberException, MissingInformationException, UnderAgeException{

         setUp("joel.Doe@mail.com","joel");
        account.setPoints(10);
        UsePoints transaction=new UsePoints(LocalDate.now(),account);
        Gift gift=new Gift();
        gift.setRequiredStatus(AccountStatus.VFP);
        transaction.setGift(gift);
        transaction.setUsedPoints(100);
        account.setStatus(AccountStatus.VFP);
        Product product3=new Product("ring",1.0,10,0.0);
        Shop shop=new Shop("A", "1 rue de la paix");
        product3.setShop(shop);
        gift.setShop(shop);
        shopRepository.save(shop);
        shop.addProduct(product3);
        catalogRepository.save(product3);
        shop.addGift(gift);
        giftRepository.save(gift);
        Item item=new Item(product3,2);
        Purchase tran=new Purchase(LocalDate.now(),account,List.of(item));
        tran.setMemberAccount(account);
        tran.setShop(shop);
        purchaseRepository.save(tran);
        item.setPurchase(tran);
        tran.addItem(item);
        itemRepository.save(item);
        account.getTransactions().add(tran);
        assertThrows(InsufficientPointsException.class,()-> transactionHandler.processPointsUsage(account,transaction));
        assertEquals(10, account.getPoints());
        assertNull(transaction.getId());
    }
    @Test
    public void processPointsUsageTest2()throws AlreadyExistingMemberException, MissingInformationException, UnderAgeException{

        setUp("joel.Doe@mail.com","joel");
        account.setPoints(150);
        UsePoints transaction=new UsePoints(LocalDate.now(),account);
        Gift gift=new Gift();
        gift.setRequiredStatus(AccountStatus.VFP);
        transaction.setGift(gift);
        transaction.setUsedPoints(100);
        account.setStatus(AccountStatus.REGULAR);
        Product product3=new Product("ring",1.0,10,0.0);
        Shop shop=new Shop("A", "1 rue de la paix");
        product3.setShop(shop);
        gift.setShop(shop);
        shopRepository.save(shop);
        shop.addProduct(product3);
        catalogRepository.save(product3);
        shop.addGift(gift);
        giftRepository.save(gift);
        Item item=new Item(product3,2);
        Purchase tran=new Purchase(LocalDate.now(),account,List.of(item));
        tran.setMemberAccount(account);
        tran.addItem(item);
        item.setPurchase(tran);
        tran.setShop(shop);
        transactionRepository.save(tran);
        itemRepository.save(item);
        assertThrows(DeclinedTransactionException.class,()-> transactionHandler.processPointsUsage(account,transaction));
        assertEquals(150, account.getPoints());
        assertNull(transaction.getId());


    }

    @Test
    public void processPointsUsageTest3(){
        account = new MemberAccount("john","mail","pass",LocalDate.of(2001,11,04),0,0);
        UsePoints transaction=new UsePoints(LocalDate.now(),account);
        transaction.setUsedPoints(100);
        assertThrows(AccountNotFoundException.class,()-> transactionHandler.processPointsUsage(account,transaction));
    }
    @Test
    public void processPurchaseTest()throws Exception{
        Product product3=new Product("ring",1.0,10,0.0);
        Shop shop=new Shop("A", "1 rue de la paix");
        product3.setShop(shop);
        shopRepository.save(shop);
        catalogRepository.save(product3);
        Item item=new Item(product3,2);
        purchaseOfJohn=new Purchase(LocalDate.now(),account,List.of(item));
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
        transactionHandler.processPurchaseWithCreditCard(john, purchaseOfJohn,"1234567999123456");
        assertEquals(purchaseOfJohn.getEarnedPoints(), john.getPoints());
        assertTrue(transactionRepository.existsById(purchaseOfJohn.getId()));
        assertTrue(transactionRepository.findById(purchaseOfJohn.getId()).isPresent());
        assertTrue(purchaseRepository.existsById(purchaseOfJohn.getId()));
        assertTrue(purchaseRepository.findById(purchaseOfJohn.getId()).isPresent());
    }

    @Test
    public void attributeVFPStatus() throws AlreadyExistingMemberException, UnderAgeException, MissingInformationException, AccountNotFoundException, PaymentException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        MemberAccount account =  memberHandler.createAccount("John Doe", "John.Doe@mail.com", "password", LocalDate.parse("11/04/2001", formatter));

        when(bankMock.pay(anyString(),anyDouble())).thenReturn(true);

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


        assertEquals(memberFinder.findById(account.getId()).get().getStatus(), AccountStatus.REGULAR);
        transactionHandler.processPurchaseWithCreditCard(account,purchase,"123456456789");
        assertEquals(memberFinder.findById(account.getId()).get().getStatus(), AccountStatus.REGULAR);
        transactionHandler.processPurchaseWithCreditCard(account, purchase,"123456456789");
        assertEquals(memberFinder.findById(account.getId()).get().getStatus(), AccountStatus.REGULAR);
        transactionHandler.processPurchaseWithCreditCard(account, purchase,"123456456789");
        assertEquals(memberFinder.findById(account.getId()).get().getStatus(), AccountStatus.REGULAR);
        transactionHandler.processPurchaseWithCreditCard(account,purchase,"123456456789");
        assertEquals(memberFinder.findById(account.getId()).get().getStatus(), AccountStatus.REGULAR);
        transactionHandler.processPurchaseWithCreditCard(account,purchase,"123456456789");
        assertEquals(memberFinder.findById(account.getId()).get().getStatus(), AccountStatus.VFP);
        memberHandler.deleteAccount(account);


    }
    @Test
    public void processPurchaseTest1()throws Exception{
        Product product=new Product("cake",1.0,10,0.0);
        purchaseOfPat=new Purchase(LocalDate.now(),account,List.of(new Item(product,5)));
        pat = memberHandler.createAccount("pat", "pat.d@gmail.com", "password", LocalDate.parse("11/04/2001", formatter));
        assertNotNull(memberFinder.findById(pat.getId()));
        when(bankMock.pay(eq("1234567999123456"), anyDouble())).thenReturn(false);
        assertThrows(PaymentException.class,()-> transactionHandler.processPurchaseWithCreditCard(pat, purchaseOfPat,"1234567999123456"));
        assertNull(purchaseOfPat.getId());
        assertEquals(0, pat.getPoints());
        assertNotEquals(purchaseOfPat.getEarnedPoints(), pat.getPoints());
    }
    @Test
    public void processPurchaseTest2(){
        assertThrows(AccountNotFoundException.class,()-> transactionHandler.processPurchaseWithCreditCard( new MemberAccount("john","mail","pass",LocalDate.of(2001,11,04),0,0), purchaseOfPat,"1234567999123456"));
    }

    @Test
    public void processPurchaseWithCash() throws AlreadyExistingMemberException, UnderAgeException, MissingInformationException, AccountNotFoundException {
        Product product=new Product("cake",1.0,10,0.0);
        purchaseOfPat=new Purchase(LocalDate.now(),account,List.of(new Item(product,5)));
        pat = memberHandler.createAccount("pat", "pat.d@gmail.com", "password", LocalDate.parse("11/04/2001", formatter));
        assertNotNull(memberFinder.findById(pat.getId()));
        transactionHandler.processPurchaseWithCash(pat,purchaseOfPat);
        assertEquals(50, pat.getPoints());
        assertTrue(pat.getTransactions().contains(purchaseOfPat));

    }

    @Test
    public void processPurchaseWithMembershipCard() throws AlreadyExistingMemberException, UnderAgeException, MissingInformationException, AccountNotFoundException {
        Product product=new Product("cake",1.0,10,0.0);
        purchaseOfPat=new Purchase(LocalDate.now(),account,List.of(new Item(product,5)));
        pat = memberHandler.createAccount("pat", "pat.d@gmail.com", "password", LocalDate.parse("11/04/2001", formatter));
        assertNotNull(memberFinder.findById(pat.getId()));
        assertThrows(PaymentException.class, () -> transactionHandler.processPurchaseWithMemberCard(pat,purchaseOfPat));
        pat.setBalance(5.0);
        assertDoesNotThrow(() -> transactionHandler.processPurchaseWithMemberCard(pat,purchaseOfPat));
        assertEquals(50, pat.getPoints());
        assertEquals(0.0,pat.getBalance());
        assertTrue(pat.getTransactions().contains(purchaseOfPat));

    }
}
