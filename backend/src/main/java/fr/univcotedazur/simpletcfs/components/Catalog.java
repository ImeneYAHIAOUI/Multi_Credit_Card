package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.entities.Gift;
import fr.univcotedazur.simpletcfs.entities.Product;
import fr.univcotedazur.simpletcfs.entities.Shop;
import fr.univcotedazur.simpletcfs.exceptions.AlreadyExistingGiftException;
import fr.univcotedazur.simpletcfs.exceptions.AlreadyExistingProductException;
import fr.univcotedazur.simpletcfs.exceptions.GiftNotFoundException;
import fr.univcotedazur.simpletcfs.exceptions.ProductNotFoundException;
import fr.univcotedazur.simpletcfs.interfaces.CatalogEditor;
import fr.univcotedazur.simpletcfs.interfaces.CatalogFinder;
import fr.univcotedazur.simpletcfs.repositories.CatalogRepository;

import fr.univcotedazur.simpletcfs.repositories.GiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class Catalog implements CatalogEditor, CatalogFinder {
    private CatalogRepository catalogRepository;
    private GiftRepository giftRepository;
    @Autowired
    public Catalog(CatalogRepository catalogRepository, GiftRepository giftRepository) {
        this.catalogRepository = catalogRepository;
        this.giftRepository = giftRepository;
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
    public void addGift(Shop shop, Gift gift)throws AlreadyExistingGiftException {
        if(gift!=null && giftRepository.findAll().stream().noneMatch(p-> p.equals(gift))) {
            gift.setShop(shop);
            shop.addGift(gift);
            giftRepository.save(gift);
        }else
            throw new AlreadyExistingGiftException();
    }

    @Override
    public void removeGift(Shop shop, Gift gift) throws GiftNotFoundException {
        if(gift!=null && giftRepository.findAll().stream().anyMatch(p-> p.equals(gift)) ){
            giftRepository.delete(gift);
            shop.getGiftList().remove(gift);
        }else
            throw new GiftNotFoundException();

    }

    @Override
    public void addProductToCatalog(Shop shop,Product product) throws AlreadyExistingProductException {
        if(catalogRepository.findAll().stream().noneMatch(p-> p.equals(product))) {
            product.setShop(shop);
            shop.addProduct(product);
            catalogRepository.save(product);
        }else
            throw new AlreadyExistingProductException();
    }
    @Override
    public void removeProductFromCatalog(Shop shop,Product product) throws ProductNotFoundException {
        if(product!=null && product.getId()!=null &&
                catalogRepository.findAll().stream().anyMatch(p-> p.getId().equals(product.getId()))
        ){
            shop.removeProduct(product);
            catalogRepository.delete(product);
        }
        else
            throw new ProductNotFoundException();

    }
    @Override
    public void addDiscountToProduct(Shop shop, Product product, double discount)throws ProductNotFoundException{
        if(shop!= null && product!= null && discount>=0.0 && discount<=1.0){
            if(shop.getProductList().stream().anyMatch(p-> p.getId().equals(product.getId()))){
                product.setDiscountPercentage(discount);
                catalogRepository.save(product);
            }else
                throw new ProductNotFoundException();

        }
    }

}
