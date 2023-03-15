package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.entities.Product;
import fr.univcotedazur.simpletcfs.entities.Shop;
import fr.univcotedazur.simpletcfs.exceptions.AlreadyExistingProductException;
import fr.univcotedazur.simpletcfs.exceptions.ProductNotFoundException;
import fr.univcotedazur.simpletcfs.interfaces.CatalogEditor;
import fr.univcotedazur.simpletcfs.interfaces.CatalogFinder;
import fr.univcotedazur.simpletcfs.repositories.CatalogRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class Catalog implements CatalogEditor, CatalogFinder {
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
    public void editShopCatalog(Shop shop, List<Product> addedProducts, List<Product> removedProducts)throws AlreadyExistingProductException , ProductNotFoundException {
        if(addedProducts!=null)
            for (Product product : addedProducts) {
                addProductToCatalog(shop,product);

            }
        if(removedProducts!=null)
            for (Product product : removedProducts) {
                removeProductFromCatalog(shop,product);
            }
    }


    @Override
    public void addProductToCatalog(Shop shop,Product product) throws AlreadyExistingProductException {
        if(catalogRepository.findAll().stream().noneMatch(p-> p.equals(product))) {
            shop.addProduct(product);
            catalogRepository.save(product);
        }else
            throw new AlreadyExistingProductException();
    }
    @Override
    public void removeProductFromCatalog(Shop shop,Product product) throws ProductNotFoundException {
        if(product.getId()!=null &&
                catalogRepository.findAll().stream().anyMatch(p-> p.getId().equals(product.getId()))
        ){
            shop.removeProduct(product);
            catalogRepository.delete(product);
        }
        else
            throw new ProductNotFoundException();

    }

}
