package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.entities.*;
import fr.univcotedazur.simpletcfs.exceptions.AlreadyExistingGiftException;
import fr.univcotedazur.simpletcfs.exceptions.AlreadyExistingProductException;
import fr.univcotedazur.simpletcfs.exceptions.GiftNotFoundException;
import fr.univcotedazur.simpletcfs.exceptions.ProductNotFoundException;
import fr.univcotedazur.simpletcfs.interfaces.*;
import fr.univcotedazur.simpletcfs.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Transactional

public class ShopManager implements ShopHandler, ShopFinder, ShopkeeperFinder{
    private ShopRepository shopRepository;
    private ShopKeeperAccountRepository shopKeeperAccountRepository;
    private  PlanningRepository planningRepository;
     private final  MailSender mailSender;
     private MemberRepository  memberRepository;

    @Autowired
    public ShopManager(ShopRepository shopRepository,
                       ShopKeeperAccountRepository shopKeeperAccountRepository,
                  PlanningRepository  planningRepository,
                       MailSender mailSender,MemberRepository memberRepository){
        this.shopRepository = shopRepository;
        this.shopKeeperAccountRepository = shopKeeperAccountRepository;
        this.planningRepository=planningRepository;
        this.mailSender=mailSender;
        this.memberRepository=memberRepository;
    }
    public Optional<Planning> findPlanningByDay(Shop shop, WeekDay day){
        return shop.getPlanningList().stream().filter(plan-> plan.getDayWorking().equals(day)).findFirst();
    }
    @Override
    public void modifyPlanning(Shop shop, WeekDay day, LocalTime OpeningHours, LocalTime ClosingHours){
        Planning planning=null;
        if(shop!= null && day!= null ){
            if( shop.getPlanningList().stream().filter(plan-> plan.getDayWorking().equals(day)).findFirst().isEmpty()){
                if(OpeningHours!=null && ClosingHours!=null && OpeningHours.isBefore(ClosingHours) ){
                    planning =new Planning(day,OpeningHours, ClosingHours);
                    planning.setShop(shop);
                    shop.addPlanning(planning);
                 }
            }
            else{
                // update existing planning
                 planning = shop.getPlanningList().stream().filter(plan-> plan.getDayWorking().equals(day)).findFirst().get();
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
            }

        }
        if(planning!=null){
            planningRepository.save(planning);
            //TODO mettre un vrai mail ^^
            mailSender.sendMail(memberRepository.findAll(), new Mail("me@me.com", "Planning modified", "The planning of the shop "+shop.getName()+" has been modified"));
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
    public  Optional<ShopKeeperAccount> findShopkeeperAccountById(Long id) {
        return  shopKeeperAccountRepository.findById(id);
    }
    @Override
    public Optional<Shop> findShopById(Long id){
        return shopRepository.findById(id);
    }
    public List<Shop> findShopByAddress(String address){
        return shopRepository.findAll().stream().filter(shop-> shop.getAddress().equals(address)).collect(Collectors.toList());
    }
    @Override
    public  ShopKeeperAccount findShopkeeperAccountByMail(String mail) {
        for (ShopKeeperAccount  shopkeeperAccount : shopKeeperAccountRepository.findAll()) {
            if (shopkeeperAccount.getMail().equals(mail)) {
                return shopkeeperAccount;
            }
        }
        return null;
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

}
