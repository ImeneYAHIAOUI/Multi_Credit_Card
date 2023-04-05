package fr.univcotedazur.multiCredit.interfaces;

import fr.univcotedazur.multiCredit.entities.Gift;
import fr.univcotedazur.multiCredit.entities.Product;
import fr.univcotedazur.multiCredit.entities.Shop;
import fr.univcotedazur.multiCredit.exceptions.AlreadyExistingGiftException;
import fr.univcotedazur.multiCredit.exceptions.AlreadyExistingProductException;
import fr.univcotedazur.multiCredit.exceptions.GiftNotFoundException;
import fr.univcotedazur.multiCredit.exceptions.ProductNotFoundException;

import java.util.List;

public interface CatalogEditor {
    void editShopCatalog(Shop shop, List<Product> addedProducts, List<Product> removedProducts)throws AlreadyExistingProductException, ProductNotFoundException;
    void addProductToCatalog(Shop shop,Product product) throws AlreadyExistingProductException;
    void removeProductFromCatalog(Shop shop,Product product) throws ProductNotFoundException;
    void addGift(Shop shop, Gift gift)throws AlreadyExistingGiftException;
    void removeGift(Shop shop, Gift gift) throws GiftNotFoundException;
    void addDiscountToProduct(Shop shop, Product product, double discount)throws ProductNotFoundException;
}
