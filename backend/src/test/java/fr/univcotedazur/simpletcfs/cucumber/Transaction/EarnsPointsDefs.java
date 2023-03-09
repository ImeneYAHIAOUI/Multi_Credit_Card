package fr.univcotedazur.simpletcfs.cucumber.Transaction;

import fr.univcotedazur.simpletcfs.components.TransactionManager;
import fr.univcotedazur.simpletcfs.entities.*;
import fr.univcotedazur.simpletcfs.exceptions.*;
import fr.univcotedazur.simpletcfs.interfaces.Bank;
import fr.univcotedazur.simpletcfs.interfaces.MemberFinder;
import fr.univcotedazur.simpletcfs.interfaces.MemberHandler;
import fr.univcotedazur.simpletcfs.repositories.MemberAccountRepository;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
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
    TransactionManager transactionManager;
    @Autowired
    MemberAccountRepository memberAccountRepository;
    CreditCard card;
    @Autowired
    private Bank bankMock;
    @Given("a client has an account in the system")
    public void a_client_has_an_account_in_the_system()throws AlreadyExistingMemberException, UnderAgeException, MissingInformationException, AccountNotFoundException {
        memberAccountRepository.deleteAll();
        name = "John Doe";
        mail = "sourour.gazzeh@outlook.fr";
        password="123456";
        birthDate = LocalDate.parse("01/01/2000",formatter);
        memberAccount = memberHandler.createAccount(name,mail,password,birthDate);
        assertEquals(memberFinder.findMember(memberAccount.getId()),memberAccount);
        card=new CreditCard("1234567890123456", "John Doe",  LocalDate.parse("01/01/2030",formatter), "123");
    }


    @When("the client makes a valid purchase")
    public void the_client_makes_a_purchase() throws AccountNotFoundException, PaymentException {
        Product product3=new Product(UUID.randomUUID(),"ring",1.0,10);
        Purchase tran=new Purchase(List.of(new Item(product3,2)));
        tran.setMemberAccount(memberAccount);
        when(bankMock.pay(any(CreditCard.class), anyDouble())).thenReturn(true);
        transactionManager.processPurchase(memberAccount,tran,card );
    }
    @Then("the client earns points")
    public void the_client_earns_points() {
        assertEquals(20,memberAccount.getPoints());
    }
    @When("the client makes an invalid purchase")
    public void the_client_makes_an_invalid_purchase() throws AccountNotFoundException, PaymentException {
        /**assertEquals(0,memberAccount.getPoints());
        Product product3=new Product(UUID.randomUUID(),"ring",1.0,10);
        Purchase tran=new Purchase(List.of(new Item(product3,2)));
        tran.setMemberAccount(memberAccount);
        when(bankMock.pay(any(CreditCard.class), anyDouble())).thenReturn(false);
        Assertions.assertThrows(PaymentException.class, () -> transactionManager.processPurchase(memberAccount,tran,card));
    **/}
    @Then("the client doesn't earn points")
    public void the_client_doesnt_earns_points() {
        assertEquals(0,memberAccount.getPoints());
    }
}
