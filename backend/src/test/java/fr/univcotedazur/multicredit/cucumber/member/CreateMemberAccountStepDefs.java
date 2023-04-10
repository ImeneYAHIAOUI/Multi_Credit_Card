package fr.univcotedazur.multicredit.cucumber.member;

import fr.univcotedazur.multicredit.entities.MemberAccount;
import fr.univcotedazur.multicredit.exceptions.AccountNotFoundException;
import fr.univcotedazur.multicredit.exceptions.AlreadyExistingMemberException;
import fr.univcotedazur.multicredit.exceptions.MissingInformationException;
import fr.univcotedazur.multicredit.exceptions.UnderAgeException;
import fr.univcotedazur.multicredit.interfaces.MemberFinder;
import fr.univcotedazur.multicredit.interfaces.MemberHandler;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class CreateMemberAccountStepDefs {
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


    @Given("a member who wants to create an account")
    public void aMemberWhoWantsToCreateAnAccount() {
    }

    @When("the member submits the name {string}")
    public void a_member_with_named(String name) {
        this.name = name;
    }

    @And("the mail {string}")
    public void with_mail(String mail) {

        this.mail = mail;
    }

    @And("the password {string}")
    public void with_password(String password) {
        this.password = password;
    }

    @And("the birth date {string}")
    public void with_birth_date(String birthDate) {
        this.birthDate = LocalDate.parse(birthDate, formatter);
    }

    @Then("this account is created")
    public void the_member_creates_an_account() throws AlreadyExistingMemberException, UnderAgeException, MissingInformationException, AccountNotFoundException {
        memberAccount = memberHandler.createAccount(name, mail, password, birthDate);
        assertEquals(memberFinder.findById(memberAccount.getId()).get().getId(), memberAccount.getId());
        memberHandler.deleteAccount(memberAccount);
    }

    @Then("this account is not created because of missing information")
    public void the_member_does_not_create_an_account2() {
        assertThrows(MissingInformationException.class, () -> memberHandler.createAccount(name, mail, password, birthDate));
    }

    @Then("this account is not created because they are under age")
    public void the_member_does_not_create_an_account3() {
        assertThrows(UnderAgeException.class, () -> memberHandler.createAccount(name, mail, password, birthDate));
    }

    @When("this member tries to create an account with the same mail")
    public void the_member_tries_to_create_an_account_with_the_same_mail() throws AlreadyExistingMemberException, UnderAgeException, MissingInformationException {
        memberAccount = memberHandler.createAccount(name, mail, password, birthDate);
    }

    @Then("this account is not created because they are already a member")
    public void the_member_does_not_create_an_account() throws AccountNotFoundException {
        assertThrows(AlreadyExistingMemberException.class, () -> memberHandler.createAccount(name, mail, password, birthDate));
        memberHandler.deleteAccount(memberAccount);
    }
}
