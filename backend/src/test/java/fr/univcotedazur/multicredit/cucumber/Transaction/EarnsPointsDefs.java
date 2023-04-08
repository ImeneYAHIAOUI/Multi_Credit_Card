package fr.univcotedazur.multicredit.cucumber.Transaction;

import fr.univcotedazur.multicredit.components.TransactionHandler;
import fr.univcotedazur.multicredit.entities.*;
import fr.univcotedazur.multicredit.exceptions.*;
import fr.univcotedazur.multicredit.interfaces.*;
import fr.univcotedazur.multicredit.repositories.CatalogRepository;
import fr.univcotedazur.multicredit.repositories.MemberRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class EarnsPointsDefs {
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
    private ShopFinder shopFinder;
    @Autowired
    CatalogRepository catalogRepository;
    String card;
    @Autowired
    private Bank bankMock;
    @Given("a client has an account in the system")
    public void a_client_has_an_account_in_the_system()throws AlreadyExistingMemberException, UnderAgeException, MissingInformationException, AccountNotFoundException {
        try {
            memberAccount = memberHandler.createAccount("John Doe", "John.Doe@mail.com", "password", LocalDate.parse("11/04/2001", formatter));
        }catch (AlreadyExistingMemberException e){
            memberHandler.deleteAccount(memberFinder.findByMail("John.Doe@mail.com").get());
            memberAccount = memberHandler.createAccount("John Doe", "John.Doe@mail.com", "password", LocalDate.parse("11/04/2001", formatter));
        }
    }
    @When("the client makes a valid purchase")
    public void the_client_makes_a_purchase() throws AccountNotFoundException, PaymentException, MissingInformationException, AlreadyExistingShopException {
        Product product3=new Product("phone",1.0,20,0.0);
        Shop shop=shopRegistration.addShop("A", "1 rue de la paix");
        product3.setShop(shop);
        catalogRepository.save(product3);
        Purchase tran=new Purchase(LocalDate.now(),memberAccount,List.of(new Item(product3,2)));
        tran.setShop(shop);
        when(bankMock.pay(eq("1234567999123456"), anyDouble())).thenReturn(true);
        transactionHandler.processPurchaseWithCreditCard(memberAccount,tran,"1234567999123456" );
    }
    @Then("the client earns points")
    public void the_client_earns_points() {
       assertEquals(40,memberAccount.getPoints());
    }
    @When("the client makes an invalid purchase")
    public void the_client_makes_an_invalid_purchase() throws  MissingInformationException, AlreadyExistingShopException {
        assertEquals(0,memberAccount.getPoints());
        Product product3=new Product("phone",1.0,20,0.0);
        Shop shop;
        try {
            shop=shopRegistration.addShop("A", "1 rue de la paix");
        }
        catch (AlreadyExistingShopException e){
            shop=shopFinder.findShopByAddress("1 rue de la paix").get(0);
        }

        product3.setShop(shop);
        catalogRepository.save(product3);
        Purchase tran=new Purchase(LocalDate.now(),memberAccount,List.of(new Item(product3,2)));
        when(bankMock.pay(eq("1234567999123456"), anyDouble())).thenReturn(false);
        Assertions.assertThrows(PaymentException.class, () -> transactionHandler.processPurchaseWithCreditCard(memberAccount,tran,card));
        assertEquals(0,memberAccount.getPoints());
    }
    @Then("the client doesn't earn points")
    public void the_client_doesnt_earns_points() {
        assertEquals(0,memberAccount.getPoints());
    }
}
