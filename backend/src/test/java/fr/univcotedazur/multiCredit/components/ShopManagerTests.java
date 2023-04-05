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
    public void testModifyAddress() {
        assertTrue(shopManager.findShopById(shop.getId()).isPresent());
        assertEquals("1 rue de la paix",shop.getAddress(),"1 rue de la paix");
        shopManager.modifyAddress(shop,"2 rue de la liberté");
        assertEquals(shop.getAddress(),"2 rue de la liberté");
    }
    @Test
    public void testModifyAddress2(){
        assertTrue(shopManager.findShopById(shop.getId()).isPresent());
        assertEquals("1 rue de la paix",shop.getAddress(),"1 rue de la paix");
        shopManager.modifyAddress(shop,null);
        assertEquals(shop.getAddress(),"1 rue de la paix");
    }
    @Test
    public void testModifyPlanning(){
        shopManager.modifyPlanning(shop,WeekDay.Saturday,LocalTime.of(10,00),LocalTime.of(15,00));
        shopManager.modifyPlanning(shop,WeekDay.Monday,LocalTime.of(9,00),LocalTime.of(19,00));
        assertTrue(shopManager.findShopById(shop.getId()).isPresent());
        Planning planning =shop.getPlanningList()
                .stream().filter(plan-> plan.getDayWorking()
                .equals(WeekDay.Saturday)).findFirst().get();
        assertEquals(LocalTime.of(10,00), planning.getOpeningHours());
        assertEquals(LocalTime.of(15,00), planning.getClosingHours());
        shopManager.modifyPlanning(shop,WeekDay.Friday,LocalTime.of(11,00),LocalTime.of(16,00));
         planning =shop.getPlanningList()
                 .stream().filter(plan-> plan.getDayWorking()
                .equals(WeekDay.Friday)).findFirst().get();
        assertEquals(LocalTime.of(11,00), planning.getOpeningHours());
        assertEquals(LocalTime.of(16,00), planning.getClosingHours());
    }
    @Test
    public void testModifyPlanning2()  {
        shopManager.modifyPlanning(shop,WeekDay.Saturday,LocalTime.of(10,00),LocalTime.of(14,00));
        shopManager.modifyPlanning(shop,WeekDay.Monday,LocalTime.of(9,00),LocalTime.of(19,00));
        assertTrue(shopManager.findShopById(shop.getId()).isPresent());
        assertTrue(shop.getPlanningList()
                .stream().filter(plan-> plan.getDayWorking()
                .equals(WeekDay.Wednesday)).findFirst().isEmpty());

        shopManager.modifyPlanning(shop,WeekDay.Wednesday,LocalTime.of(11,00),LocalTime.of(19,00));
        Planning planning =shop.getPlanningList()
                .stream().filter(plan-> plan.getDayWorking()
                .equals(WeekDay.Wednesday)).findFirst().get();
        assertEquals(LocalTime.of(11,00), planning.getOpeningHours());
        assertEquals(LocalTime.of(19,00), planning.getClosingHours());
    }
    @Test
    public void testModifyPlanning3()  {
        shopManager.modifyPlanning(shop,WeekDay.Saturday,LocalTime.of(10,00),LocalTime.of(14,00));
        shopManager.modifyPlanning(shop,WeekDay.Monday,LocalTime.of(9,00),LocalTime.of(19,00));
        assertTrue(shopManager.findShopById(shop.getId()).isPresent());
        assertTrue(shopManager.findPlanningByDay(shop,WeekDay.Monday).isPresent());
        Planning planning =shopManager.findPlanningByDay(shop,WeekDay.Monday).get();
        assertEquals(LocalTime.of(9,00), planning.getOpeningHours());
        assertEquals(LocalTime.of(19,00), planning.getClosingHours());
        shopManager.modifyPlanning(shop,WeekDay.Monday,null,null);
        assertEquals(LocalTime.of(9,00), planning.getOpeningHours());
        assertEquals(LocalTime.of(19,00), planning.getClosingHours());
        shopManager.modifyPlanning(shop,WeekDay.Monday,LocalTime.of(19,00),LocalTime.of(9,00));
        assertEquals(LocalTime.of(9,00), planning.getOpeningHours());
        assertEquals(LocalTime.of(19,00), planning.getClosingHours());
    }
    @Test
    @Transactional
    public void testModifyPlanning4()  {
        shopManager.modifyPlanning(shop,WeekDay.Saturday,LocalTime.of(10,00),LocalTime.of(14,00));
        shopManager.modifyPlanning(shop,WeekDay.Monday,LocalTime.of(9,00),LocalTime.of(19,00));
        assertTrue(shopManager.findShopById(shop.getId()).isPresent());
        assertTrue(shopManager.findPlanningByDay(shop,WeekDay.Monday).isPresent());
        Planning planning =shopManager.findPlanningByDay(shop,WeekDay.Monday).get();
        assertEquals(LocalTime.of(9,00),shopManager.findPlanningByDay(shop,WeekDay.Monday).get().getOpeningHours());
        assertEquals(LocalTime.of(19,00),shopManager.findPlanningByDay(shop,WeekDay.Monday).get().getClosingHours());
        shopManager.modifyPlanning(shop,WeekDay.Monday,null,LocalTime.of(20,00));
        assertEquals(LocalTime.of(9,00),shopManager.findPlanningByDay(shop,WeekDay.Monday).get().getOpeningHours());
        assertEquals(LocalTime.of(20,00),shopManager.findPlanningByDay(shop,WeekDay.Monday).get().getClosingHours());
        shopManager.modifyPlanning(shop,WeekDay.Monday,LocalTime.of(7,00),null);
        assertEquals(LocalTime.of(7,00),shopManager.findPlanningByDay(shop,WeekDay.Monday).get().getOpeningHours());
        assertEquals(LocalTime.of(20,00),shopManager.findPlanningByDay(shop,WeekDay.Monday).get().getClosingHours());
        shopManager.modifyPlanning(shop,WeekDay.Monday,LocalTime.of(21,00),null);
        assertEquals(LocalTime.of(7,00),shopManager.findPlanningByDay(shop,WeekDay.Monday).get().getOpeningHours());
        assertEquals(LocalTime.of(20,00),shopManager.findPlanningByDay(shop,WeekDay.Monday).get().getClosingHours());
        shopManager.modifyPlanning(shop,WeekDay.Monday,null,LocalTime.of(5,00));
        assertEquals(LocalTime.of(7,00),shopManager.findPlanningByDay(shop,WeekDay.Monday).get().getOpeningHours());
        assertEquals(LocalTime.of(20,00),shopManager.findPlanningByDay(shop,WeekDay.Monday).get().getClosingHours());
    }

}
