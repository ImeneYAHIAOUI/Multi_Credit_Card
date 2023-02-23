package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.entities.Planning;
import fr.univcotedazur.simpletcfs.entities.Product;
import fr.univcotedazur.simpletcfs.entities.Shop;
import fr.univcotedazur.simpletcfs.entities.WeekDay;
import fr.univcotedazur.simpletcfs.exceptions.MissingInformationException;
import fr.univcotedazur.simpletcfs.interfaces.ShopRegistration;
import fr.univcotedazur.simpletcfs.repositories.CatalogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CatalogTest {
    @Autowired
    private ShopRegistration shopRegistration;
    @Autowired
    private Catalog catalog;
    private Shop shop;
    private Product product;
    private Product product1;
    private Product product2;
    private Product product3;
    private Product product4;
    @BeforeEach
    public void setUp() throws MissingInformationException {
        Map<WeekDay, Planning> planning=new HashMap();
        planning.put(WeekDay.Friday,new Planning(LocalTime.of(10,00),LocalTime.of(15,00)));
        planning.put(WeekDay.Saturday,new Planning(LocalTime.of(10,00),LocalTime.of(14,00)));
        planning.put(WeekDay.Monday,new Planning(LocalTime.of(9,00),LocalTime.of(19,00)));
         product=new Product(UUID.randomUUID(),"ring",1.0,2);
         product1=new Product(UUID.randomUUID(),"Cookie",2.0,4);
         product2=new Product(UUID.randomUUID(),"Cake",1.0,1);
         product3=new Product(UUID.randomUUID(),"ring",1.0,4);
        product4=new Product(UUID.randomUUID(),"chocolat",1.5,1);
        catalog.addProductToCatalog( product);
        catalog.addProductToCatalog( product1);
        catalog.addProductToCatalog( product2);
        List<Product> productList=new ArrayList<>();
        productList.add(product);
        productList.add(product1);
        productList.add(product2);
        shop=shopRegistration.addShop("A", "1 rue de la paix", planning, productList);

    }

    @Test
    public void  editCatalogTest() {
        assertTrue(catalog.findProductById(product.getId()).isPresent());
        assertTrue(catalog.findProductById(product1.getId()).isPresent());
        assertTrue(catalog.findProductById(product2.getId()).isPresent());
        catalog.editCatalog(List.of(product3),List.of(product));
        assertTrue(catalog.findProductById(product.getId()).isEmpty());
        assertTrue(catalog.findProductById(product3.getId()).isPresent());
    }
    @Test
    public void  editCatalogTest1() {
        assertTrue(catalog.findProductById(product.getId()).isPresent());
        assertTrue(catalog.findProductById(product1.getId()).isPresent());
        assertTrue(catalog.findProductById(product2.getId()).isPresent());
        catalog.editCatalog(List.of(product),List.of(product3));
        assertTrue(catalog.findProductById(product3.getId()).isEmpty());
        catalog.editCatalog(List.of(product4),null);
        assertTrue(catalog.findProductById(product4.getId()).isPresent());
    }
    @Test
    public void editShopCatalogTest() {
        assertTrue(catalog.findProductById(product.getId()).isPresent());
        assertTrue(catalog.findProductById(product1.getId()).isPresent());
        assertTrue(catalog.findProductById(product2.getId()).isPresent());
        assertFalse(shop.getProductList().contains(product3));
        assertTrue(shop.getProductList().contains(product1));
        assertTrue(shop.getProductList().contains(product2));
        assertTrue(shop.getProductList().contains(product));
        catalog.editShopCatalog(shop,List.of(product3),List.of(product));
        assertFalse(shop.getProductList().contains(product));
        assertTrue(shop.getProductList().contains(product3));
        assertTrue(catalog.findProductById(product.getId()).isEmpty());
    }
    @Test
    public void editShopCatalogTest1() {
        assertTrue(catalog.findProductById(product.getId()).isPresent());
        assertTrue(catalog.findProductById(product1.getId()).isPresent());
        assertTrue(catalog.findProductById(product2.getId()).isPresent());
        assertFalse(shop.getProductList().contains(product3));
        assertTrue(shop.getProductList().contains(product1));
        assertTrue(shop.getProductList().contains(product2));
        assertTrue(shop.getProductList().contains(product));
        catalog.editShopCatalog(shop,List.of(product3),null);
        assertTrue(shop.getProductList().contains(product3));
    }
}
