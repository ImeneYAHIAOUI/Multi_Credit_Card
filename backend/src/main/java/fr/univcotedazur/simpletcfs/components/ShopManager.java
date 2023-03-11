package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.entities.*;
import fr.univcotedazur.simpletcfs.exceptions.GiftNotFoundException;
import fr.univcotedazur.simpletcfs.exceptions.ProductNotFoundException;
import fr.univcotedazur.simpletcfs.interfaces.ShopFinder;
import fr.univcotedazur.simpletcfs.interfaces.ShopHandler;
import fr.univcotedazur.simpletcfs.interfaces.ShopkeeperFinder;
import fr.univcotedazur.simpletcfs.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Component

public class ShopManager implements ShopHandler, ShopFinder, ShopkeeperFinder{

    private ShopRepository shopRepository;
    private ShopKeeperAccountRepository shopKeeperAccountRepository;
    private GiftRepository giftRepository;
    private CatalogRepository catalogRepository;
    private  PlanningRepository planningRepository;
    @Autowired
    public ShopManager(ShopRepository shopRepository,
                       ShopKeeperAccountRepository shopKeeperAccountRepository,
                       GiftRepository giftRepository,
                       CatalogRepository catalogRepository, PlanningRepository  planningRepository){
        this.shopRepository = shopRepository;
        this.shopKeeperAccountRepository = shopKeeperAccountRepository;
        this.giftRepository = giftRepository;
        this.catalogRepository = catalogRepository;
        this.planningRepository=planningRepository;
    }
    public Optional<Planning> findPlanningByDay(Shop shop, WeekDay day){
        return getPlanningList(shop).stream().filter(plan-> plan.getDayWorking().equals(day)).findFirst();
    }
    public List<Product> getProductList(Shop shop) {
        return catalogRepository.findAll().stream().filter(product ->
                product.getShop().getId().equals(shop.getId())).toList();
    }
    public List<Gift> getGiftList(Shop shop) {
        return giftRepository.findAll().stream().filter(gift ->
                gift.getShop().getId().equals(shop.getId())).toList();
    }
    public List<Planning> getPlanningList(Shop shop) {
        return planningRepository.findAll().stream().filter(planning ->
                planning.getShop().getId().equals(shop.getId())).toList();
    }
    @Override
    public void modifyPlanning(Shop shop, WeekDay day, LocalTime OpeningHours, LocalTime ClosingHours){
        if(shop!= null && day!= null ){
            if( getPlanningList(shop).stream().filter(plan-> plan.getDayWorking().equals(day)).findFirst().isEmpty()){
                if(OpeningHours!=null && ClosingHours!=null && OpeningHours.isBefore(ClosingHours) ){
                    Planning planning =new Planning(day,OpeningHours, ClosingHours);
                    planning.setShop(shop);
                    planningRepository.save(planning);
                }
            }
            else{
                Planning planning = getPlanningList(shop).stream().filter(plan-> plan.getDayWorking().equals(day)).findFirst().get();
                if(OpeningHours!=null && ClosingHours!=null){
                    if( OpeningHours.isBefore(ClosingHours)){
                        planning.setOpeningHours(OpeningHours);
                        planningRepository.updateOpeningHours(OpeningHours,planning.getId());
                        planning.setClosingHours(ClosingHours);
                        planningRepository.updateClosingHours(ClosingHours, planning.getId());
                    }
                }else if(OpeningHours==null && ClosingHours!=null){
                    if(planning.getOpeningHours().isBefore(ClosingHours)) {
                        planning.setClosingHours(ClosingHours);
                        planningRepository.updateClosingHours(ClosingHours, planning.getId());
                    }
                }else if( OpeningHours!=null  ){
                    if(OpeningHours.isBefore(planning.getClosingHours())) {
                        planning.setOpeningHours(OpeningHours);
                        planningRepository.updateOpeningHours(OpeningHours,planning.getId());
                    }
                }
            }
        }
    }
    @Override
    public void modifyName(Shop shop, String name){
        if(name!=null){
            shop.setName(name);
            shopRepository.updateName(name, shop.getId());
        }
    }

    @Override
    public void modifyAddress(Shop shop, String address){
        if(address!=null){
            shop.setAddress(address);
            shopRepository.updateAddress(address, shop.getId());
        }
    }
    @Override
    public void addGift(Shop shop, Gift gift){
        if(gift!=null && !getGiftList(shop).contains(gift)){
            getGiftList(shop).add(gift);
            giftRepository.save(gift);
        }
    }
    @Override
    public void addProduct(Shop shop, Product product){
        if(product!=null && !getProductList(shop).contains(product)){
            catalogRepository.save(product);
            getProductList(shop).add(product);
        }
    }
    @Override
    public void removeProduct(Shop shop, Product product)throws ProductNotFoundException {
        if(product!=null && getProductList(shop).contains(product)) {
            getProductList(shop).remove(product);
            catalogRepository.delete(product);
        }
        else
            throw new ProductNotFoundException();
    }
    @Override
    public void removeGift(Shop shop, Gift gift) throws GiftNotFoundException {
        if(gift!=null ){
            if(getGiftList(shop).contains(gift)) {
                giftRepository.delete(gift);
                getGiftList(shop).remove(gift);
            }else
                throw new GiftNotFoundException();
        }

    }
    @Override
    public Optional<Shop> findShopById(Long id){
        return shopRepository.findById(id);
    }

    @Override
    public Optional<ShopKeeperAccount> findShopKeeperAccountById(Long id){
        return shopKeeperAccountRepository.findById(id);
    }
    @Override
    public Optional<ShopKeeperAccount>findShopKeeperAccountByName(String name){
        for (ShopKeeperAccount  shopKeeperAccount : shopKeeperAccountRepository.findAll()) {
            if (shopKeeperAccount.getMail().equals(name)) {
                return Optional.of(shopKeeperAccount);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<ShopKeeperAccount> findShopKeeperAccountByMail(String mail)
    {
        for (ShopKeeperAccount  shopKeeperAccount : shopKeeperAccountRepository.findAll()) {
            if (shopKeeperAccount.getMail().equals(mail)) {
                return Optional.of(shopKeeperAccount);
            }
        }
        return Optional.empty();
    }
}
