package fr.univcotedazur.multicredit.components;

import fr.univcotedazur.multicredit.entities.Planning;
import fr.univcotedazur.multicredit.entities.Shop;
import fr.univcotedazur.multicredit.entities.ShopKeeperAccount;
import fr.univcotedazur.multicredit.entities.WeekDay;
import fr.univcotedazur.multicredit.interfaces.ShopFinder;
import fr.univcotedazur.multicredit.interfaces.ShopHandler;
import fr.univcotedazur.multicredit.interfaces.ShopkeeperFinder;
import fr.univcotedazur.multicredit.repositories.PlanningRepository;
import fr.univcotedazur.multicredit.repositories.ShopKeeperAccountRepository;
import fr.univcotedazur.multicredit.repositories.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Component
@Transactional

public class ShopManager implements ShopHandler, ShopFinder, ShopkeeperFinder {
    private final ShopRepository shopRepository;
    private final ShopKeeperAccountRepository shopKeeperAccountRepository;
    private final PlanningRepository planningRepository;

    @Autowired
    public ShopManager(ShopRepository shopRepository, ShopKeeperAccountRepository shopKeeperAccountRepository, PlanningRepository planningRepository) {
        this.shopRepository = shopRepository;
        this.shopKeeperAccountRepository = shopKeeperAccountRepository;
        this.planningRepository = planningRepository;
    }

    public Optional<Planning> findPlanningByDay(Shop shop, WeekDay day) {
        return shop.getPlanningList().stream().filter(plan -> plan.getDayWorking().equals(day)).findFirst();
    }

    @Override
    public void modifyPlanning(Shop shop, WeekDay day, LocalTime openingHours, LocalTime closingHours) {
        Planning planning = null;
        if (shop != null && day != null) {
            if (shop.getPlanningList().stream().filter(plan -> plan.getDayWorking().equals(day)).findFirst().isEmpty()) {
                if (openingHours != null && closingHours != null && openingHours.isBefore(closingHours)) {
                    planning = new Planning(day, openingHours, closingHours);
                    planning.setShop(shop);
                    shop.addPlanning(planning);
                }
            } else {
                // update existing planning
                planning = shop.getPlanningList().stream().filter(plan -> plan.getDayWorking().equals(day)).findFirst().orElse(null);
                if (planning == null) return;

                if (openingHours != null && closingHours != null) {
                    if (openingHours.isBefore(closingHours)) {
                        planning.setOpeningHours(openingHours);
                        planning.setClosingHours(closingHours);
                    }
                } else if (openingHours == null && closingHours != null) {
                    if (planning.getOpeningHours().isBefore(closingHours)) {
                        planning.setClosingHours(closingHours);
                    }
                } else if (openingHours != null && (openingHours.isBefore(planning.getClosingHours()))) {
                    planning.setOpeningHours(openingHours);
                }
            }
        }

        if (planning != null) planningRepository.save(planning);
    }

    @Override
    public void modifyName(Shop shop, String name) {
        if (name == null) return;
        shop.setName(name);
        shopRepository.save(shop);
    }

    @Override
    public void modifyAddress(Shop shop, String address) {
        if (address == null) return;
        shop.setAddress(address);
        shopRepository.save(shop);
    }

    @Override
    public Optional<ShopKeeperAccount> findShopkeeperAccountById(Long id) {
        return shopKeeperAccountRepository.findById(id);
    }

    @Override
    public Optional<Shop> findShopById(Long id) {
        return shopRepository.findById(id);
    }
    @Override

    public List<Shop> findShopByAddress(String address) {
        return shopRepository.findAll().stream().filter(shop -> shop.getAddress().equals(address)).toList();
    }

    @Override
    public ShopKeeperAccount findShopkeeperAccountByMail(String mail) {
        for (ShopKeeperAccount shopkeeperAccount : shopKeeperAccountRepository.findAll()) {
            if (shopkeeperAccount.getMail().equals(mail)) return shopkeeperAccount;
        }
        return null;
    }

    @Override
    public Optional<ShopKeeperAccount> findShopKeeperAccountByName(String name) {
        for (ShopKeeperAccount shopKeeperAccount : shopKeeperAccountRepository.findAll()) {
            if (shopKeeperAccount.getMail().equals(name)) return Optional.of(shopKeeperAccount);
        }
        return Optional.empty();
    }
}