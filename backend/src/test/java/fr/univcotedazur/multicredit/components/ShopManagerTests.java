package fr.univcotedazur.multicredit.components;

import fr.univcotedazur.multicredit.entities.*;
import fr.univcotedazur.multicredit.exceptions.AlreadyExistingGiftException;
import fr.univcotedazur.multicredit.exceptions.AlreadyExistingShopException;
import fr.univcotedazur.multicredit.exceptions.MissingInformationException;
import fr.univcotedazur.multicredit.exceptions.ShopNotFoundException;
import fr.univcotedazur.multicredit.interfaces.MailSender;
import fr.univcotedazur.multicredit.interfaces.ShopRegistration;
import fr.univcotedazur.multicredit.repositories.GiftRepository;
import fr.univcotedazur.multicredit.repositories.ShopRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.transaction.Transactional;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
class ShopManagerTests {
    @Autowired
    Catalog catalog;
    @Autowired
    GiftRepository giftRepository;
    @Autowired
    ShopRepository shopRepository;
    Shop shop;
    Gift gift;
    Gift gift1;
    Gift gift2;
    @Autowired
    private ShopManager shopManager;
    @Autowired
    private ShopRegistration shopRegistration;
    @MockBean
    private MailSender mailSender;

    @BeforeEach
    void setUp() throws MissingInformationException, AlreadyExistingGiftException, ShopNotFoundException, AlreadyExistingShopException {
        shopRepository.deleteAll();
        shop = shopRegistration.addShop("A", "1 rue de la paix");
        gift = new Gift(150, "ring", AccountStatus.VFP);
        gift1 = new Gift(10, "cake", AccountStatus.REGULAR);
        gift.setShop(shop);
        gift1.setShop(shop);
        catalog.addGift(shop.getId(), gift);
        catalog.addGift(shop.getId(), gift1);
        gift2 = new Gift(10, "cookie", AccountStatus.VFP);
        when(mailSender.sendMail(any(), any())).thenReturn(true);
    }

    @Test
    void testModifyAddress() {
        assertTrue(shopManager.findShopById(shop.getId()).isPresent());
        assertEquals("1 rue de la paix", shop.getAddress(), "1 rue de la paix");
        shopManager.modifyAddress(shop, "2 rue de la liberté");
        assertEquals("2 rue de la liberté", shop.getAddress());
    }

    @Test
    void testModifyAddress2() {
        assertTrue(shopManager.findShopById(shop.getId()).isPresent());
        assertEquals("1 rue de la paix", shop.getAddress(), "1 rue de la paix");
        shopManager.modifyAddress(shop, null);
        assertEquals("1 rue de la paix", shop.getAddress());
    }

    @Test
    void testModifyPlanning() {
        shopManager.modifyPlanning(shop, WeekDay.SATURDAY, LocalTime.of(10, 0), LocalTime.of(15, 0));
        shopManager.modifyPlanning(shop, WeekDay.MONDAY, LocalTime.of(9, 0), LocalTime.of(19, 0));
        assertTrue(shopManager.findShopById(shop.getId()).isPresent());
        Planning planning = shop.getPlanningList()
                .stream().filter(plan -> plan.getDayWorking()
                        .equals(WeekDay.SATURDAY)).findFirst().orElse(null);
        if (planning == null) throw new AssertionError();

        assertEquals(LocalTime.of(10, 0), planning.getOpeningHours());
        assertEquals(LocalTime.of(15, 0), planning.getClosingHours());
        shopManager.modifyPlanning(shop, WeekDay.FRIDAY, LocalTime.of(11, 0), LocalTime.of(16, 0));
        planning = shop.getPlanningList()
                .stream().filter(plan -> plan.getDayWorking()
                        .equals(WeekDay.FRIDAY)).findFirst().orElse(null);
        if (planning == null) throw new AssertionError();

        assertEquals(LocalTime.of(11, 0), planning.getOpeningHours());
        assertEquals(LocalTime.of(16, 0), planning.getClosingHours());
    }

    @Test
    void testModifyPlanning2() {
        shopManager.modifyPlanning(shop, WeekDay.SATURDAY, LocalTime.of(10, 0), LocalTime.of(14, 0));
        shopManager.modifyPlanning(shop, WeekDay.MONDAY, LocalTime.of(9, 0), LocalTime.of(19, 0));
        assertTrue(shopManager.findShopById(shop.getId()).isPresent());
        assertTrue(shop.getPlanningList()
                .stream().filter(plan -> plan.getDayWorking()
                        .equals(WeekDay.WEDNESDAY)).findFirst().isEmpty());

        shopManager.modifyPlanning(shop, WeekDay.WEDNESDAY, LocalTime.of(11, 0), LocalTime.of(19, 0));
        Planning planning = shop.getPlanningList()
                .stream().filter(plan -> plan.getDayWorking()
                        .equals(WeekDay.WEDNESDAY)).findFirst().orElse(null);
        if (planning == null) throw new AssertionError();

        assertEquals(LocalTime.of(11, 0), planning.getOpeningHours());
        assertEquals(LocalTime.of(19, 0), planning.getClosingHours());
    }

    @Test
    void testModifyPlanning3() {
        shopManager.modifyPlanning(shop, WeekDay.SATURDAY, LocalTime.of(10, 0), LocalTime.of(14, 0));
        shopManager.modifyPlanning(shop, WeekDay.MONDAY, LocalTime.of(9, 0), LocalTime.of(19, 0));
        assertTrue(shopManager.findShopById(shop.getId()).isPresent());
        assertTrue(shopManager.findPlanningByDay(shop, WeekDay.MONDAY).isPresent());
        Planning planning = shopManager.findPlanningByDay(shop, WeekDay.MONDAY).get();
        assertEquals(LocalTime.of(9, 0), planning.getOpeningHours());
        assertEquals(LocalTime.of(19, 0), planning.getClosingHours());
        shopManager.modifyPlanning(shop, WeekDay.MONDAY, null, null);
        assertEquals(LocalTime.of(9, 0), planning.getOpeningHours());
        assertEquals(LocalTime.of(19, 0), planning.getClosingHours());
        shopManager.modifyPlanning(shop, WeekDay.MONDAY, LocalTime.of(19, 0), LocalTime.of(9, 0));
        assertEquals(LocalTime.of(9, 0), planning.getOpeningHours());
        assertEquals(LocalTime.of(19, 0), planning.getClosingHours());
    }

    @Test
    @Transactional
    void testModifyPlanning4() {
        shopManager.modifyPlanning(shop, WeekDay.SATURDAY, LocalTime.of(10, 0), LocalTime.of(14, 0));
        shopManager.modifyPlanning(shop, WeekDay.MONDAY, LocalTime.of(9, 0), LocalTime.of(19, 0));
        assertTrue(shopManager.findShopById(shop.getId()).isPresent());
        assertTrue(shopManager.findPlanningByDay(shop, WeekDay.MONDAY).isPresent());
        Planning planning = shopManager.findPlanningByDay(shop, WeekDay.MONDAY).get();
        assertEquals(LocalTime.of(9, 0), shopManager.findPlanningByDay(shop, WeekDay.MONDAY).get().getOpeningHours());
        assertEquals(LocalTime.of(19, 0), shopManager.findPlanningByDay(shop, WeekDay.MONDAY).get().getClosingHours());
        shopManager.modifyPlanning(shop, WeekDay.MONDAY, null, LocalTime.of(20, 0));
        assertEquals(LocalTime.of(9, 0), shopManager.findPlanningByDay(shop, WeekDay.MONDAY).get().getOpeningHours());
        assertEquals(LocalTime.of(20, 0), shopManager.findPlanningByDay(shop, WeekDay.MONDAY).get().getClosingHours());
        shopManager.modifyPlanning(shop, WeekDay.MONDAY, LocalTime.of(7, 0), null);
        assertEquals(LocalTime.of(7, 0), shopManager.findPlanningByDay(shop, WeekDay.MONDAY).get().getOpeningHours());
        assertEquals(LocalTime.of(20, 0), shopManager.findPlanningByDay(shop, WeekDay.MONDAY).get().getClosingHours());
        shopManager.modifyPlanning(shop, WeekDay.MONDAY, LocalTime.of(21, 0), null);
        assertEquals(LocalTime.of(7, 0), shopManager.findPlanningByDay(shop, WeekDay.MONDAY).get().getOpeningHours());
        assertEquals(LocalTime.of(20, 0), shopManager.findPlanningByDay(shop, WeekDay.MONDAY).get().getClosingHours());
        shopManager.modifyPlanning(shop, WeekDay.MONDAY, null, LocalTime.of(5, 0));
        assertEquals(LocalTime.of(7, 0), shopManager.findPlanningByDay(shop, WeekDay.MONDAY).get().getOpeningHours());
        assertEquals(LocalTime.of(20, 0), shopManager.findPlanningByDay(shop, WeekDay.MONDAY).get().getClosingHours());
    }
}
