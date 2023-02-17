package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.entities.Planning;
import fr.univcotedazur.simpletcfs.entities.Shop;
import fr.univcotedazur.simpletcfs.entities.WeekDay;
import fr.univcotedazur.simpletcfs.exceptions.MissingInformationException;
import fr.univcotedazur.simpletcfs.interfaces.ShopFinder;
import fr.univcotedazur.simpletcfs.interfaces.ShopRegistration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ShopManagerTest {
    @Autowired
    private ShopManager shopManager;
    @Autowired
    private ShopRegistration shopRegistration;
    Shop shop;
    @BeforeEach
    public void setUp() throws MissingInformationException  {
        Map<WeekDay,Planning> planning=new HashMap();
        planning.put(WeekDay.Friday,new Planning(LocalTime.of(10,00),LocalTime.of(15,00)));
        planning.put(WeekDay.Saturday,new Planning(LocalTime.of(10,00),LocalTime.of(14,00)));
        planning.put(WeekDay.Monday,new Planning(LocalTime.of(9,00),LocalTime.of(19,00)));
         shop=shopRegistration.addShop("A", "1 rue de la paix", planning, new ArrayList<>());
    }
    @Test
    public void testModifyAddress() {

        assertTrue(shopManager.findShopById(shop.getId()).isPresent());
        assertEquals("1 rue de la paix",shop.getAddress(),"1 rue de la paix");
        shopManager.modifyAdress(shop,"2 rue de la liberté");
        assertEquals(shop.getAddress(),"2 rue de la liberté");
    }
    @Test
    public void testModifyAddress2(){

        assertTrue(shopManager.findShopById(shop.getId()).isPresent());
        assertEquals("1 rue de la paix",shop.getAddress(),"1 rue de la paix");
        shopManager.modifyAdress(shop,null);
        assertEquals(shop.getAddress(),"1 rue de la paix");
    }
    @Test
    public void testModifyPlanning(){

        assertTrue(shopManager.findShopById(shop.getId()).isPresent());
        assertEquals(LocalTime.of(10,00),shop.getPlanning().get(WeekDay.Friday).getOpeningHours());
        assertEquals(LocalTime.of(15,00),shop.getPlanning().get(WeekDay.Friday).getClosingHours());
        shopManager.modifyPlanning(shop,WeekDay.Friday,LocalTime.of(11,00),LocalTime.of(16,00));
        assertEquals(LocalTime.of(11,00),shop.getPlanning().get(WeekDay.Friday).getOpeningHours());
        assertEquals(LocalTime.of(16,00),shop.getPlanning().get(WeekDay.Friday).getClosingHours());
    }
    @Test
    public void testModifyPlanning2()  {

        assertTrue(shopManager.findShopById(shop.getId()).isPresent());
        assertTrue(shop.getPlanning().get(WeekDay.Wednesday)==null);
        shopManager.modifyPlanning(shop,WeekDay.Wednesday,LocalTime.of(11,00),LocalTime.of(19,00));
        assertEquals(LocalTime.of(11,00),shop.getPlanning().get(WeekDay.Wednesday).getOpeningHours());
        assertEquals(LocalTime.of(19,00),shop.getPlanning().get(WeekDay.Wednesday).getClosingHours());
    }
    @Test
    public void testModifyPlanning3()  {

        assertTrue(shopManager.findShopById(shop.getId()).isPresent());
        shopManager.modifyPlanning(shop,WeekDay.Monday,null,null);
        assertEquals(LocalTime.of(9,00),shop.getPlanning().get(WeekDay.Monday).getOpeningHours());
        assertEquals(LocalTime.of(19,00),shop.getPlanning().get(WeekDay.Monday).getClosingHours());
        shopManager.modifyPlanning(shop,WeekDay.Monday,LocalTime.of(19,00),LocalTime.of(9,00));
        assertEquals(LocalTime.of(9,00),shop.getPlanning().get(WeekDay.Monday).getOpeningHours());
        assertEquals(LocalTime.of(19,00),shop.getPlanning().get(WeekDay.Monday).getClosingHours());
    }
}
