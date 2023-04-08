package fr.univcotedazur.multicredit.cucumber.Transaction;

import fr.univcotedazur.multicredit.components.TransactionHandler;
import fr.univcotedazur.multicredit.entities.*;
import fr.univcotedazur.multicredit.exceptions.*;
import fr.univcotedazur.multicredit.interfaces.MemberFinder;
import fr.univcotedazur.multicredit.interfaces.MemberHandler;
import fr.univcotedazur.multicredit.interfaces.ShopFinder;
import fr.univcotedazur.multicredit.interfaces.ShopRegistration;
import fr.univcotedazur.multicredit.repositories.CatalogRepository;
import fr.univcotedazur.multicredit.repositories.GiftRepository;
import fr.univcotedazur.multicredit.repositories.MemberRepository;
import fr.univcotedazur.multicredit.repositories.TransactionRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UsePointsDefs {
    MemberAccount memberAccount;
    String name;
    String mail;
    String password;
    LocalDate birthDate;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
    @Autowired
    MemberHandler memberHandler;
    @Autowired
    private ShopRegistration shopRegistration;
    @Autowired
    private ShopFinder shopFinder;
    @Autowired
    MemberFinder memberFinder;
    @Autowired
    TransactionHandler transactionHandler;
    @Autowired
    MemberRepository memberAccountRepository;
    @Autowired
    CatalogRepository catalogRepository;
    @Autowired
    GiftRepository giftRepository;
    UsePoints transaction;

    @Autowired
    TransactionRepository transactionRepository;
    Purchase tran;
    @Given("a client has an account")
    @Transactional
    public void a_client_has_an_account() throws AlreadyExistingMemberException, UnderAgeException, MissingInformationException, AccountNotFoundException, AlreadyExistingShopException {
        try {
            memberAccount = memberHandler.createAccount("John Doe", "John.Doe@mail.com", "password", LocalDate.parse("11/04/2001", formatter));
        }catch (AlreadyExistingMemberException e){
            memberHandler.deleteAccount(memberFinder.findByMail("John.Doe@mail.com").get());
            memberAccount = memberHandler.createAccount("John Doe", "John.Doe@mail.com", "password", LocalDate.parse("11/04/2001", formatter));
        }
        memberAccount=memberFinder.findByMail("John.Doe@mail.com").orElse(null);
        Gift gift=new Gift();
        gift.setRequiredStatus(AccountStatus.VFP);
        memberAccount.setStatus(AccountStatus.VFP);
        transaction=new UsePoints(LocalDate.now(),memberAccount);
        transaction.setUsedPoints(100);
        transaction.setGift(gift);
        Product product3=new Product("phone",1.0,0,0.0);
        Shop shop;
        try {
            shop = shopRegistration.addShop("A", "1 rue de la paix");
        }catch (AlreadyExistingShopException e){
            shop=shopFinder.findShopByAddress("1 rue de la paix").get(0);
        }
        product3.setShop(shop);
        gift.setShop(shop);
        catalogRepository.save(product3);
        tran=new Purchase(LocalDate.now(),memberAccount,List.of(new Item(product3,2)));
        tran.setMemberAccount(memberAccount);
        tran.setShop(shop);
        transactionRepository.save(tran);
        memberAccount.getTransactions().add(tran);
        giftRepository.save(gift);
        transaction.setShop(shop);
    }
    @Given("the client has {int} points")
    public void the_client_has_points(Integer int1) {
        memberAccount.setPoints(int1);
    }
    @When("the client uses points to obtain a gift")
    public void the_client_uses_points_to_obtain_a_gift()throws DeclinedTransactionException, InsufficientPointsException ,AccountNotFoundException{
        transactionHandler.processPointsUsage(memberAccount,transaction);
    }
    @Then("the client gets the gift")
    public void the_client_gets_the_gift() {
        assertEquals(50, memberAccount.getPoints());
    }

    @Then("the client is unable to obtain a gift because he does not have enough points.")
    public void the_client_is_unable_to_obtain_a_gift_because_he_does_not_have_enough_points() {
        assertThrows(InsufficientPointsException.class,()-> transactionHandler.processPointsUsage(memberAccount,transaction));
    }
    @And("the client doesn't get the gift")
    public void theClientDoesnTEarnPoints() {
        assertEquals(0, memberAccount.getPoints());
    }
    @Then("the client is unable to obtain a gift because he hasn't the status required")
    public void the_client_is_unable_to_obtain_a_gift_because_he_does_not_status() {
        memberAccount.setStatus(AccountStatus.REGULAR);
        assertThrows(DeclinedTransactionException.class,()-> transactionHandler.processPointsUsage(memberAccount,transaction));
    }
    @Then("the client is unable to obtain a gift because he doesn't make a purchase before")
    public void the_client_is_unable_to_obtain_a_gift_because_he_does_not_make_purchase() {
       transactionRepository.deleteAll();
        memberAccount.setStatus(AccountStatus.VFP);
        memberAccount.getTransactions().clear();
        assertThrows(DeclinedTransactionException.class,()-> transactionHandler.processPointsUsage(memberAccount,transaction));
    }
}
