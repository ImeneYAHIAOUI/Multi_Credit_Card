package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.entities.Product;
import fr.univcotedazur.simpletcfs.entities.Shop;
import fr.univcotedazur.simpletcfs.exceptions.MissingInformationException;
import fr.univcotedazur.simpletcfs.interfaces.ShopRegistration;
import fr.univcotedazur.simpletcfs.repositories.CatalogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CatalogTest {
    @Autowired
    private ShopRegistration shopRegistration;
    @Autowired
    private Catalog catalog;
    @Autowired
    private ShopManager shopManager;
    @Autowired
    CatalogRepository catalogRepository;
    private Shop shop;
    private Product product;
    private Product product1;
    private Product product2;
    private Product product3;
    private Product product4;
    @BeforeEach
    public void setUp() throws MissingInformationException {

         product=new Product("ring",1.0,0);
         product1=new Product("Cookie",2.0,0);
         product2=new Product("Cake",1.0,0);
         product3=new Product("ring",1.0,0);
        product4=new Product("chocolat",1.5,0);
        shop=shopRegistration.addShop("A", "1 rue de la paix", new ArrayList<>(), new ArrayList<>(),null);
        product.setShop(shop);
        product1.setShop(shop);
        product2.setShop(shop);
        catalog.addProductToCatalog( product);
        catalog.addProductToCatalog( product1);
        catalog.addProductToCatalog( product2);

    }

    @Test
    public void  editCatalogTest() {
        assertTrue(catalog.findProductById(product.getId()).isPresent());
        assertTrue(catalog.findProductById(product1.getId()).isPresent());
        assertTrue(catalog.findProductById(product2.getId()).isPresent());
        product3.setShop(shop);
        catalogRepository.save(product3);
        catalog.editCatalog(List.of(product3),List.of(product));
        assertTrue(catalog.findProductById(product.getId()).isEmpty());
        assertTrue(catalog.findProductById(product3.getId()).isPresent());
    }
    @Test
    public void  editCatalogTest1() {

        assertTrue(catalog.findProductById(product.getId()).isPresent());
        assertTrue(catalog.findProductById(product1.getId()).isPresent());
        assertTrue(catalog.findProductById(product2.getId()).isPresent());
        product3.setShop(shop);
        catalogRepository.save(product3);
        catalog.editCatalog(List.of(product),List.of(product3));
        assertTrue(catalog.findProductById(product3.getId()).isEmpty());
        product4.setShop(shop);
        catalog.editCatalog(List.of(product4),null);
        assertTrue(catalog.findProductById(product4.getId()).isPresent());
    }
    @Test
    public void editShopCatalogTest() {
        assertTrue(catalog.findProductById(product.getId()).isPresent());
        assertTrue(catalog.findProductById(product1.getId()).isPresent());
        assertTrue(catalog.findProductById(product2.getId()).isPresent());
        assertNull(product3.getId());
        product3.setShop(shop);
        catalog.editShopCatalog(shop,List.of(product3),List.of(product));
        assertFalse(shopManager.getProductList(shop).contains(product));
        assertTrue(catalogRepository.findById(product3.getId()).isPresent());
        assertTrue(catalog.findProductById(product.getId()).isEmpty());
    }
    @Test
    public void editShopCatalogTest1() {
        assertTrue(catalog.findProductById(product.getId()).isPresent());
        assertTrue(catalog.findProductById(product1.getId()).isPresent());
        assertTrue(catalog.findProductById(product2.getId()).isPresent());
        assertNull(product3.getId());
        product3.setShop(shop);
        catalog.editShopCatalog(shop,List.of(product3),null);
        assertTrue(catalogRepository.findById(product3.getId()).isPresent());
        //assertTrue(shopManager.getProductList(shop).contains(product3));
    }
}
