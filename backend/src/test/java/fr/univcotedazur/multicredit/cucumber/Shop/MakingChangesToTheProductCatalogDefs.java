package fr.univcotedazur.multicredit.cucumber.Shop;

import fr.univcotedazur.multicredit.components.Catalog;
import fr.univcotedazur.multicredit.components.ShopManager;
import fr.univcotedazur.multicredit.entities.Product;
import fr.univcotedazur.multicredit.entities.Shop;
import fr.univcotedazur.multicredit.exceptions.*;
import fr.univcotedazur.multicredit.interfaces.ShopRegistration;
import fr.univcotedazur.multicredit.repositories.CatalogRepository;
import fr.univcotedazur.multicredit.repositories.ShopRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    public void a_shop_with_an_empty_product_list() throws MissingInformationException, AlreadyExistingShopException {
        shopRepository.deleteAll();
        shop=shopRegistration.addShop("A", "1 rue de la paix");
        assertTrue(shopRepository.findById(shop.getId()).get().getProductList().isEmpty());
    }
    @When("the shop adds a product to the catalog")
    public void the_shop_adds_a_product_to_the_catalog() throws AlreadyExistingProductException, ShopNotFoundException {
        product=new  Product("chocolat",1.5,0,0.0);
        product1=new  Product("Cake",1.0,0,0.0);
        catalog.addProductToCatalog(shop.getId(), product);



    }
    @Then("the product is added to the catalog")
    public void the_product_is_added_to_the_catalog() {
        assertTrue(shopRepository.findById(shop.getId()).get().getProductList().contains(product));
        assertTrue(productRepository.existsById(product.getId()));
    }
    @Given("a shop with a product list")
    public void a_shop_with_a_product_list() throws MissingInformationException, AlreadyExistingProductException, AlreadyExistingShopException, ShopNotFoundException {
        shopRepository.deleteAll();
        shop=shopRegistration.addShop("A", "1 rue de la paix");
        assertTrue(shopRepository.findById(shop.getId()).get().getProductList().isEmpty());
        product=new  Product("chocolat",1.5,0,0.0);
        product1=new  Product("Cake",1.0,0,0.0);
        catalog.addProductToCatalog(shop.getId(), product);
        assertTrue(productRepository.existsById(product.getId()));
    }
    @When("shop includes an existing product in its catalog.")
    public void shop_includes_an_existing_product_in_its_catalog() {
        assertEquals(shopRepository.findById(shop.getId()).get().getProductList().size(),1);
        assertTrue(shopRepository.findById(shop.getId()).get().getProductList().contains(product));
        assertThrows(AlreadyExistingProductException.class, () -> {
            catalog.addProductToCatalog(shop.getId(), product);
        });
    }
    @Then("the product is not added to the catalog because it already exists")
    public void the_product_is_not_added_to_the_catalog_because_it_already_exists() {
        assertEquals(shopRepository.findById(shop.getId()).get().getProductList().size(),1);
    }
    @When("the shop remove a product from the catalog")
    public void the_shop_remove_a_product_from_the_catalog() throws ProductNotFoundException {
        assertTrue(shopRepository.findById(shop.getId()).get().getProductList().contains(product));
        assertEquals(shopRepository.findById(shop.getId()).get().getProductList().size(),1);
        catalog.removeProductFromCatalog(shop, product);
    }
    @Then("the product is removed from the catalog")
    public void the_product_is_removed_from_the_catalog() {
       assertFalse(shopRepository.findById(shop.getId()).get().getProductList().contains(product));
        assertEquals(shopRepository.findById(shop.getId()).get().getProductList().size(),0);
    }
    @When("shop removes a non-existent product from its catalog.")
    public void shop_removes_an_existing_product_from_its_catalog() {
        assertFalse(shopRepository.findById(shop.getId()).get().getProductList().contains(product));
        assertThrows(ProductNotFoundException.class, () -> {
            catalog.removeProductFromCatalog(shop, product);
        });
    }

    @Then("the product is not removed from the catalog because it does not exist")
    public void the_product_is_not_removed_from_the_catalog_because_it_does_not_exist() {
        assertFalse(shopRepository.findById(shop.getId()).get().getProductList().contains(product));
    }

}
