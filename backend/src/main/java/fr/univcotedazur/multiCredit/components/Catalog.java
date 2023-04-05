package fr.univcotedazur.multiCredit.components;

import fr.univcotedazur.multiCredit.entities.Gift;
import fr.univcotedazur.multiCredit.entities.Product;
import fr.univcotedazur.multiCredit.entities.Shop;
import fr.univcotedazur.multiCredit.exceptions.AlreadyExistingGiftException;
import fr.univcotedazur.multiCredit.exceptions.AlreadyExistingProductException;
import fr.univcotedazur.multiCredit.exceptions.GiftNotFoundException;
import fr.univcotedazur.multiCredit.exceptions.ProductNotFoundException;
import fr.univcotedazur.multiCredit.interfaces.CatalogEditor;
import fr.univcotedazur.multiCredit.interfaces.CatalogFinder;
import fr.univcotedazur.multiCredit.repositories.CatalogRepository;

import fr.univcotedazur.multiCredit.repositories.GiftRepository;
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
    public Optional<Gift> findGiftById(Long id){
        return giftRepository.findById(id);
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
        }else{
            System.out.println("product already exists");
            throw new AlreadyExistingProductException();}
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