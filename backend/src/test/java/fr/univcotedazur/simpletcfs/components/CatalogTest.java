package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.entities.Product;
import fr.univcotedazur.simpletcfs.entities.Shop;
import fr.univcotedazur.simpletcfs.exceptions.AlreadyExistingProductException;
import fr.univcotedazur.simpletcfs.exceptions.MissingInformationException;
import fr.univcotedazur.simpletcfs.exceptions.ProductNotFoundException;
import fr.univcotedazur.simpletcfs.interfaces.ShopRegistration;
import fr.univcotedazur.simpletcfs.repositories.CatalogRepository;
import fr.univcotedazur.simpletcfs.repositories.ShopRepository;
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
    @Autowired
    ShopRepository  shopRepository;
    @BeforeEach
    public void setUp() throws MissingInformationException, AlreadyExistingProductException {
        shopRepository.deleteAll();
         product=new Product("ring",1.0,0);
         product1=new Product("Cookie",2.0,0);
         product2=new Product("Cake",1.0,0);
         product3=new Product("phone",1.0,0);
        product4=new Product("chocolat",1.5,0);
        shop=shopRegistration.addShop("A", "1 rue de la paix");
        product.setShop(shop);
        product1.setShop(shop);
        product2.setShop(shop);
        catalog.addProductToCatalog(shop, product);
        catalog.addProductToCatalog( shop,product1);
        catalog.addProductToCatalog(shop, product2);
    }

    @Test
    public void  editTest() throws AlreadyExistingProductException, ProductNotFoundException {
        assertTrue(catalog.findProductById(product.getId()).isPresent());
        assertTrue(catalog.findProductById(product1.getId()).isPresent());
        assertTrue(catalog.findProductById(product2.getId()).isPresent());
        product3.setShop(shop);
        catalog.addProductToCatalog(shop,product3);
        assertTrue(catalog.findProductById(product3.getId()).isPresent());
        catalog.removeProductFromCatalog(shop,product);
        assertTrue(catalog.findProductById(product.getId()).isEmpty());
    }
    @Test
    public void  editTest1() throws AlreadyExistingProductException, ProductNotFoundException {
        assertTrue(catalog.findProductById(product.getId()).isPresent());
        assertTrue(catalog.findProductById(product1.getId()).isPresent());
        assertTrue(catalog.findProductById(product2.getId()).isPresent());
        assertTrue(shop.getProductList().contains(product));
        assertTrue(shop.getProductList().contains(product1));
        assertTrue(shop.getProductList().contains(product2));
        catalog.removeProductFromCatalog(shop,product);
        assertTrue(catalog.findProductById(product.getId()).isEmpty());
        assertFalse(shop.getProductList().contains(product));
        assertThrows(ProductNotFoundException.class,()->catalog.removeProductFromCatalog(shop,product));

    }
    @Test
    public void editShopCatalogTest() throws AlreadyExistingProductException, ProductNotFoundException{
        assertTrue(catalog.findProductById(product.getId()).isPresent());
        assertTrue(catalog.findProductById(product1.getId()).isPresent());
        assertTrue(catalog.findProductById(product2.getId()).isPresent());
        assertTrue(shop.getProductList().contains(product));
        assertTrue(shop.getProductList().contains(product1));
        assertTrue(shop.getProductList().contains(product2));
        assertNull(product3.getId());
        product3.setShop(shop);
        assertFalse(shop.getProductList().contains(product3));
        catalog.editShopCatalog(shop,List.of(product3),List.of(product));
        assertTrue(shop.getProductList().contains(product3));
        assertFalse(shop.getProductList().contains(product));
        assertTrue(catalogRepository.findById(product3.getId()).isPresent());
        assertTrue(catalog.findProductById(product.getId()).isEmpty());
    }
    @Test
    public void editShopCatalogTest1() throws AlreadyExistingProductException, ProductNotFoundException {
        assertTrue(catalog.findProductById(product.getId()).isPresent());
        assertTrue(catalog.findProductById(product1.getId()).isPresent());
        assertTrue(catalog.findProductById(product2.getId()).isPresent());
        assertTrue(shop.getProductList().contains(product));
        assertTrue(shop.getProductList().contains(product1));
        assertTrue(shop.getProductList().contains(product2));
        assertNull(product3.getId());
        product3.setShop(shop);
        catalog.editShopCatalog(shop,List.of(product3),null);
        assertTrue(catalogRepository.findById(product3.getId()).isPresent());
        assertTrue(shop.getProductList().contains(product3));
        catalog.editShopCatalog(shop,null,List.of(product3));
        assertTrue(catalogRepository.findById(product3.getId()).isEmpty());
        assertFalse(shop.getProductList().contains(product3));
    }
}
