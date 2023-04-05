package fr.univcotedazur.multiCredit.components;

import fr.univcotedazur.multiCredit.entities.AccountStatus;
import fr.univcotedazur.multiCredit.entities.Gift;
import fr.univcotedazur.multiCredit.entities.Product;
import fr.univcotedazur.multiCredit.entities.Shop;
import fr.univcotedazur.multiCredit.exceptions.*;
import fr.univcotedazur.multiCredit.interfaces.ShopRegistration;
import fr.univcotedazur.multiCredit.repositories.CatalogRepository;
import fr.univcotedazur.multiCredit.repositories.GiftRepository;
import fr.univcotedazur.multiCredit.repositories.ShopRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class CatalogTest {
    @Autowired
    private ShopRegistration shopRegistration;
    @Autowired
    private Catalog catalog;
    @Autowired
    private ShopManager shopManager;
    @Autowired
    GiftRepository giftRepository;
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
    Gift gift;
    Gift gift1;
    Gift gift2;
    @BeforeEach
    public void setUp() throws MissingInformationException, AlreadyExistingProductException,
            AlreadyExistingGiftException{
        shopRepository.deleteAll();
         product=new Product("ring",1.0,0,0.0);
         product1=new Product("Cookie",2.0,0,0.0);
         product2=new Product("Cake",1.0,0,0.0);
         product3=new Product("phone",1.0,0,0.0);
        product4=new Product("chocolat",1.5,0,0.0);
        shop=shopRegistration.addShop("A", "1 rue de la paix");
        product.setShop(shop);
        product1.setShop(shop);
        product2.setShop(shop);
        catalog.addProductToCatalog(shop, product);
        catalog.addProductToCatalog( shop,product1);
        catalog.addProductToCatalog(shop, product2);
        gift=new Gift(150,"ring", AccountStatus.VFP);
        gift1=new Gift(10,"cake", AccountStatus.REGULAR);
        gift.setShop(shop);
        gift1.setShop(shop);
        catalog.addGift(shop,gift);
        catalog.addGift(shop,gift1);
        gift2=new Gift(10,"cookie", AccountStatus.VFP);
    }
    @Test
      void addGiftTest()throws AlreadyExistingGiftException {
        assertTrue(giftRepository.findById(gift.getGiftId()).isPresent());
        assertTrue(giftRepository.findById(gift1.getGiftId()).isPresent());
        assertTrue(shop.getGiftList().contains(gift1));
        assertTrue(shop.getGiftList().contains(gift));
        assertFalse(shop.getGiftList().contains(gift2));
        assertNull(gift2.getGiftId());
        catalog.addGift(shop,gift2);
        assertTrue(giftRepository.findById(gift2.getGiftId()).isPresent());
        assertTrue(shop.getGiftList().contains(gift2));
        assertEquals(shop, gift2.getShop());
    }
    @Test
      void addGiftTest2()throws AlreadyExistingGiftException {
        assertTrue(giftRepository.findById(gift.getGiftId()).isPresent());
        assertTrue(giftRepository.findById(gift1.getGiftId()).isPresent());
        assertTrue(shop.getGiftList().contains(gift1));
        assertTrue(shop.getGiftList().contains(gift));
        assertThrows(AlreadyExistingGiftException.class,()->catalog.addGift(shop,gift));
    }
    @Test
      void RemoveGiftTest() {
        assertTrue(giftRepository.findById(gift.getGiftId()).isPresent());
        assertTrue(giftRepository.findById(gift1.getGiftId()).isPresent());
        assertTrue(shop.getGiftList().contains(gift1));
        assertTrue(shop.getGiftList().contains(gift));
        assertThrows(GiftNotFoundException.class,()->catalog.removeGift(shop,gift2));
    }
    @Test
      void RemoveGiftTest1()throws GiftNotFoundException {
        assertTrue(giftRepository.findById(gift.getGiftId()).isPresent());
        assertTrue(giftRepository.findById(gift1.getGiftId()).isPresent());
        assertTrue(shop.getGiftList().contains(gift1));
        assertTrue(shop.getGiftList().contains(gift));
        catalog.removeGift(shop,gift);
        assertTrue(giftRepository.findById(gift.getGiftId()).isEmpty());
        assertFalse(shop.getGiftList().contains(gift));
    }
    @Test
     void  editTest() throws AlreadyExistingProductException, ProductNotFoundException {
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
     void  editTest1() throws AlreadyExistingProductException, ProductNotFoundException {
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
     void editShopCatalogTest() throws AlreadyExistingProductException, ProductNotFoundException{
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
     void editShopCatalogTest1() throws AlreadyExistingProductException, ProductNotFoundException {
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
