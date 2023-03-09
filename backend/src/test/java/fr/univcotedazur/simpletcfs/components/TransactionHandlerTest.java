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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
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
        assertNull(memberFinder.findByMail(mail).orElse(null));
         account = memberHandler.createAccount(name, mail, "password", LocalDate.parse("11/04/2001", formatter));
        assertNotNull(memberFinder.findById(account.getId()));

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
        Product product3=new Product("ring",1.0,10);
        Shop shop=new Shop("A", "1 rue de la paix",new HashMap<>(),new ArrayList<>(),new ArrayList<>());
        product3.setShop(shop);
        gift.setShop(shop);
        shop.addGift(gift);

        shopRepository.save(shop);
        catalogRepository.save(product3);
        shop.addProduct(product3);
        giftRepository.save(gift);
        Item item=new Item(product3,2);
        Purchase tran=new Purchase(LocalDate.now(),account,List.of(item));
        tran.setMemberAccount(account);
        item.setPurchase(tran);
        tran.addItem(item);
        purchaseRepository.save(tran);
        itemRepository.save(item);
        assertDoesNotThrow(()-> transactionHandler.processPointsUsage(account,transaction));
        assertEquals(50, account.getPoints());
        assertTrue(transactionRepository.existsById(transaction.getId()));
        assertTrue(transactionRepository.findById(transaction.getId()).isPresent());

    }
    @Test
    public void processPointsUsageTest1()throws AlreadyExistingMemberException, MissingInformationException, UnderAgeException{
        purchaseRepository.deleteAll();
        transactionRepository.deleteAll();
         setUp("joel.Doe@mail.com","joel");
        account.setPoints(10);
        UsePoints transaction=new UsePoints(LocalDate.now(),account);
        Gift gift=new Gift();
        gift.setRequiredStatus(AccountStatus.VFP);
        transaction.setGift(gift);
        transaction.setUsedPoints(100);
        account.setStatus(AccountStatus.VFP);
        Product product3=new Product("ring",1.0,10);
        Shop shop=new Shop("A", "1 rue de la paix",new HashMap<>(),new ArrayList<>(),new ArrayList<>());
        product3.setShop(shop);
        gift.setShop(shop);
        shop.addGift(gift);
        shopRepository.save(shop);
        shop.addProduct(product3);
        catalogRepository.save(product3);
        giftRepository.save(gift);
        Item item=new Item(product3,2);
        Purchase tran=new Purchase(LocalDate.now(),account,List.of(item));
        tran.setMemberAccount(account);
        purchaseRepository.save(tran);
        item.setPurchase(tran);
        tran.addItem(item);
        itemRepository.save(item);
        assertThrows(InsufficientPointsException.class,()-> transactionHandler.processPointsUsage(account,transaction));
        assertEquals(10, account.getPoints());
        assertNull(transaction.getId());
    }
    @Test
    public void processPointsUsageTest2()throws AlreadyExistingMemberException, MissingInformationException, UnderAgeException{
        itemRepository.deleteAll();
         transactionRepository.deleteAll();
        purchaseRepository.deleteAll();
        memberAccoutRepository.deleteAll();

        setUp("joel.Doe@mail.com","joel");
        account.setPoints(150);
        UsePoints transaction=new UsePoints(LocalDate.now(),account);
        Gift gift=new Gift();
        gift.setRequiredStatus(AccountStatus.VFP);
        transaction.setGift(gift);
        transaction.setUsedPoints(100);
        account.setStatus(AccountStatus.REGULAR);
        Product product3=new Product("ring",1.0,10);
        Shop shop=new Shop("A", "1 rue de la paix",new HashMap<>(),new ArrayList<>(),new ArrayList<>());
        product3.setShop(shop);
        gift.setShop(shop);
        shop.addGift(gift);
        shopRepository.save(shop);
        shop.addProduct(product3);
        catalogRepository.save(product3);
        giftRepository.save(gift);
        Item item=new Item(product3,2);
        Purchase tran=new Purchase(LocalDate.now(),account,List.of(item));
        tran.setMemberAccount(account);
        tran.addItem(item);
        item.setPurchase(tran);
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
        transactionRepository.deleteAll();
        memberAccoutRepository.deleteAll();
        catalogRepository.deleteAll();
        shopRepository.deleteAll();
        itemRepository.deleteAll();
        Product product3=new Product("ring",1.0,10);
        Shop shop=new Shop("A", "1 rue de la paix",new HashMap<>(),new ArrayList<>(),new ArrayList<>());
        product3.setShop(shop);

        shopRepository.save(shop);
        shop.addProduct(product3);
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
        transactionRepository.save(purchaseOfJohn);
        itemRepository.save(item);
        // Mocking the bank proxy
        when(bankMock.pay(eq("1234567999123456"), anyDouble())).thenReturn(true);
        transactionHandler.processPurchase(john, purchaseOfJohn,"1234567999123456");
        assertEquals(purchaseOfJohn.getEarnedPoints(), john.getPoints());
        assertTrue(transactionRepository.existsById(purchaseOfJohn.getId()));
    }
    @Test
    public void processPurchaseTest1()throws Exception{
        transactionRepository.deleteAll();
        memberAccoutRepository.deleteAll();
        Product product=new Product("cake",1.0,10);
        purchaseOfPat=new Purchase(LocalDate.now(),account,List.of(new Item(product,5)));
        pat = memberHandler.createAccount("pat", "pat.d@gmail.com", "password", LocalDate.parse("11/04/2001", formatter));
        assertNotNull(memberFinder.findById(pat.getId()));
        // Mocking the bank proxy
        when(bankMock.pay(eq("1234567999123456"), anyDouble())).thenReturn(false);
        assertThrows(PaymentException.class,()-> transactionHandler.processPurchase(pat, purchaseOfPat,"1234567999123456"));
        assertNull(purchaseOfPat.getId());
        assertEquals(0, pat.getPoints());
        assertNotEquals(purchaseOfPat.getEarnedPoints(), pat.getPoints());
    }
    @Test
    public void processPurchaseTest2(){
        assertThrows(AccountNotFoundException.class,()-> transactionHandler.processPurchase( new MemberAccount("john","mail","pass",LocalDate.of(2001,11,04),0,0), purchaseOfPat,"1234567999123456"));
    }
}
