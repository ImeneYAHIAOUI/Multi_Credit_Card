package fr.univcotedazur.simpletcfs.cucumber.Shop;

import fr.univcotedazur.simpletcfs.components.Catalog;
import fr.univcotedazur.simpletcfs.components.ShopManager;
import fr.univcotedazur.simpletcfs.entities.*;
import fr.univcotedazur.simpletcfs.exceptions.AlreadyExistingGiftException;
import fr.univcotedazur.simpletcfs.exceptions.GiftNotFoundException;
import fr.univcotedazur.simpletcfs.exceptions.MissingInformationException;
import fr.univcotedazur.simpletcfs.interfaces.MailSender;
import fr.univcotedazur.simpletcfs.interfaces.ShopRegistration;
import fr.univcotedazur.simpletcfs.repositories.GiftRepository;
import fr.univcotedazur.simpletcfs.repositories.ShopRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MakingChangesToTheGiftCatalogDefs {


    @Autowired
    private ShopManager shopManager;
    @Autowired
    private ShopRegistration shopRegistration;
    @Autowired
    private GiftRepository giftRepository;
    @Autowired
    private Catalog catalog;
    @Autowired
    ShopRepository shopRepository;
    Shop shop;
    Gift gift;
    Gift gift1;

    @Given("a shop with an empty gift list")
    public void a_shop_with_an_empty_gift_list()throws MissingInformationException {
        shopRepository.deleteAll();
        shop=shopRegistration.addShop("A", "1 rue de la paix");
        assertTrue(shop.getGiftList().isEmpty());

    }
    @When("the shop adds a gift to the catalog")
    public void the_shop_adds_a_gift_to_the_catalog() throws AlreadyExistingGiftException {
        gift=new Gift(150,"ring", AccountStatus.VFP);
        gift1=new Gift(10,"cake", AccountStatus.REGULAR);
        catalog.addGift(shop, gift);



    }
    @Then("the gift is added to the catalog")
    public void the_gift_is_added_to_the_catalog() {
        assertTrue(shop.getGiftList().contains(gift));
        assertTrue(giftRepository.existsById(gift.getGiftId()));
    }
    @Given("a shop with a gift list")
    public void a_shop_with_a_gift_list() throws MissingInformationException, AlreadyExistingGiftException{
        shopRepository.deleteAll();
        shop=shopRegistration.addShop("A", "1 rue de la paix");
     assertTrue(shop.getGiftList().isEmpty());
        gift=new Gift(150,"ring", AccountStatus.VFP);
        gift1=new Gift(10,"cake", AccountStatus.REGULAR);
        catalog.addGift(shop, gift);
        assertTrue(giftRepository.existsById(gift.getGiftId()));
    }
    @When("shop includes an existing gift in its catalog.")
    public void shop_includes_an_existing_gift_in_its_catalog() {
        assertEquals(shop.getGiftList().size(),1);
        assertTrue(shop.getGiftList().contains(gift));
        assertThrows(AlreadyExistingGiftException.class, () -> {
            catalog.addGift(shop, gift);
        });
    }
    @Then("the gift is not added to the catalog because it already exists")
    public void the_gift_is_not_added_to_the_catalog_because_it_already_exists() {
        assertEquals(shop.getGiftList().size(),1);
    }
    @When("the shop remove a gift from the catalog")
    public void the_shop_remove_a_gift_from_the_catalog()throws GiftNotFoundException {
        assertTrue(shop.getGiftList().contains(gift));
        assertEquals(shop.getGiftList().size(),1);
        catalog.removeGift(shop, gift);
    }
    @Then("the gift is removed from the catalog")
    public void the_gift_is_removed_from_the_catalog() {
       assertFalse(shop.getGiftList().contains(gift));
        assertEquals(shop.getGiftList().size(),0);
    }
    @When("shop removes a non-existent gift from its catalog.")
    public void shop_removes_an_existing_gift_from_its_catalog() {
        assertFalse(shop.getGiftList().contains(gift));
        assertThrows(GiftNotFoundException.class, () -> {
            catalog.removeGift(shop, gift);
        });
    }

    @Then("the gift is not removed from the catalog because it does not exist")
    public void the_gift_is_not_removed_from_the_catalog_because_it_does_not_exist() {
        assertFalse(shop.getGiftList().contains(gift));
    }

}
