package fr.univcotedazur.multiCredit.components;

import fr.univcotedazur.multiCredit.entities.*;
import fr.univcotedazur.multiCredit.exceptions.AlreadyExistingGiftException;
import fr.univcotedazur.multiCredit.exceptions.MissingInformationException;
import fr.univcotedazur.multiCredit.interfaces.MailSender;
import fr.univcotedazur.multiCredit.interfaces.ShopRegistration;
import fr.univcotedazur.multiCredit.repositories.GiftRepository;
import fr.univcotedazur.multiCredit.repositories.ShopRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.transaction.Transactional;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
public class ShopManagerTests {
    @Autowired
    private ShopManager shopManager;
    @Autowired
    Catalog catalog;
    @Autowired
    private ShopRegistration shopRegistration;
    @Autowired
    GiftRepository giftRepository;
    @Autowired
    ShopRepository shopRepository;
    Shop shop;
    Gift gift;
    Gift gift1;
    Gift gift2;
    @MockBean
    private MailSender mailSender;
    @BeforeEach
    public void setUp() throws MissingInformationException , AlreadyExistingGiftException {
        shopRepository.deleteAll();
        shop=shopRegistration.addShop("A", "1 rue de la paix");
        gift=new Gift(150,"ring", AccountStatus.VFP);
        gift1=new Gift(10,"cake", AccountStatus.REGULAR);
        gift.setShop(shop);
        gift1.setShop(shop);
        catalog.addGift(shop,gift);
        catalog.addGift(shop,gift1);
        gift2=new Gift(10,"cookie", AccountStatus.VFP);
        when(mailSender.sendMail(any(), any())).thenReturn(true);

    }

    @Test
     void testModifyAddress() {
        assertTrue(shopManager.findShopById(shop.getId()).isPresent());
        assertEquals("1 rue de la paix",shop.getAddress(),"1 rue de la paix");
        shopManager.modifyAddress(shop,"2 rue de la liberté");
        assertEquals(shop.getAddress(),"2 rue de la liberté");
    }
    @Test
     void testModifyAddress2(){
        assertTrue(shopManager.findShopById(shop.getId()).isPresent());
        assertEquals("1 rue de la paix",shop.getAddress(),"1 rue de la paix");
        shopManager.modifyAddress(shop,null);
        assertEquals(shop.getAddress(),"1 rue de la paix");
    }
    @Test
     void testModifyPlanning(){
        shopManager.modifyPlanning(shop,WeekDay.SATURDAY,LocalTime.of(10,00),LocalTime.of(15,00));
        shopManager.modifyPlanning(shop,WeekDay.MONDAY,LocalTime.of(9,00),LocalTime.of(19,00));
        assertTrue(shopManager.findShopById(shop.getId()).isPresent());
        Planning planning =shop.getPlanningList()
                .stream().filter(plan-> plan.getDayWorking()
                .equals(WeekDay.SATURDAY)).findFirst().get();
        assertEquals(LocalTime.of(10,00), planning.getOpeningHours());
        assertEquals(LocalTime.of(15,00), planning.getClosingHours());
        shopManager.modifyPlanning(shop,WeekDay.FRIDAY,LocalTime.of(11,00),LocalTime.of(16,00));
         planning =shop.getPlanningList()
                 .stream().filter(plan-> plan.getDayWorking()
                .equals(WeekDay.FRIDAY)).findFirst().get();
        assertEquals(LocalTime.of(11,00), planning.getOpeningHours());
        assertEquals(LocalTime.of(16,00), planning.getClosingHours());
    }
    @Test
     void testModifyPlanning2()  {
        shopManager.modifyPlanning(shop,WeekDay.SATURDAY,LocalTime.of(10,00),LocalTime.of(14,00));
        shopManager.modifyPlanning(shop,WeekDay.MONDAY,LocalTime.of(9,00),LocalTime.of(19,00));
        assertTrue(shopManager.findShopById(shop.getId()).isPresent());
        assertTrue(shop.getPlanningList()
                .stream().filter(plan-> plan.getDayWorking()
                .equals(WeekDay.WEDNESDAY)).findFirst().isEmpty());

        shopManager.modifyPlanning(shop,WeekDay.WEDNESDAY,LocalTime.of(11,00),LocalTime.of(19,00));
        Planning planning =shop.getPlanningList()
                .stream().filter(plan-> plan.getDayWorking()
                .equals(WeekDay.WEDNESDAY)).findFirst().get();
        assertEquals(LocalTime.of(11,00), planning.getOpeningHours());
        assertEquals(LocalTime.of(19,00), planning.getClosingHours());
    }
    @Test
     void testModifyPlanning3()  {
        shopManager.modifyPlanning(shop,WeekDay.SATURDAY,LocalTime.of(10,00),LocalTime.of(14,00));
        shopManager.modifyPlanning(shop,WeekDay.MONDAY,LocalTime.of(9,00),LocalTime.of(19,00));
        assertTrue(shopManager.findShopById(shop.getId()).isPresent());
        assertTrue(shopManager.findPlanningByDay(shop,WeekDay.MONDAY).isPresent());
        Planning planning =shopManager.findPlanningByDay(shop,WeekDay.MONDAY).get();
        assertEquals(LocalTime.of(9,00), planning.getOpeningHours());
        assertEquals(LocalTime.of(19,00), planning.getClosingHours());
        shopManager.modifyPlanning(shop,WeekDay.MONDAY,null,null);
        assertEquals(LocalTime.of(9,00), planning.getOpeningHours());
        assertEquals(LocalTime.of(19,00), planning.getClosingHours());
        shopManager.modifyPlanning(shop,WeekDay.MONDAY,LocalTime.of(19,00),LocalTime.of(9,00));
        assertEquals(LocalTime.of(9,00), planning.getOpeningHours());
        assertEquals(LocalTime.of(19,00), planning.getClosingHours());
    }
    @Test
    @Transactional
     void testModifyPlanning4()  {
        shopManager.modifyPlanning(shop,WeekDay.SATURDAY,LocalTime.of(10,00),LocalTime.of(14,00));
        shopManager.modifyPlanning(shop,WeekDay.MONDAY,LocalTime.of(9,00),LocalTime.of(19,00));
        assertTrue(shopManager.findShopById(shop.getId()).isPresent());
        assertTrue(shopManager.findPlanningByDay(shop,WeekDay.MONDAY).isPresent());
        Planning planning =shopManager.findPlanningByDay(shop,WeekDay.MONDAY).get();
        assertEquals(LocalTime.of(9,00),shopManager.findPlanningByDay(shop,WeekDay.MONDAY).get().getOpeningHours());
        assertEquals(LocalTime.of(19,00),shopManager.findPlanningByDay(shop,WeekDay.MONDAY).get().getClosingHours());
        shopManager.modifyPlanning(shop,WeekDay.MONDAY,null,LocalTime.of(20,00));
        assertEquals(LocalTime.of(9,00),shopManager.findPlanningByDay(shop,WeekDay.MONDAY).get().getOpeningHours());
        assertEquals(LocalTime.of(20,00),shopManager.findPlanningByDay(shop,WeekDay.MONDAY).get().getClosingHours());
        shopManager.modifyPlanning(shop,WeekDay.MONDAY,LocalTime.of(7,00),null);
        assertEquals(LocalTime.of(7,00),shopManager.findPlanningByDay(shop,WeekDay.MONDAY).get().getOpeningHours());
        assertEquals(LocalTime.of(20,00),shopManager.findPlanningByDay(shop,WeekDay.MONDAY).get().getClosingHours());
        shopManager.modifyPlanning(shop,WeekDay.MONDAY,LocalTime.of(21,00),null);
        assertEquals(LocalTime.of(7,00),shopManager.findPlanningByDay(shop,WeekDay.MONDAY).get().getOpeningHours());
        assertEquals(LocalTime.of(20,00),shopManager.findPlanningByDay(shop,WeekDay.MONDAY).get().getClosingHours());
        shopManager.modifyPlanning(shop,WeekDay.MONDAY,null,LocalTime.of(5,00));
        assertEquals(LocalTime.of(7,00),shopManager.findPlanningByDay(shop,WeekDay.MONDAY).get().getOpeningHours());
        assertEquals(LocalTime.of(20,00),shopManager.findPlanningByDay(shop,WeekDay.MONDAY).get().getClosingHours());
    }

}
