package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.entities.*;
import fr.univcotedazur.simpletcfs.exceptions.GiftNotFoundException;
import fr.univcotedazur.simpletcfs.interfaces.ShopFinder;
import fr.univcotedazur.simpletcfs.interfaces.ShopHandler;
import fr.univcotedazur.simpletcfs.interfaces.ShopkeeperFinder;
import fr.univcotedazur.simpletcfs.repositories.ShopKeeperAccountRepository;
import fr.univcotedazur.simpletcfs.repositories.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

@Component
@Transactional
public class ShopManager implements ShopHandler, ShopFinder, ShopkeeperFinder{

    private ShopRepository shopRepository;
    private ShopKeeperAccountRepository shopKeeperAccountRepository;
    @Autowired
    public ShopManager(ShopRepository shopRepository, ShopKeeperAccountRepository shopKeeperAccountRepository) {
        this.shopRepository = shopRepository;
        this.shopKeeperAccountRepository = shopKeeperAccountRepository;
    }
    @Override
    public void modifyPlanning(Shop shop, WeekDay day, LocalTime OpeningHours, LocalTime ClosingHours){
        if(OpeningHours!=null && ClosingHours!=null && OpeningHours.isBefore(ClosingHours) ){
            if(shop.getPlanning().get(day)==null){
                shop.getPlanning().put(day,new Planning(OpeningHours, ClosingHours));
            }
            else{
                shop.getPlanning().get(day).setOpeningHours(OpeningHours);
                shop.getPlanning().get(day).setClosingHours(ClosingHours);
            }
        }

    }
    @Override
    public void modifyAdress(Shop shop, String adress){
        if(adress!=null)
            shop.setAddress(adress);
    }
    @Override
    public void addGift(Shop shop, Gift gift){
        if(gift!=null && !shop.getGiftList().contains(gift))
            shop.getGiftList().add(gift);
    }
    @Override
    public void removeGift(Shop shop, Gift gift) throws GiftNotFoundException {
        if(gift!=null ){
            if(shop.getGiftList().contains(gift))
                shop.getGiftList().remove(gift);
            else
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
