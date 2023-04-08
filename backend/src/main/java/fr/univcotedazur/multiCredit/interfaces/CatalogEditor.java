package fr.univcotedazur.multiCredit.interfaces;

import fr.univcotedazur.multiCredit.entities.Gift;
import fr.univcotedazur.multiCredit.entities.Product;
import fr.univcotedazur.multiCredit.entities.Shop;
import fr.univcotedazur.multiCredit.exceptions.*;

import java.util.List;

public interface CatalogEditor {
    void editShopCatalog(Shop shop, List<Product> addedProducts, List<Product> removedProducts)throws ShopNotFoundException,AlreadyExistingProductException, ProductNotFoundException;
    void addProductToCatalog(Long id,Product product) throws AlreadyExistingProductException, ShopNotFoundException;
    void removeProductFromCatalog(Shop shop,Product product) throws ProductNotFoundException;
    void addGift(Long id, Gift gift)throws AlreadyExistingGiftException,ShopNotFoundException;
    void removeGift(Shop shop, Gift gift) throws GiftNotFoundException;
    void addDiscountToProduct(Shop shop, Product product, double discount)throws ProductNotFoundException;
}
