package fr.univcotedazur.simpletcfs.cucumber.Shop;

import fr.univcotedazur.simpletcfs.components.AdminManager;
import fr.univcotedazur.simpletcfs.entities.*;
import fr.univcotedazur.simpletcfs.exceptions.AccountNotFoundException;
import fr.univcotedazur.simpletcfs.exceptions.AlreadyExistingMemberException;
import fr.univcotedazur.simpletcfs.exceptions.MissingInformationException;
import fr.univcotedazur.simpletcfs.exceptions.UnderAgeException;
import fr.univcotedazur.simpletcfs.interfaces.ShopRegistration;
import fr.univcotedazur.simpletcfs.interfaces.ShopkeeperFinder;
import fr.univcotedazur.simpletcfs.repositories.ShopRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class CreateShopKeeperAccountDefs {
    ShopKeeperAccount shopKeeperAccount;
    String name;
    String mail;
    String password;
    LocalDate birthDate;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
    @Autowired
    AdminManager adminManager;
    @Autowired
    ShopkeeperFinder shopkeeperFinder;
    @Autowired
    private ShopRegistration shopRegistration;
    @Autowired
    ShopRepository shopRepository;
    Shop shop;
    @Given("a shop who wants to create an account")
    public void aMemberWhoWantsToCreateAnAccount() throws MissingInformationException{
        /*shopRepository.deleteAll();
        List<Planning> planning =new ArrayList<>();
        planning.add(new Planning(WeekDay.Friday,LocalTime.of(10,00),LocalTime.of(15,00)));
        planning.add(new Planning(WeekDay.Saturday,LocalTime.of(10,00),LocalTime.of(14,00)));
        planning.add(new Planning(WeekDay.Monday,LocalTime.of(9,00),LocalTime.of(19,00)));
        Product product=new Product("ring",1.0,0);
        Product product1=new Product("Cookie",2.0,0);
        Product product2=new Product("Cake",1.0,0);
        Product product3=new Product("ring",1.0,0);
        Product product4=new Product("chocolat",1.5,0);
        List<Product> productList=new ArrayList<>();
        productList.add(product);
        productList.add(product1);
        productList.add(product2);
        shop=shopRegistration.addShop("A", "1 rue de la paix", new ArrayList<>(), productList,new ArrayList<>());
*/
    }

    @When("the shop submits the name {string}")
    public void a_member_with_named(String name) {
       // this.name = name;
    }

    @And("the mail is {string}")
    public void with_mail(String mail) {
       // this.mail = mail;
    }

    @And("the password is {string}")
    public void with_password(String password) {
       // this.password = password;
    }

    @And("the birth date is {string}")
    public void with_birth_date(String birthDate) {
        //this.birthDate = LocalDate.parse(birthDate,formatter);
    }

    @Then("this shop keeper account is created")
    public void the_member_creates_an_account() throws AlreadyExistingMemberException, UnderAgeException, MissingInformationException, AccountNotFoundException {
        /*try{
            shopKeeperAccount = adminManager.createShopKeeperAccount(new Form(name,mail,password,birthDate),shop)   ;
        }
        catch (Exception e){
            shopKeeperAccount = shopkeeperFinder.findShopKeeperAccountByMail(mail).orElse(null);
        }
        //assertEquals(shopkeeperFinder.findShopKeeperAccountById(shopKeeperAccount.getId()).get(), shopKeeperAccount);
        adminManager.deleteShopKeeperAccount(shopKeeperAccount);*/
    }

    @Then("this shop keeper account is not created because of missing information")
    public void the_member_does_not_create_an_account2()  {
        /*assertThrows(MissingInformationException.class, () -> adminManager.createShopKeeperAccount(
                new Form(name,mail,password,birthDate),
                shop));*/
    }

    @Then("this shop keeper account is not created because they are under age")
    public void the_member_does_not_create_an_account3()  {
        /*assertThrows(UnderAgeException.class, () -> adminManager.createShopKeeperAccount(
                new Form(name,mail,password,birthDate),
                shop));*/
    }

    @When("this shop tries to create an account with the same mail")
    public void the_member_tries_to_create_an_account_with_the_same_mail() throws AlreadyExistingMemberException, UnderAgeException, MissingInformationException {
        /*adminManager.createShopKeeperAccount(
                new Form(name,mail,password,birthDate),
                shop);
        assertThrows(AlreadyExistingMemberException.class, () ->  adminManager.createShopKeeperAccount(
                new Form(name,mail,password,birthDate),
                shop));*/
    }

    @Then("this shop keeper account is not created because they are already a member")
    public void the_member_does_not_create_an_account() throws AccountNotFoundException {
        //adminManager.deleteShopKeeperAccount(shopKeeperAccount);
    }



}
