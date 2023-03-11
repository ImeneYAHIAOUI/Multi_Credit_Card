package fr.univcotedazur.simpletcfs.cucumber.Shop;

import fr.univcotedazur.simpletcfs.components.ShopManager;
import fr.univcotedazur.simpletcfs.entities.*;
import fr.univcotedazur.simpletcfs.exceptions.GiftNotFoundException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MakingChangesToTheGiftCatalogDefs {

    Shop shop;
    @Autowired
    ShopManager shopManager;
    Gift gift1;
    Gift gift;
    @Given("a shop with an empty gift list")
    public void a_shop_with_an_empty_gift_list() {
        /*List<Planning> planning =new ArrayList<>();

        shop=new Shop("Pizza noli", "1 rue de la paix");
        shopManager.modifyPlanning(shop,WeekDay.Friday,LocalTime.of(10,00),LocalTime.of(15,00));
        shopManager.modifyPlanning(shop,WeekDay.Saturday,LocalTime.of(10,00),LocalTime.of(14,00));
        shopManager.modifyPlanning(shop,WeekDay.Monday,LocalTime.of(9,00),LocalTime.of(19,00));
        assertTrue(shopManager.getGiftList(shop).isEmpty());*/
    }
    @When("the shop adds a gift to the catalog")
    public void the_shop_adds_a_gift_to_the_catalog() {
        /*gift1=new Gift();
        gift1.setRequiredStatus(AccountStatus.VFP);
        gift1.setPointsNeeded(100);
        gift1.setDescription("Only for vfp");
        shopManager.addGift(shop, gift1);*/


    }
    @Then("the gift is added to the catalog")
    public void the_gift_is_added_to_the_catalog() {
        //assertTrue(shopManager.getGiftList(shop).contains(gift1));
    }
    @Given("a shop with a gift list")
    public void a_shop_with_a_gift_list() {
     /*   List<Planning> planning =new ArrayList<>();
        shop=new Shop("Pizza noli", "1 rue de la paix");
        shopManager.modifyPlanning(shop,WeekDay.Friday,LocalTime.of(10,00),LocalTime.of(15,00));
        shopManager.modifyPlanning(shop,WeekDay.Saturday,LocalTime.of(10,00),LocalTime.of(14,00));
        shopManager.modifyPlanning(shop,WeekDay.Monday,LocalTime.of(9,00),LocalTime.of(19,00));
        gift1=new Gift();
        gift1.setRequiredStatus(AccountStatus.VFP);
        gift1.setPointsNeeded(100);
        gift1.setDescription("Only for vfp");
        shopManager.addGift(shop, gift1);
        assertTrue(shopManager.getGiftList(shop).contains(gift1));
        assertEquals(shopManager.getGiftList(shop).size(),1);
    */}
    @When("shop includes an existing gift in its catalog.")
    public void shop_includes_an_existing_gift_in_its_catalog() {
        /*assertEquals(shopManager.getGiftList(shop).size(),1);
        assertTrue(shopManager.getGiftList(shop).contains(gift1));
        shopManager.addGift(shop, gift1);*/


    }
    @Then("the gift is not added to the catalog because it already exists")
    public void the_gift_is_not_added_to_the_catalog_because_it_already_exists() {
        //assertEquals(shopManager.getGiftList(shop).size(),1);
    }
    @When("the shop remove a gift from the catalog")
    public void the_shop_remove_a_gift_from_the_catalog()throws GiftNotFoundException {
        /*assertTrue(shopManager.getGiftList(shop).contains(gift1));
        assertEquals(shopManager.getGiftList(shop).size(),1);
        shopManager.removeGift(shop, gift1);*/
    }
    @Then("the gift is removed from the catalog")
    public void the_gift_is_removed_from_the_catalog() {
       /*assertFalse(shopManager.getGiftList(shop).contains(gift1));
        assertEquals(shopManager.getGiftList(shop).size(),0);*/
    }
    @When("shop removes a non-existent gift from its catalog.")
    public void shop_removes_an_existing_gift_from_its_catalog() {
        /*gift1=new Gift();
        gift1.setRequiredStatus(AccountStatus.VFP);
        gift1.setPointsNeeded(100);
        gift1.setDescription("Only for vfp");
        assertTrue(shopManager.getGiftList(shop).isEmpty()) ;
        shopManager.addGift(shop, gift1);
        assertTrue(shopManager.getGiftList(shop).contains(gift1));
        gift=new Gift();
        gift.setRequiredStatus(AccountStatus.REGULAR);
        gift.setPointsNeeded(10);
        assertFalse(shopManager.getGiftList(shop).contains(gift));
        assertThrows(GiftNotFoundException.class, () -> {
            shopManager.removeGift(shop, gift);
        });
        assertTrue(shopManager.getGiftList(shop).contains(gift1));
        assertEquals(shopManager.getGiftList(shop).size(),1) ;*/
    }

    @Then("the gift is not removed from the catalog because it does not exist")
    public void the_gift_is_not_removed_from_the_catalog_because_it_does_not_exist() {
        // Write code here that turns the phrase above into concrete actions
    }

}
