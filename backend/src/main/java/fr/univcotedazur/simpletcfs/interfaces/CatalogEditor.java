package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.Gift;
import fr.univcotedazur.simpletcfs.entities.Product;
import fr.univcotedazur.simpletcfs.entities.Shop;
import fr.univcotedazur.simpletcfs.exceptions.AlreadyExistingGiftException;
import fr.univcotedazur.simpletcfs.exceptions.AlreadyExistingProductException;
import fr.univcotedazur.simpletcfs.exceptions.GiftNotFoundException;
import fr.univcotedazur.simpletcfs.exceptions.ProductNotFoundException;

import java.util.List;

public interface CatalogEditor {
    void editShopCatalog(Shop shop, List<Product> addedProducts, List<Product> removedProducts)throws AlreadyExistingProductException, ProductNotFoundException;
    void addProductToCatalog(Shop shop,Product product) throws AlreadyExistingProductException;
    void removeProductFromCatalog(Shop shop,Product product) throws ProductNotFoundException;
    void addGift(Shop shop, Gift gift)throws AlreadyExistingGiftException;
    void removeGift(Shop shop, Gift gift) throws GiftNotFoundException;
    void addDiscountToProduct(Shop shop, Product product, double discount)throws ProductNotFoundException;
}
