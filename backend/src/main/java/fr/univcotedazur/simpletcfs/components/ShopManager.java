package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.entities.*;
import fr.univcotedazur.simpletcfs.exceptions.AlreadyExistingGiftException;
import fr.univcotedazur.simpletcfs.exceptions.AlreadyExistingProductException;
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

    private  PlanningRepository planningRepository;
    @Autowired
    public ShopManager(ShopRepository shopRepository,
                       ShopKeeperAccountRepository shopKeeperAccountRepository,
                  PlanningRepository  planningRepository){
        this.shopRepository = shopRepository;
        this.shopKeeperAccountRepository = shopKeeperAccountRepository;
        this.planningRepository=planningRepository;
    }
    public Optional<Planning> findPlanningByDay(Shop shop, WeekDay day){
        return shop.getPlanningList().stream().filter(plan-> plan.getDayWorking().equals(day)).findFirst();
    }

    @Override
    public void modifyPlanning(Shop shop, WeekDay day, LocalTime OpeningHours, LocalTime ClosingHours){
        if(shop!= null && day!= null ){
            if( shop.getPlanningList().stream().filter(plan-> plan.getDayWorking().equals(day)).findFirst().isEmpty()){
                if(OpeningHours!=null && ClosingHours!=null && OpeningHours.isBefore(ClosingHours) ){
                    System.out.println(ClosingHours);
                    System.out.println(OpeningHours);
                    Planning planning =new Planning(day,OpeningHours, ClosingHours);
                    planning.setShop(shop);
                    shop.addPlanning(planning);
                    planningRepository.save(planning);
                }
            }
            else{
                // update existing planning
                Planning planning = shop.getPlanningList().stream().filter(plan-> plan.getDayWorking().equals(day)).findFirst().get();
                if(OpeningHours!=null && ClosingHours!=null){
                    if( OpeningHours.isBefore(ClosingHours)){
                        planning.setOpeningHours(OpeningHours);
                        planning.setClosingHours(ClosingHours);
                    }
                }else if(OpeningHours==null && ClosingHours!=null){
                    if(planning.getOpeningHours().isBefore(ClosingHours)) {
                        planning.setClosingHours(ClosingHours);
                    }
                }else if( OpeningHours!=null  ){
                    if(OpeningHours.isBefore(planning.getClosingHours())) {
                        planning.setOpeningHours(OpeningHours);
                    }
                }
                planningRepository.save(planning);
            }
        }
    }
    @Override
    public void modifyName(Shop shop, String name){
        if(name!=null){
            shop.setName(name);
            shopRepository.save(shop);
        }
    }
    @Override
    public void modifyAddress(Shop shop, String address){
        if(address!=null){
            shop.setAddress(address);
            shopRepository.save(shop);
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
