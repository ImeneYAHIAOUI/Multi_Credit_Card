package fr.univcotedazur.simpletcfs.cucumber.member;

import fr.univcotedazur.simpletcfs.entities.AccountStatus;
import fr.univcotedazur.simpletcfs.entities.MemberAccount;
import fr.univcotedazur.simpletcfs.entities.Purchase;
import fr.univcotedazur.simpletcfs.entities.Shop;
import fr.univcotedazur.simpletcfs.exceptions.AccountNotFoundException;
import fr.univcotedazur.simpletcfs.exceptions.AlreadyExistingMemberException;
import fr.univcotedazur.simpletcfs.exceptions.MissingInformationException;
import fr.univcotedazur.simpletcfs.exceptions.UnderAgeException;
import fr.univcotedazur.simpletcfs.interfaces.MemberFinder;
import fr.univcotedazur.simpletcfs.interfaces.MemberHandler;
import fr.univcotedazur.simpletcfs.repositories.TransactionRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.TestPropertySource;

import static org.awaitility.Awaitility.await;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@TestPropertySource(properties = {"VFP.updateRate.cron=*/1 * * * * *","VFP.MinPurchasesNumber=5"})
@Commit
public class BecomeVFPStepDefs {
    MemberAccount memberAccount;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");


    @Autowired
    MemberHandler memberHandler;
    @Autowired
    MemberFinder memberFinder;
    @Autowired
    TransactionRepository transactionRepository;


    @Given("a member with name {string}, mail {string}, password {string} and birthdate {string}")
    public void aMemberWithNameMailPasswordAndBirthdate(String name, String mail, String password, String birthDate) throws AlreadyExistingMemberException, UnderAgeException, MissingInformationException {
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
    public void theMemberMakesPurchases(int nbPurchases) {
        for (int i = 0; i < nbPurchases; i++) {
            transactionRepository.save(new Purchase(LocalDate.now(), memberFinder.findById(memberAccount.getId()).orElse(null),new ArrayList<>()));
        }
    }

    @When("the member account status gets updated")
    public void theMemberAccountStatusGetsUpdated() {
        await().atMost(6, TimeUnit.SECONDS).untilAsserted(() ->
                verify(memberHandler, Mockito.atLeast(5)).updateAccountsStatus());
    }

    @Then("the member statue becomes VFP")
    public void theMemberStatueIsVFP2() throws AccountNotFoundException {
        assertEquals(Objects.requireNonNull(memberFinder.findById(memberAccount.getId()).orElse(null)).getStatus(),AccountStatus.VFP);
        transactionRepository.deleteAll();
        memberHandler.deleteAccount(memberAccount);
    }

    @Then("the member statue becomes regular")
    public void theMemberStatueIsNotVFP() throws AccountNotFoundException {
        assertEquals(Objects.requireNonNull(memberFinder.findById(memberAccount.getId()).orElse(null)).getStatus(),AccountStatus.REGULAR);
        transactionRepository.deleteAll();
        memberHandler.deleteAccount(memberAccount);
    }

}
