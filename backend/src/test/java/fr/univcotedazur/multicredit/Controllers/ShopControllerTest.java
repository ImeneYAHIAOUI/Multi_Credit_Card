package fr.univcotedazur.multicredit.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univcotedazur.multicredit.controllers.AdminController;
import fr.univcotedazur.multicredit.controllers.ShopController;
import fr.univcotedazur.multicredit.controllers.dto.PlanningDTO;
import fr.univcotedazur.multicredit.controllers.dto.ShopDTO;
import fr.univcotedazur.multicredit.entities.Shop;
import fr.univcotedazur.multicredit.exceptions.AlreadyExistingShopException;
import fr.univcotedazur.multicredit.interfaces.MailSender;
import fr.univcotedazur.multicredit.interfaces.ShopFinder;
import fr.univcotedazur.multicredit.interfaces.ShopRegistration;
import fr.univcotedazur.multicredit.repositories.ShopRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
class ShopControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private ShopRegistration shopRegistration;
    @Autowired
    private ShopFinder  shopFinder;
    @MockBean
    private MailSender mailSender;

    @BeforeEach
    void setup() {
        shopRepository.deleteAll();
    }

    @Test
    void registerShopTest() throws Exception {
        ShopDTO shop = new ShopDTO();
        shop.setName("Sephora");
        shop.setAddress("adresse");
        mockMvc.perform(MockMvcRequestBuilders.post(AdminController.BASE_URI + "/shops/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(shop)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
        mockMvc.perform(MockMvcRequestBuilders.post(AdminController.BASE_URI + "/shops/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(shop)))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    void registerShopTest2() throws Exception {
        ShopDTO shop = new ShopDTO();
        shop.setName("Sephora");
        shop.setAddress(null);
        mockMvc.perform(MockMvcRequestBuilders.post(AdminController.BASE_URI + "/shops/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(shop)))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
        shop.setName(null);
        shop.setAddress("addresse");
        mockMvc.perform(MockMvcRequestBuilders.post(AdminController.BASE_URI + "/shops/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(shop)))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
        shop.setAddress(null);
        mockMvc.perform(MockMvcRequestBuilders.post(AdminController.BASE_URI + "/shops/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(shop)))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }

    @Test
    void getShopByIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ShopController.BASE_URI + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
        ShopDTO shop = new ShopDTO();
        shop.setName("Sephora");
        shop.setAddress("adresse");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(AdminController.BASE_URI + "/shops/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(shop)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON)).andReturn();

        String json = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        ShopDTO savedshop = mapper.readValue(json, ShopDTO.class);
        mockMvc.perform(MockMvcRequestBuilders.get(ShopController.BASE_URI + "/" + savedshop.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    void updateShopAddressTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(ShopController.BASE_URI + "/1/address")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString("address")))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
        ;
        ShopDTO shop = new ShopDTO();
        shop.setName("Sephora");
        shop.setAddress("adress");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(AdminController.BASE_URI + "/shops/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(shop)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON)).andReturn();

        String json = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        ShopDTO savedshop = mapper.readValue(json, ShopDTO.class);
        mockMvc.perform(MockMvcRequestBuilders.put(ShopController.BASE_URI + "/" + savedshop.getId() + "/address")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("new address"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void deleteShopByIdTest() throws Exception {
        shopRepository.deleteAll();
        ShopDTO shop = new ShopDTO();
        shop.setName("Sephora");
        shop.setAddress("adress");
        MvcResult result =mockMvc.perform(MockMvcRequestBuilders.post(AdminController.BASE_URI + "/shops/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(shop)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON)).andReturn();

        String json = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        Shop savedshop;
        try{
            savedshop = shopRegistration.addShop(shop.getName(),shop.getAddress());
        }
        catch (AlreadyExistingShopException e){
            savedshop = shopFinder.findShopByAddress(shop.getAddress()).get(0);
        }
        long id=savedshop.getId();
        mockMvc.perform(MockMvcRequestBuilders.delete(AdminController.BASE_URI + "/shops/"+id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.delete(AdminController.BASE_URI + "/shops/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void modifyPlanningTest() throws Exception {
        when(mailSender.sendMail(any(), any())).thenReturn(true);

        PlanningDTO planningDTO = new PlanningDTO();
        mockMvc.perform(MockMvcRequestBuilders.put(ShopController.BASE_URI + "/1/planning")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(planningDTO)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        ShopDTO shop = new ShopDTO();
        shop.setName("Sephora");
        shop.setAddress("adress");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(AdminController.BASE_URI + "/shops/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(shop)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON)).andReturn();
        String json = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        ShopDTO savedshop = mapper.readValue(json, ShopDTO.class);
        mockMvc.perform(MockMvcRequestBuilders.put(ShopController.BASE_URI + "/" + savedshop.getId() + "/planning")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(planningDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        planningDTO.setDayWorking("MONDAY");
        planningDTO.setClosingHours("08:00");
        planningDTO.setOpeningHours("10:00");
        mockMvc.perform(MockMvcRequestBuilders.put(ShopController.BASE_URI + "/" + savedshop.getId() + "/planning")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(planningDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        planningDTO.setClosingHours("18:00");
        planningDTO.setOpeningHours("10:00");
        mockMvc.perform(MockMvcRequestBuilders.put(ShopController.BASE_URI + "/" + savedshop.getId() + "/planning")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(planningDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


}
