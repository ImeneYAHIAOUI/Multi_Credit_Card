package fr.univcotedazur.multiCredit.cucumber.Admin;

import fr.univcotedazur.multiCredit.components.AdminManager;
import fr.univcotedazur.multiCredit.entities.AdminAccount;
import fr.univcotedazur.multiCredit.entities.Form;
import fr.univcotedazur.multiCredit.entities.MemberAccount;
import fr.univcotedazur.multiCredit.exceptions.*;
import fr.univcotedazur.multiCredit.interfaces.MemberFinder;
import fr.univcotedazur.multiCredit.interfaces.MemberHandler;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CreateAdminAccountStepDefs {
    AdminAccount memberAccount;
    String name;
    String mail;
    String password;
    LocalDate birthDate;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
    @Autowired
    AdminManager adminManager;
    @Given("a admin who wants to create an account")
    public void aMemberWhoWantsToCreateAnAccount() {
    }

    @When("the admin submits the name {string}")
    public void a_member_with_named(String name) {
        this.name = name;
    }

    @And("admin mail is {string}")
    public void with_mail(String mail) {

        this.mail = mail;
    }

    @And("admin password is {string}")
    public void with_password(String password) {
        this.password = password;
    }

    @And("admin birth date is {string}")
    public void with_birth_date(String birthDate) {
        this.birthDate = LocalDate.parse(birthDate,formatter);
    }

    @Then("this admin account is created")
    public void the_member_creates_an_account() throws AlreadyExistingAdminException, UnderAgeException, MissingInformationException, AccountNotFoundException {
        memberAccount = adminManager.createAdminAccount(new Form(name,mail,password,birthDate));
        assertEquals(adminManager.findAdminById(memberAccount.getId()).get().getId(),memberAccount.getId());
        adminManager.deleteAdminAccount(memberAccount);
    }

    @Then("this admin account is not created because of missing information")
    public void the_member_does_not_create_an_account2()  {
        assertThrows(MissingInformationException.class, () -> adminManager.createAdminAccount(new Form(name,mail,password,birthDate)));
    }


    @When("this admin tries to create an account with the same mail")
    public void the_member_tries_to_create_an_account_with_the_same_mail() throws AlreadyExistingAdminException, UnderAgeException, MissingInformationException {
        memberAccount = adminManager.createAdminAccount(new Form(name,mail,password,birthDate));
    }

    @Then("this admin account is not created because they are already a admin")
    public void the_member_does_not_create_an_account() throws AccountNotFoundException {
        assertThrows(AlreadyExistingAdminException.class, () ->adminManager.createAdminAccount(new Form(name,mail,password,birthDate)));
        adminManager.deleteAdminAccount(memberAccount);
    }




}
