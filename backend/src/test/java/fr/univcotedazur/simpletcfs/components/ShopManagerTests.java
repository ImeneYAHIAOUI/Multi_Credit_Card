package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.entities.Planning;
import fr.univcotedazur.simpletcfs.entities.Shop;
import fr.univcotedazur.simpletcfs.entities.WeekDay;
import fr.univcotedazur.simpletcfs.exceptions.MissingInformationException;
import fr.univcotedazur.simpletcfs.interfaces.ShopRegistration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

public class ShopManagerTests {
    @Autowired
    private ShopManager shopManager;
    @Autowired
    private ShopRegistration shopRegistration;
    Shop shop;
    @BeforeEach
    public void setUp() throws MissingInformationException  {
        shop=shopRegistration.addShop("A", "1 rue de la paix", new ArrayList<>(), new ArrayList<>(),new ArrayList<>());


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
        Planning planning =shop.getPlanningList().stream().filter(plan-> plan.getDayWorking()
                .equals(WeekDay.Saturday)).findFirst().get();
        assertEquals(LocalTime.of(10,00), planning.getOpeningHours());
        assertEquals(LocalTime.of(15,00), planning.getClosingHours());
        shopManager.modifyPlanning(shop,WeekDay.Friday,LocalTime.of(11,00),LocalTime.of(16,00));
         planning =shop.getPlanningList().stream().filter(plan-> plan.getDayWorking()
                .equals(WeekDay.Friday)).findFirst().get();
        assertEquals(LocalTime.of(11,00), planning.getOpeningHours());
        assertEquals(LocalTime.of(16,00), planning.getClosingHours());
    }
    @Test
    public void testModifyPlanning2()  {
        shopManager.modifyPlanning(shop,WeekDay.Saturday,LocalTime.of(10,00),LocalTime.of(14,00));
        shopManager.modifyPlanning(shop,WeekDay.Monday,LocalTime.of(9,00),LocalTime.of(19,00));
        assertTrue(shopManager.findShopById(shop.getId()).isPresent());
        assertTrue(shop.getPlanningList().stream().filter(plan-> plan.getDayWorking()
                .equals(WeekDay.Wednesday)).findFirst().isEmpty());

        shopManager.modifyPlanning(shop,WeekDay.Wednesday,LocalTime.of(11,00),LocalTime.of(19,00));
        Planning planning =shop.getPlanningList().stream().filter(plan-> plan.getDayWorking()
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
