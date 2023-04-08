package fr.univcotedazur.multicredit.cucumber.Shop;

import fr.univcotedazur.multicredit.components.Catalog;
import fr.univcotedazur.multicredit.components.ShopManager;
import fr.univcotedazur.multicredit.entities.*;
import fr.univcotedazur.multicredit.exceptions.*;
import fr.univcotedazur.multicredit.interfaces.ShopRegistration;
import fr.univcotedazur.multicredit.repositories.GiftRepository;
import fr.univcotedazur.multicredit.repositories.ShopRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

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
    public void a_shop_with_an_empty_gift_list() throws MissingInformationException, AlreadyExistingShopException {
        shopRepository.deleteAll();
        shop=shopRegistration.addShop("A", "1 rue de la paix");
        assertTrue(shopRepository.findById(shop.getId()).get().getGiftList().isEmpty());

    }
    @When("the shop adds a gift to the catalog")
    public void the_shop_adds_a_gift_to_the_catalog() throws AlreadyExistingGiftException, ShopNotFoundException {
        gift=new Gift(150,"ring", AccountStatus.VFP);
        gift1=new Gift(10,"cake", AccountStatus.REGULAR);
        catalog.addGift(shop.getId(), gift);



    }
    @Then("the gift is added to the catalog")
    public void the_gift_is_added_to_the_catalog() {
        assertTrue(shopRepository.findById(shop.getId()).get().getGiftList().contains(gift));
        assertTrue(giftRepository.existsById(gift.getGiftId()));
    }
    @Given("a shop with a gift list")
    public void a_shop_with_a_gift_list() throws MissingInformationException, AlreadyExistingGiftException, ShopNotFoundException, AlreadyExistingShopException {
        shopRepository.deleteAll();
        shop=shopRegistration.addShop("A", "1 rue de la paix");
         assertTrue(shopRepository.findById(shop.getId()).get().getGiftList().isEmpty());
        gift=new Gift(150,"ring", AccountStatus.VFP);
        gift1=new Gift(10,"cake", AccountStatus.REGULAR);
        catalog.addGift(shop.getId(), gift);
        assertTrue(giftRepository.existsById(gift.getGiftId()));
    }
    @When("shop includes an existing gift in its catalog.")
    public void shop_includes_an_existing_gift_in_its_catalog() {
        assertEquals(shopRepository.findById(shop.getId()).get().getGiftList().size(),1);
        assertTrue(shopRepository.findById(shop.getId()).get().getGiftList().contains(gift));
        assertThrows(AlreadyExistingGiftException.class, () -> {
            catalog.addGift(shop.getId(), gift);
        });
    }
    @Then("the gift is not added to the catalog because it already exists")
    public void the_gift_is_not_added_to_the_catalog_because_it_already_exists() {
        assertEquals(shopRepository.findById(shop.getId()).get().getGiftList().size(),1);
    }
    @When("the shop remove a gift from the catalog")
    public void the_shop_remove_a_gift_from_the_catalog()throws GiftNotFoundException {
        assertTrue(shopRepository.findById(shop.getId()).get().getGiftList().contains(gift));
        assertEquals(shopRepository.findById(shop.getId()).get().getGiftList().size(),1);
        catalog.removeGift(shop, gift);
    }
    @Then("the gift is removed from the catalog")
    public void the_gift_is_removed_from_the_catalog() {
       assertFalse(shopRepository.findById(shop.getId()).get().getGiftList().contains(gift));
        assertEquals(shopRepository.findById(shop.getId()).get().getGiftList().size(),0);
    }
    @When("shop removes a non-existent gift from its catalog.")
    public void shop_removes_an_existing_gift_from_its_catalog() {
        assertFalse(shopRepository.findById(shop.getId()).get().getGiftList().contains(gift));
        assertThrows(GiftNotFoundException.class, () -> {
            catalog.removeGift(shop, gift);
        });
    }

    @Then("the gift is not removed from the catalog because it does not exist")
    public void the_gift_is_not_removed_from_the_catalog_because_it_does_not_exist() {
        assertFalse(shopRepository.findById(shop.getId()).get().getGiftList().contains(gift));
    }

}
