package fr.univcotedazur.multiCredit.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univcotedazur.multiCredit.controllers.TransactionController;
import fr.univcotedazur.multiCredit.controllers.dto.PurchaseDTO;
import fr.univcotedazur.multiCredit.controllers.dto.UsePointDTO;
import fr.univcotedazur.multiCredit.entities.*;
import fr.univcotedazur.multiCredit.interfaces.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @SpyBean
    private MemberFinder memberFinder;
    @SpyBean
    private ShopFinder shopFinder;
    @SpyBean
    private CatalogFinder catalogFinder;
    @MockBean
    Bank bank;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

    MemberAccount memberAccount;
    Shop shop;
    Gift regularGift;
    Gift vfpGift;
    Product product1;
    Product product2;

    @BeforeEach
    void setUp()
    {

        memberAccount = new MemberAccount("john doe", "john.doe@mail.com", "password",LocalDate.parse("11/04/2001",formatter), 0,0);
        memberAccount.setId(1L);
        memberAccount.setStatus(AccountStatus.REGULAR);
        when(memberFinder.findById(1L)).thenReturn(Optional.of(memberAccount));
        shop = new Shop("Emily and the cool kids","200 route des lucioles");
        shop.setId(1L);
        when(shopFinder.findShopById(1L)).thenReturn(Optional.of(shop));
        regularGift = new Gift(100,"description", AccountStatus.REGULAR);
        regularGift.setGiftId(1L);
        vfpGift = new Gift(50,"description", AccountStatus.VFP);
        vfpGift.setGiftId(1L);
        vfpGift.setShop(shop);
        regularGift.setShop(shop);
        product1 = new Product("chocolate",5, 100, 0);
        product1.setId(5L);
        product2 = new Product("cookies",3, 80, 0);
        product2.setId(6L);
        product1.setShop(shop);
        product2.setShop(shop);
        when(catalogFinder.findGiftById(1L)).thenReturn(Optional.of(regularGift));
        when(catalogFinder.findGiftById(2L)).thenReturn(Optional.of(vfpGift));
        when(catalogFinder.findProductById(5L)).thenReturn(Optional.of(product1));
        when(catalogFinder.findProductById(6L)).thenReturn(Optional.of(product2));
        when(bank.pay(anyString(),anyDouble())).thenReturn(true);
    }

    @Test
     void getTransactionsTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(TransactionController.BASE_URI)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
     void UsePointsMemberNotFound() throws Exception {
        UsePointDTO usePointDTO = new UsePointDTO(0,LocalDate.now().format(formatter),2L,1L,0,1L);
        mockMvc.perform(MockMvcRequestBuilders.post(TransactionController.BASE_URI + "/UsePoints")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usePointDTO)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
     void UsePointsShopNotFound() throws Exception
    {
        UsePointDTO usePointDTO = new UsePointDTO(0,LocalDate.now().format(formatter),1L,2L,0,1L);
        mockMvc.perform(MockMvcRequestBuilders.post(TransactionController.BASE_URI + "/UsePoints")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usePointDTO)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
     void UsePointsGiftNotFound() throws Exception {
        UsePointDTO usePointDTO = new UsePointDTO(0, LocalDate.now().format(formatter), 1L, 1L, 0, 3L);
        mockMvc.perform(MockMvcRequestBuilders.post(TransactionController.BASE_URI + "/UsePoints")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usePointDTO)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
     void UsePointWithoutPurchases() throws Exception {
        UsePointDTO usePointDTO = new UsePointDTO(0,LocalDate.now().format(formatter),1L,1L,0,1L);
        memberAccount.setPoints(100);
        mockMvc.perform(MockMvcRequestBuilders.post(TransactionController.BASE_URI + "/UsePoints")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usePointDTO)))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));

    }



    @Test
     void UsePointsInsuffisantePoints() throws Exception {
        UsePointDTO usePointDTO = new UsePointDTO(0, LocalDate.now().format(formatter), 1L, 1L, 0, 1L);
        Purchase purchase = new Purchase(LocalDate.now(),memberAccount,new ArrayList<>());
        purchase.setShop(shop);
        memberAccount.getTransactions().add(purchase);
        mockMvc.perform(MockMvcRequestBuilders.post(TransactionController.BASE_URI + "/UsePoints")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usePointDTO)))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
     void UsePointsNotVFP() throws Exception {
        UsePointDTO usePointDTO = new UsePointDTO(0, LocalDate.now().format(formatter), 1L, 1L, 0, 2L);
        memberAccount.setPoints(100);
        Purchase purchase = new Purchase(LocalDate.now(),memberAccount,new ArrayList<>());
        purchase.setShop(shop);
        memberAccount.getTransactions().add(purchase);
        mockMvc.perform(MockMvcRequestBuilders.post(TransactionController.BASE_URI + "/UsePoints")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usePointDTO)))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
     void UsePointsRegularGift() throws Exception {
        UsePointDTO usePointDTO = new UsePointDTO(0, LocalDate.now().format(formatter), 1L, 1L, 0, 1L);
        memberAccount.setPoints(100);
        Purchase purchase = new Purchase(LocalDate.now(),memberAccount,new ArrayList<>());
        purchase.setShop(shop);
        memberAccount.getTransactions().add(purchase);
        mockMvc.perform(MockMvcRequestBuilders.post(TransactionController.BASE_URI + "/UsePoints")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usePointDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
     void UsePointsVFPGift() throws Exception {
        UsePointDTO usePointDTO = new UsePointDTO(0, LocalDate.now().format(formatter), 1L, 1L, 0, 2L);
        memberAccount.setPoints(100);
        Purchase purchase = new Purchase(LocalDate.now(),memberAccount,new ArrayList<>());
        purchase.setShop(shop);
        memberAccount.setStatus(AccountStatus.VFP);
        memberAccount.getTransactions().add(purchase);
        mockMvc.perform(MockMvcRequestBuilders.post(TransactionController.BASE_URI + "/UsePoints")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usePointDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
     void PurchaseWithCreditCardTest() throws Exception
    {
        long[] items = {product1.getId(),product2.getId()};
        int[] quantities = {2,4};
        PurchaseDTO purchaseDTO = new PurchaseDTO(0l,LocalDate.now().format(formatter),1,1,0,0,"123489698356789",items,quantities,"REGULAR");
        mockMvc.perform(MockMvcRequestBuilders.post(TransactionController.BASE_URI + "/purchase/creditCard")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(purchaseDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
     void PurchaseWithCash() throws Exception
    {
        long[] items = {product1.getId(),product2.getId()};
        int[] quantities = {2,4};
        PurchaseDTO purchaseDTO = new PurchaseDTO(0l,LocalDate.now().format(formatter),1,1,0,0,null,items,quantities,"REGULAR");
        mockMvc.perform(MockMvcRequestBuilders.post(TransactionController.BASE_URI + "/purchase/cash")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(purchaseDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
     void PurchaseWithMembershipCard() throws Exception {
        long[] items = {product1.getId(),product2.getId()};
        int[] quantities = {2,4};
        PurchaseDTO purchaseDTO = new PurchaseDTO(0l,LocalDate.now().format(formatter),1,1,0,0,null,items,quantities,"REGULAR");
        mockMvc.perform(MockMvcRequestBuilders.post(TransactionController.BASE_URI + "/purchase/membershipCard")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(purchaseDTO)))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));

        memberAccount.setBalance(100);
        mockMvc.perform(MockMvcRequestBuilders.post(TransactionController.BASE_URI + "/purchase/membershipCard")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(purchaseDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));


    }
}
