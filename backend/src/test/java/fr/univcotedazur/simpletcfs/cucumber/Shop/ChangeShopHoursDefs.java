package fr.univcotedazur.simpletcfs.cucumber.Shop;

import fr.univcotedazur.simpletcfs.components.Catalog;
import fr.univcotedazur.simpletcfs.components.ShopManager;
import fr.univcotedazur.simpletcfs.entities.*;
import fr.univcotedazur.simpletcfs.exceptions.AlreadyExistingGiftException;
import fr.univcotedazur.simpletcfs.exceptions.MissingInformationException;
import fr.univcotedazur.simpletcfs.interfaces.ShopRegistration;
import fr.univcotedazur.simpletcfs.repositories.GiftRepository;
import fr.univcotedazur.simpletcfs.repositories.ShopRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ChangeShopHoursDefs {

    @Autowired
    private ShopManager shopManager;
    @Autowired
    private ShopRegistration shopRegistration;
    @Autowired
    ShopRepository shopRepository;
    Shop shop;

    @Given("a shop who wants to change his planning")
    public void a_shop_who_wants_to_change_his_planning()throws MissingInformationException {
        shopRepository.deleteAll();
        shop=shopRegistration.addShop("A", "1 rue de la paix");
        shopManager.modifyPlanning(shop,WeekDay.Monday,LocalTime.of(10,00),LocalTime.of(18,00));
    }
    @When("The shop modifies its operating hours to be open monday between from {int} am and  {int} pm")
    public void the_shop_wants_to_change_the_shop_hours_for_monday(int open,int close) {
      shopManager.modifyPlanning(shop,WeekDay.Monday,LocalTime.of(open,00),LocalTime.of(close+12,00));
    }
    @Then("the shop can change the shop hours for Monday")
    public void the_shop_can_change_the_shop_hours_for_monday() {
        assertEquals(shopManager.findPlanningByDay(shop,WeekDay.Monday).get().getOpeningHours(), LocalTime.of(8, 00));
        assertEquals(shopManager.findPlanningByDay(shop,WeekDay.Monday).get().getClosingHours(), LocalTime.of(20, 00));
    }
    @When("The shop modifies its operating hours to be open monday between from {int} pm and  {int} am")
    public void the_shop_modifies_its_operating_hours_to_be_open_monday_between_from_3pm_and_am(int open,int close) {
      shopManager.modifyPlanning(shop,WeekDay.Monday,LocalTime.of(open+12,00),LocalTime.of(close,00));
    }
    @Then("the shop cannot change its operating hours to be open monday between from {int} pm and  {int} am")
    public void the_shop_cannot_change_the_shop_hours_for_monday(int open,int close) {
        assertNotEquals(shopManager.findPlanningByDay(shop,WeekDay.Monday).get().getOpeningHours(), LocalTime.of(open, 00));
        assertNotEquals(shopManager.findPlanningByDay(shop,WeekDay.Monday).get().getClosingHours(), LocalTime.of(close, 00));
    }
    @When("The shop include an additional workday")
    public void the_shop_include_an_additional_workday() {
        shopManager.modifyPlanning(shop,WeekDay.Tuesday,LocalTime.of(9,00),LocalTime.of(11,00));
    }
    @Then("the shop can  include an additional workday")
    public void the_shop_can_include_an_additional_workday() {
      assertEquals(shopManager.findPlanningByDay(shop,WeekDay.Tuesday).get().getOpeningHours(), LocalTime.of(9, 00));
      assertEquals(shopManager.findPlanningByDay(shop,WeekDay.Tuesday).get().getClosingHours(), LocalTime.of(11, 00));
    }




}
