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
    public Optional<Product> findProductById(UUID id){
        return catalogRepository.findById(id);
    }
    @Override
    public void editShopCatalog(Shop shop, List<Product> addedProducts, List<Product> removedProducts) {
        if(addedProducts!=null)
            for (Product product : addedProducts) {
            shop.addProduct(product);
            }
        if(removedProducts!=null)
            for (Product product : removedProducts) {
            shop.removeProduct(product);
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
        if(!catalogRepository.existsById(product.getId()))
            catalogRepository.save(product, product.getId());
    }
    public void removeProductFromCatalog(Product product) {
        if(catalogRepository.existsById(product.getId()))
            catalogRepository.deleteById(product.getId());
    }

}
