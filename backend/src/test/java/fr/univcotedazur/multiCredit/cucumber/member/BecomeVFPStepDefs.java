package fr.univcotedazur.multiCredit.cucumber.member;

import fr.univcotedazur.multiCredit.entities.*;
import fr.univcotedazur.multiCredit.exceptions.*;
import fr.univcotedazur.multiCredit.interfaces.Bank;
import fr.univcotedazur.multiCredit.interfaces.MemberFinder;
import fr.univcotedazur.multiCredit.interfaces.MemberHandler;
import fr.univcotedazur.multiCredit.interfaces.TransactionProcessor;
import fr.univcotedazur.multiCredit.repositories.CatalogRepository;
import fr.univcotedazur.multiCredit.repositories.ShopRepository;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.TestPropertySource;

import javax.transaction.Transactional;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SpringBootTest
@TestPropertySource(properties = {"VFP.updateRate.cron=*/1 * * * * *","VFP.MinPurchasesNumber=5"})
@Commit
@Transactional
public class BecomeVFPStepDefs {
    MemberAccount memberAccount;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");


    @Autowired
    MemberHandler memberHandler;
    @Autowired
    MemberFinder memberFinder;

    @Autowired
    Bank bank;
    @Autowired
    ShopRepository shopRepository;
    @Autowired
    CatalogRepository catalogRepository;
    @Autowired
    TransactionProcessor transactionProcessor;

    @Before
    public void setUp() {
        when(bank.pay(anyString(),anyDouble())).thenReturn(true);
    }

    @Given("a member with name {string}, mail {string}, password {string} and birthdate {string}")
    public void aMemberWithNameMailPasswordAndBirthdate(String name, String mail, String password, String birthDate) throws UnderAgeException, MissingInformationException {
        try {
            memberAccount = memberHandler.createAccount(name, mail, password, LocalDate.parse(birthDate, formatter));
        }
        catch (AlreadyExistingMemberException e) {
            memberAccount = memberFinder.findByMail(mail).orElse(null);
        }
    }

    @When("the member statue is regular")
    public void theMemberStatueIsRegular() throws AccountNotFoundException {
        memberHandler.updateAccountStatus(memberAccount, AccountStatus.REGULAR);
    }

    @When("the member statue is VFP")
    public void theMemberStatueIsVFP() throws AccountNotFoundException {
        memberHandler.updateAccountStatus(memberAccount, AccountStatus.VFP);
    }

    @And("the member makes {int} purchases")
    public void theMemberMakesPurchases(int nbPurchases) throws PaymentException, AccountNotFoundException {
        memberAccount.getTransactions().clear();
        Product product=new Product("ring",1.0,10,0.0);
        Shop shop=new Shop("A", "1 rue de la paix");
        product.setShop(shop);
        shopRepository.save(shop);
        catalogRepository.save(product);
        Item item=new Item(product,2);
        Purchase purchase =new Purchase(LocalDate.now(),memberAccount, List.of(item));
        purchase.setMemberAccount(memberAccount);
        item.setPurchase(purchase);
        purchase.addItem(item);
        purchase.setShop(shop);
        for (int i = 0; i < nbPurchases; i++) {
            transactionProcessor.processPurchaseWithCreditCard(memberAccount,purchase,"123456456789");
        }
    }

    @When("the member account status gets updated")
    public void theMemberAccountStatusGetsUpdated() {


        memberHandler.updateAccountsStatus();
    }

    @Then("the member statue becomes VFP")
    public void theMemberStatueIsVFP2() {

        assertEquals(memberFinder.findById(memberAccount.getId()).orElse(null).getStatus(),AccountStatus.VFP);
    }

    @Then("the member statue becomes regular")
    public void theMemberStatueIsNotVFP()  {
        assertEquals(memberFinder.findById(memberAccount.getId()).orElse(null).getStatus(),AccountStatus.REGULAR);
    }

}
