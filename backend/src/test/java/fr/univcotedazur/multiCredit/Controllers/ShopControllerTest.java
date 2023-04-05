package fr.univcotedazur.multiCredit.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univcotedazur.multiCredit.controllers.AdminController;
import fr.univcotedazur.multiCredit.controllers.ShopController;
import fr.univcotedazur.multiCredit.controllers.dto.PlanningDTO;
import fr.univcotedazur.multiCredit.controllers.dto.ShopDTO;
import fr.univcotedazur.multiCredit.interfaces.MailSender;
import fr.univcotedazur.multiCredit.repositories.ShopRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class ShopControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ShopRepository shopRepository;
    @MockBean
    private MailSender mailSender;
    @BeforeEach
    public void setup(){
        shopRepository.deleteAll();
    }
    @Test
    public void registerShopTest() throws Exception {
        ShopDTO shop=new ShopDTO();
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
    public void registerShopTest2() throws Exception {
        ShopDTO shop=new ShopDTO();
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
    public void getShopByIdTest()throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get(ShopController.BASE_URI + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
        ShopDTO shop=new ShopDTO();
        shop.setName("Sephora");
        shop.setAddress("adresse");
        MvcResult result =mockMvc.perform(MockMvcRequestBuilders.post(AdminController.BASE_URI + "/shops/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(shop)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON)).andReturn();

        String json = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        ShopDTO savedshop = mapper.readValue(json, ShopDTO.class);
        mockMvc.perform(MockMvcRequestBuilders.get(ShopController.BASE_URI + "/"+savedshop.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void updateShopAddressTest()throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.put(ShopController.BASE_URI + "/1/address")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString("address")))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                ;
        ShopDTO shop=new ShopDTO();
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
        ShopDTO savedshop = mapper.readValue(json, ShopDTO.class);
         mockMvc.perform(MockMvcRequestBuilders.put(ShopController.BASE_URI + "/"+savedshop.getId()+"/address")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("new address"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }
    @Test
    public void deleteShopByIdTest()throws Exception{
        ShopDTO shop=new ShopDTO();
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
        ShopDTO savedshop = mapper.readValue(json, ShopDTO.class);
        mockMvc.perform(MockMvcRequestBuilders.delete(AdminController.BASE_URI + "/shops/"+savedshop.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.delete(AdminController.BASE_URI + "/shops"+savedshop.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
        @Test
        public void modifyPlanningTest()throws Exception{
            when(mailSender.sendMail(any(), any())).thenReturn(true);

            PlanningDTO planningDTO=new PlanningDTO();


            mockMvc.perform(MockMvcRequestBuilders.put(ShopController.BASE_URI + "/1/planning")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(planningDTO)))
                    .andExpect(MockMvcResultMatchers.status().isNotFound());

            ShopDTO shop=new ShopDTO();
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
            ShopDTO savedshop = mapper.readValue(json, ShopDTO.class);
            mockMvc.perform(MockMvcRequestBuilders.put(ShopController.BASE_URI + "/"+savedshop.getId()+"/planning")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(planningDTO)))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest());

            planningDTO.setDayWorking("Monday");
            planningDTO.setClosingHours("08:00");
            planningDTO.setOpeningHours("10:00");
            mockMvc.perform(MockMvcRequestBuilders.put(ShopController.BASE_URI + "/"+savedshop.getId()+"/planning")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(planningDTO)))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest());
            planningDTO.setClosingHours("18:00");
            planningDTO.setOpeningHours("10:00");
            mockMvc.perform(MockMvcRequestBuilders.put(ShopController.BASE_URI + "/"+savedshop.getId()+"/planning")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(planningDTO)))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }


}
