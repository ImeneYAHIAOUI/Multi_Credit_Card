package fr.univcotedazur.multicredit.cucumber.Transaction;

import fr.univcotedazur.multicredit.components.TransactionHandler;
import fr.univcotedazur.multicredit.entities.MemberAccount;
import fr.univcotedazur.multicredit.exceptions.*;
import fr.univcotedazur.multicredit.interfaces.Bank;
import fr.univcotedazur.multicredit.interfaces.MemberFinder;
import fr.univcotedazur.multicredit.interfaces.MemberHandler;
import fr.univcotedazur.multicredit.interfaces.ShopRegistration;
import fr.univcotedazur.multicredit.repositories.CatalogRepository;
import fr.univcotedazur.multicredit.repositories.MemberRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
public class LoadingMoneyToCardDefs {
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
    CatalogRepository catalogRepository;
    String card = "1234567999123456";
    double amount;
    @Autowired
    private ShopRegistration shopRegistration;
    @Autowired
    private Bank bankMock;

    @Given("a client has a valid account in the system")
    public void a_client_has_an_account_in_the_system() throws AlreadyExistingMemberException, UnderAgeException, MissingInformationException, AccountNotFoundException {
        try {
            memberAccount = memberHandler.createAccount("John Doe", "John.Doe@mail.com", "password", LocalDate.parse("11/04/2001", formatter));
        } catch (AlreadyExistingMemberException e) {
            memberHandler.deleteAccount(memberFinder.findByMail("John.Doe@mail.com").orElse(null));
            memberAccount = memberHandler.createAccount("John Doe", "John.Doe@mail.com", "password", LocalDate.parse("11/04/2001", formatter));
        }
    }

    @When("the client load card with money")
    public void the_client_makes_a_purchase() {
        amount = 10.0;
        assertEquals(0.0, memberAccount.getBalance());
        when(bankMock.pay(eq(card), anyDouble())).thenReturn(true);
    }

    @Then("the money should be successfully loaded to the card")
    public void the_client_earns_points() throws AccountNotFoundException, PaymentException {
        memberHandler.chargeMembershipCard(memberAccount, amount, card);
    }

    @When("the card balance increases.")
    public void the_client_makes_an_invalid_purchase() {
        assertEquals(10.0, memberAccount.getBalance());

    }

    @Then("the bank should decline the card loading")
    public void bank_decline() {
        amount = 10000000000.0;
        assertEquals(0.0, memberAccount.getBalance());
        when(bankMock.pay(eq(card), anyDouble())).thenReturn(false);
    }

    @And("the system should indicate that the loading was unsuccessful")
    public void the_system_should_indicate_that_the_loading_was_unsuccessful() {
        Assertions.assertThrows(PaymentException.class, () -> memberHandler.chargeMembershipCard(memberAccount, amount, card));
    }

    @And("the card balance should remain the same")
    public void the_card_balance_should_remain_the_same() {
        assertEquals(0, memberAccount.getBalance());
    }
}
