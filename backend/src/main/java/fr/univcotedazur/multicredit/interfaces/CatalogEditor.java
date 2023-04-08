package fr.univcotedazur.multicredit.interfaces;

import fr.univcotedazur.multicredit.entities.Gift;
import fr.univcotedazur.multicredit.entities.Product;
import fr.univcotedazur.multicredit.entities.Shop;
import fr.univcotedazur.multicredit.exceptions.*;

import java.util.List;

public interface CatalogEditor {
    void editShopCatalog(Shop shop, List<Product> addedProducts, List<Product> removedProducts)throws ShopNotFoundException,AlreadyExistingProductException, ProductNotFoundException;
    void addProductToCatalog(Long id,Product product) throws AlreadyExistingProductException, ShopNotFoundException;
    void removeProductFromCatalog(Shop shop,Product product) throws ProductNotFoundException;
    void addGift(Long id, Gift gift)throws AlreadyExistingGiftException,ShopNotFoundException;
    void removeGift(Shop shop, Gift gift) throws GiftNotFoundException;

    void addDiscountToProduct(Shop shop, Product product, double discount) throws ProductNotFoundException;
}
