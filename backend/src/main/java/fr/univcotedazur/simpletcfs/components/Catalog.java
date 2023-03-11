package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.entities.Product;
import fr.univcotedazur.simpletcfs.entities.Shop;
import fr.univcotedazur.simpletcfs.interfaces.CatalogEditor;
import fr.univcotedazur.simpletcfs.interfaces.CatalogFinder;
import fr.univcotedazur.simpletcfs.interfaces.ShopCatalogEditor;
import fr.univcotedazur.simpletcfs.repositories.CatalogRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class Catalog implements CatalogEditor, ShopCatalogEditor, CatalogFinder {
    private CatalogRepository catalogRepository;
    @Autowired
    public Catalog(CatalogRepository catalogRepository) {
        this.catalogRepository = catalogRepository;
    }
    @Override
    public Optional<Product> findProductById(Long id){
        return catalogRepository.findById(id);
    }
    @Override
    public void editShopCatalog(Shop shop, List<Product> addedProducts, List<Product> removedProducts) {
        if(addedProducts!=null)
            for (Product product : addedProducts) {
                 catalogRepository.save(product);
            //shop.addProduct(product);
            }
        if(removedProducts!=null)
            for (Product product : removedProducts) {
                catalogRepository.delete(product);

            }
        editCatalog(addedProducts, removedProducts);
    }

    @Override
    public void editCatalog(List<Product> addedProducts, List<Product> removedProducts) {
        if(addedProducts!=null)
            for (Product product : addedProducts) {
                addProductToCatalog(product);
            }
        if(removedProducts!=null)
            for (Product product : removedProducts) {
                removeProductFromCatalog(product);
            }
    }
    public void addProductToCatalog(Product product) {
            catalogRepository.save(product);
    }
    public void removeProductFromCatalog(Product product) {
        if(product.getId()!=null){
            catalogRepository.delete(product);
        }

    }

}
