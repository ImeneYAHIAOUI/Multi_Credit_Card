package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.Product;
import fr.univcotedazur.simpletcfs.entities.Shop;

import java.util.List;

public interface ShopCatalogEditor {
    void editShopCatalog(Shop shop, List<Product> addedProducts, List<Product> removedProducts);
}
