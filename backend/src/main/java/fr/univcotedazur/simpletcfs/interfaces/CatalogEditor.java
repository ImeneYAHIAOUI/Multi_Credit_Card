package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.Product;
import fr.univcotedazur.simpletcfs.entities.Shop;
import fr.univcotedazur.simpletcfs.exceptions.AlreadyExistingProductException;
import fr.univcotedazur.simpletcfs.exceptions.ProductNotFoundException;

import java.util.List;

public interface CatalogEditor {
    void editShopCatalog(Shop shop, List<Product> addedProducts, List<Product> removedProducts)throws AlreadyExistingProductException, ProductNotFoundException;
    void addProductToCatalog(Shop shop,Product product) throws AlreadyExistingProductException;
    void removeProductFromCatalog(Shop shop,Product product) throws ProductNotFoundException;
}
