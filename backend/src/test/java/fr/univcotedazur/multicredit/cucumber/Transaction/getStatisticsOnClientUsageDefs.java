package fr.univcotedazur.multicredit.cucumber.Transaction;

import fr.univcotedazur.multicredit.components.TransactionHandler;
import fr.univcotedazur.multicredit.entities.*;
import fr.univcotedazur.multicredit.exceptions.*;
import fr.univcotedazur.multicredit.interfaces.Bank;
import fr.univcotedazur.multicredit.interfaces.MemberFinder;
import fr.univcotedazur.multicredit.interfaces.MemberHandler;
import fr.univcotedazur.multicredit.interfaces.ShopRegistration;
import fr.univcotedazur.multicredit.repositories.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
public class getStatisticsOnClientUsageDefs {
    MemberAccount memberAccount;
    String name;
    String mail;
    String password;
    LocalDate birthDate;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
    @Autowired
    MemberHandler memberHandler;
    @Autowired
    MemberFinder memberFinder;
    @Autowired
    TransactionHandler transactionHandler;
    @Autowired
    MemberRepository memberAccountRepository;
    @Autowired
    private ShopRegistration shopRegistration;
    @Autowired
    CatalogRepository catalogRepository;

    @Autowired
    PurchaseRepository purchaseRepository;
    @Autowired
    UsePointsRepository usePointsRepository;

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    GiftRepository giftRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    ShopRepository shopRepository;
    String card;
    @Autowired
    private Bank bankMock;
    Shop shop1;
    Shop shop;
    MemberAccount account;
    List<Transaction> list;
    @Given("A customer has a valid account with numerous transactions")
    public void setUp()throws AlreadyExistingMemberException, MissingInformationException, UnderAgeException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        transactionRepository.deleteAll();
        itemRepository.deleteAll();
        giftRepository.deleteAll();
        memberAccountRepository.deleteAll();
        shopRepository.deleteAll();
        catalogRepository.deleteAll();
        try {
            account = memberHandler.createAccount("sourour", "mail", "password", LocalDate.parse("11/04/2001", formatter));
            assertNotNull(memberFinder.findById(account.getId()));
        }
        catch (AlreadyExistingMemberException e){
            account = memberFinder.findByMail(mail).orElse(null);
        }
    }


    @When("the client has made at least two transactions using the application")
    @Transactional
    public void transactions(){
        shop=new Shop("A", "1 rue de la paix");
        shopRepository.save(shop);
         shop1=new Shop("4", "5 rue de la paix");
        shopRepository.save(shop1);
        account.setPoints(100);
        UsePoints transaction=new UsePoints(LocalDate.now(),account);
        Gift gift=new Gift();
        gift.setRequiredStatus(AccountStatus.VFP);
        transaction.setGift(gift);
        transaction.setUsedPoints(50);
        account.setStatus(AccountStatus.VFP);
        Product product3=new Product("ring",1.0,10,0.0);
        product3.setShop(shop);
        gift.setShop(shop);
        shop.addProduct(product3);
        catalogRepository.save(product3);
        shop.addGift(gift);
        giftRepository.save(gift);
        transaction.setShop(shop);
        Item item=new Item(product3,2);
        Purchase tran=new Purchase(LocalDate.now(),account,List.of(item));
        tran.setMemberAccount(account);
        item.setPurchase(tran);
        tran.addItem(item);
        tran.setShop(shop1);
        account.getTransactions().add(tran);
        purchaseRepository.save(tran);
        itemRepository.save(item);
        transaction.setMemberAccount(account);
        transaction.setShop(shop);
        usePointsRepository.save(transaction);
         list=transactionHandler.getStatisticsOnClientUsage(account);

    }
    @Then("the usage statistics for the client will display a count of the two transactions.")
    public void statistics1(){
        assertEquals(2, list.stream().count());
    }
    @When("the client has made at least one transactions using the application at shop with name {string}")
    @Transactional
    public void transactions1(String name){
        shop=new Shop(name, "1 rue de la paix");
        shopRepository.save(shop);
        shop1=new Shop("4", "5 rue de la paix");
        shopRepository.save(shop1);
        account.setPoints(100);
        UsePoints transaction=new UsePoints(LocalDate.now(),account);
        Gift gift=new Gift();
        gift.setRequiredStatus(AccountStatus.VFP);
        transaction.setGift(gift);
        transaction.setUsedPoints(50);
        account.setStatus(AccountStatus.VFP);
        Product product3=new Product("ring",1.0,10,0.0);
        product3.setShop(shop);
        gift.setShop(shop);
        shop.addProduct(product3);
        catalogRepository.save(product3);
        shop.addGift(gift);
        giftRepository.save(gift);
        transaction.setShop(shop);
        Item item=new Item(product3,2);
        Purchase tran=new Purchase(LocalDate.now(),account,List.of(item));
        tran.setMemberAccount(account);
        item.setPurchase(tran);
        tran.addItem(item);
        tran.setShop(shop1);
        account.getTransactions().add(tran);
        purchaseRepository.save(tran);
        itemRepository.save(item);
        transaction.setMemberAccount(account);
        transaction.setShop(shop);
        usePointsRepository.save(transaction);
        list=transactionHandler.getStatisticsOnClientUsageAtShop(shop,account);
    }
    @Then("the usage statistics for the client will display a count of the one transaction.")
    public void statistics(){
        assertEquals(1, list.stream().count());
    }
}
