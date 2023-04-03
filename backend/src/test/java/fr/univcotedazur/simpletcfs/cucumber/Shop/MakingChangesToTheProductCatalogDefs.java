package fr.univcotedazur.simpletcfs.cucumber.Shop;

import fr.univcotedazur.simpletcfs.components.Catalog;
import fr.univcotedazur.simpletcfs.components.ShopManager;
import fr.univcotedazur.simpletcfs.entities.AccountStatus;
import fr.univcotedazur.simpletcfs.entities.Product;
import fr.univcotedazur.simpletcfs.entities.Shop;
import fr.univcotedazur.simpletcfs.entities.WeekDay;
import fr.univcotedazur.simpletcfs.exceptions.AlreadyExistingProductException;
import fr.univcotedazur.simpletcfs.exceptions.MissingInformationException;
import fr.univcotedazur.simpletcfs.exceptions.ProductNotFoundException;
import fr.univcotedazur.simpletcfs.interfaces.ShopRegistration;
import fr.univcotedazur.simpletcfs.repositories.CatalogRepository;
import fr.univcotedazur.simpletcfs.repositories.GiftRepository;
import fr.univcotedazur.simpletcfs.repositories.ShopRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MakingChangesToTheProductCatalogDefs {


    @Autowired
    private ShopManager shopManager;
    @Autowired
    private ShopRegistration shopRegistration;
    @Autowired
    private CatalogRepository productRepository;
    @Autowired
    private Catalog catalog;
    @Autowired
    ShopRepository shopRepository;
    Shop shop;
    Product product;
    Product product1;
    @Given("a shop with an empty product list")
    public void a_shop_with_an_empty_product_list()throws MissingInformationException {
        shopRepository.deleteAll();
        shop=shopRegistration.addShop("A", "1 rue de la paix");
        assertTrue(shop.getProductList().isEmpty());
    }
    @When("the shop adds a product to the catalog")
    public void the_shop_adds_a_product_to_the_catalog() throws AlreadyExistingProductException {
        product=new  Product("chocolat",1.5,0,0.0);
        product1=new  Product("Cake",1.0,0,0.0);
        catalog.addProductToCatalog(shop, product);



    }
    @Then("the product is added to the catalog")
    public void the_product_is_added_to_the_catalog() {
        assertTrue(shop.getProductList().contains(product));
        assertTrue(productRepository.existsById(product.getId()));
    }
    @Given("a shop with a product list")
    public void a_shop_with_a_product_list() throws MissingInformationException, AlreadyExistingProductException{
        shopRepository.deleteAll();
        shop=shopRegistration.addShop("A", "1 rue de la paix");
        assertTrue(shop.getProductList().isEmpty());
        product=new  Product("chocolat",1.5,0,0.0);
        product1=new  Product("Cake",1.0,0,0.0);
        catalog.addProductToCatalog(shop, product);
        assertTrue(productRepository.existsById(product.getId()));
    }
    @When("shop includes an existing product in its catalog.")
    public void shop_includes_an_existing_product_in_its_catalog() {
        assertEquals(shop.getProductList().size(),1);
        assertTrue(shop.getProductList().contains(product));
        assertThrows(AlreadyExistingProductException.class, () -> {
            catalog.addProductToCatalog(shop, product);
        });
    }
    @Then("the product is not added to the catalog because it already exists")
    public void the_product_is_not_added_to_the_catalog_because_it_already_exists() {
        assertEquals(shop.getProductList().size(),1);
    }
    @When("the shop remove a product from the catalog")
    public void the_shop_remove_a_product_from_the_catalog() throws ProductNotFoundException {
        assertTrue(shop.getProductList().contains(product));
        assertEquals(shop.getProductList().size(),1);
        catalog.removeProductFromCatalog(shop, product);
    }
    @Then("the product is removed from the catalog")
    public void the_product_is_removed_from_the_catalog() {
       assertFalse(shop.getProductList().contains(product));
        assertEquals(shop.getProductList().size(),0);
    }
    @When("shop removes a non-existent product from its catalog.")
    public void shop_removes_an_existing_product_from_its_catalog() {
        assertFalse(shop.getProductList().contains(product));
        assertThrows(ProductNotFoundException.class, () -> {
            catalog.removeProductFromCatalog(shop, product);
        });
    }

    @Then("the product is not removed from the catalog because it does not exist")
    public void the_product_is_not_removed_from_the_catalog_because_it_does_not_exist() {
        assertFalse(shop.getProductList().contains(product));
    }

}
