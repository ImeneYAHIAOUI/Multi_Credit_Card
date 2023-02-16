package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.entities.Product;
import fr.univcotedazur.simpletcfs.entities.Shop;
import fr.univcotedazur.simpletcfs.interfaces.CatalogEditor;
import fr.univcotedazur.simpletcfs.interfaces.ShopCatalogEditor;
import fr.univcotedazur.simpletcfs.repositories.CatalogRepository;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static java.lang.System.in;

public class Catalog implements CatalogEditor, ShopCatalogEditor {
    private CatalogRepository catalogRepository;
    @Autowired
    public Catalog(CatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }
    public void editShopCatalog(Shop shop, List<Product> addedProducts, List<Product> removedProducts) {
        for (Product product : addedProducts) {
            shop.getProductList().add(product);
        }
        for (Product product : removedProducts) {
            shop.getProductList().remove(product);        }

    }
    public void editCatalog(List<Product> addedProducts, List<Product> removedProducts) {
        for (Product product : addedProducts) {
            catalogRepository.save(product, product.getId());
        }
        for (Product product : removedProducts) {
            catalogRepository.deleteById( product.getId());
        }
    }

}
