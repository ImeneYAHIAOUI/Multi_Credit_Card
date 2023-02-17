package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.entities.Planning;
import fr.univcotedazur.simpletcfs.entities.Shop;
import fr.univcotedazur.simpletcfs.entities.ShopKeeperAccount;
import fr.univcotedazur.simpletcfs.entities.WeekDay;
import fr.univcotedazur.simpletcfs.exceptions.MissingInformationException;
import fr.univcotedazur.simpletcfs.interfaces.ShopFinder;
import fr.univcotedazur.simpletcfs.interfaces.ShopHandler;
import fr.univcotedazur.simpletcfs.interfaces.ShopkeeperFinder;
import fr.univcotedazur.simpletcfs.repositories.ShopKeeperAccountRepository;
import fr.univcotedazur.simpletcfs.repositories.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Component
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
    public Optional<Shop> findShopById(UUID id){
        return shopRepository.findById(id);
    }


    @Override
    public Optional<ShopKeeperAccount> findShopKeeperAccountById(UUID id){
        return shopKeeperAccountRepository.findById(id);
    }
    @Override
    public Optional<ShopKeeperAccount>findShopKeeperAccountByName(String name){
        return shopKeeperAccountRepository.findByName(name);
    }
}
