package fr.univcotedazur.simpletcfs.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univcotedazur.simpletcfs.controllers.CatalogController;
import fr.univcotedazur.simpletcfs.controllers.ShopController;
import fr.univcotedazur.simpletcfs.controllers.dto.GiftDTO;
import fr.univcotedazur.simpletcfs.controllers.dto.ProductDTO;
import fr.univcotedazur.simpletcfs.controllers.dto.ShopDTO;
import fr.univcotedazur.simpletcfs.entities.Product;
import fr.univcotedazur.simpletcfs.repositories.CatalogRepository;
import fr.univcotedazur.simpletcfs.repositories.GiftRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class CatalogControllerTest {
    @Autowired
    private MockMvc mockMvc;
    ShopDTO savedShop;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    CatalogRepository catalogRepository;
    @Autowired
    GiftRepository giftRepository;
    @Test
    public void addProductTest() throws Exception{
        ShopDTO shop=new ShopDTO();
        shop.setName("Sephora");
        shop.setAddress("adresse");
        MvcResult result =mockMvc.perform(MockMvcRequestBuilders.post(ShopController.BASE_URI + "/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(shop)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON)).andReturn();
        String json = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        savedShop = mapper.readValue(json, ShopDTO.class);
        ProductDTO productDTO=new ProductDTO("cookie",10,2.3,0.0);
         result =mockMvc.perform(MockMvcRequestBuilders.post(CatalogController.BASE_URI + "/add/"+savedShop.getId()+"/Products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON)).andReturn();
        json = result.getResponse().getContentAsString();
        productDTO= mapper.readValue(json, ProductDTO.class);
        assert(productDTO.getDiscountPercentage()==0.0);
        assert(productDTO.getPoints()==10);
        assert(Objects.equals(productDTO.getName(), "cookie"));
        assert(productDTO.getPrice()==2.3);
        assert (productDTO.getId()!=null);
        mockMvc.perform(MockMvcRequestBuilders.delete(ShopController.BASE_URI + "/"+savedShop.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
    @Test
    public void addProductTest1() throws Exception{
        ProductDTO productDTO=new ProductDTO("cookie",10,2.3,0.0);
         mockMvc.perform(MockMvcRequestBuilders.post(CatalogController.BASE_URI + "/add/1"+"/Products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
        ShopDTO shop=new ShopDTO();
        shop.setName("Sephora");
        shop.setAddress("adresse");
        MvcResult result =mockMvc.perform(MockMvcRequestBuilders.post(ShopController.BASE_URI + "/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(shop)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON)).andReturn();
        String json = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        savedShop = mapper.readValue(json, ShopDTO.class);
        result =mockMvc.perform(MockMvcRequestBuilders.post(CatalogController.BASE_URI + "/add/"+savedShop.getId()+"/Products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON)).andReturn();
        json = result.getResponse().getContentAsString();
        productDTO= mapper.readValue(json, ProductDTO.class);
        assert(productDTO.getDiscountPercentage()==0.0);
        assert(productDTO.getPoints()==10);
        assert(Objects.equals(productDTO.getName(), "cookie"));
        assert(productDTO.getPrice()==2.3);
        assert (productDTO.getId()!=null);
        mockMvc.perform(MockMvcRequestBuilders.post(CatalogController.BASE_URI + "/add/"+savedShop.getId()+"/Products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(MockMvcResultMatchers.status().isConflict());
        mockMvc.perform(MockMvcRequestBuilders.delete(ShopController.BASE_URI + "/"+savedShop.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void addPGiftTest() throws Exception{
        ShopDTO shop=new ShopDTO();
        shop.setName("Sephora");
        shop.setAddress("adresse");
        MvcResult result =mockMvc.perform(MockMvcRequestBuilders.post(ShopController.BASE_URI + "/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(shop)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON)).andReturn();
        String json = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        savedShop = mapper.readValue(json, ShopDTO.class);
        assert(savedShop.getId()!=null);
        GiftDTO gift=new GiftDTO(10,"cookie","vfp");
        result =mockMvc.perform(MockMvcRequestBuilders.post(CatalogController.BASE_URI + "/add/"+savedShop.getId()+"/Gifts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gift)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON)).andReturn();
        json = result.getResponse().getContentAsString();
        gift= mapper.readValue(json, GiftDTO.class);
        assert(gift.getPointsNeeded()==10);
        assert(gift.getStatus().equals("VFP"));
        assert (gift.getShop().getId()==savedShop.getId());
        assert(Objects.equals(gift.getDescription(), "cookie"));
        assert (gift.getGiftId()!=null);
        mockMvc.perform(MockMvcRequestBuilders.delete(ShopController.BASE_URI + "/"+savedShop.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void addGiftTest1() throws Exception{
        ShopDTO shop=new ShopDTO();
        shop.setName("Sephora");
        shop.setAddress("adresse");
        MvcResult result =mockMvc.perform(MockMvcRequestBuilders.post(ShopController.BASE_URI + "/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(shop)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON)).andReturn();
        String json = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        savedShop = mapper.readValue(json, ShopDTO.class);
        GiftDTO gift=new GiftDTO(10,"cookie","vfp");
        mockMvc.perform(MockMvcRequestBuilders.post(CatalogController.BASE_URI + "/add/10000000"+"/Gifts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gift)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
         result =mockMvc.perform(MockMvcRequestBuilders.post(CatalogController.BASE_URI + "/add/"+savedShop.getId()+"/Gifts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gift)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON)).andReturn();
         json = result.getResponse().getContentAsString();
        gift= mapper.readValue(json, GiftDTO.class);
        assert(gift.getPointsNeeded()==10);
        assert(gift.getStatus().equals("VFP"));
        assert(Objects.equals(gift.getDescription(), "cookie"));
        assert (gift.getGiftId()!=null);
        mockMvc.perform(MockMvcRequestBuilders.post(CatalogController.BASE_URI + "/add/"+savedShop.getId()+"/Gifts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gift)))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }
    @Test
    public void getGiftByIdTest()throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get(CatalogController.BASE_URI + "/get/Gifts/"+1052125555555L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    @Test
    public void getGiftByIdTest1()throws Exception{
        ShopDTO shop=new ShopDTO();
        shop.setName("Sephora");
        shop.setAddress("adresse");
        MvcResult result =mockMvc.perform(MockMvcRequestBuilders.post(ShopController.BASE_URI + "/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(shop)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON)).andReturn();
        String json = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        savedShop = mapper.readValue(json, ShopDTO.class);
        GiftDTO gift=new GiftDTO(10,"cookie","vfp");
        mockMvc.perform(MockMvcRequestBuilders.post(CatalogController.BASE_URI + "/add/10000000"+"/Gifts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gift)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
        result =mockMvc.perform(MockMvcRequestBuilders.post(CatalogController.BASE_URI + "/add/"+savedShop.getId()+"/Gifts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gift)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON)).andReturn();
        json = result.getResponse().getContentAsString();
        gift= mapper.readValue(json, GiftDTO.class);
        assert(gift.getPointsNeeded()==10);
        assert(gift.getStatus().equals("VFP"));
        assert(Objects.equals(gift.getDescription(), "cookie"));
        assert (gift.getGiftId()!=null);
        result=mockMvc.perform(MockMvcRequestBuilders.get(CatalogController.BASE_URI + "/get/Gifts/"+gift.getGiftId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        json = result.getResponse().getContentAsString();
        assertEquals(json,objectMapper.writeValueAsString(gift));
    }
    @Test
    public void getProductByIdTest()throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get(CatalogController.BASE_URI + "/get/Products/"+1052125555555L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    @Test
    public void getProductByIdTest1()throws Exception{
        ShopDTO shop=new ShopDTO();
        shop.setName("Sephora");
        shop.setAddress("adresse");
        MvcResult result =mockMvc.perform(MockMvcRequestBuilders.post(ShopController.BASE_URI + "/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(shop)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON)).andReturn();
        String json = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        savedShop = mapper.readValue(json, ShopDTO.class);
        ProductDTO productDTO=new ProductDTO("cookie",10,2.3,0.0);
        result =mockMvc.perform(MockMvcRequestBuilders.post(CatalogController.BASE_URI + "/add/"+savedShop.getId()+"/Products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON)).andReturn();
        json = result.getResponse().getContentAsString();
        productDTO= mapper.readValue(json, ProductDTO.class);
        assert (productDTO.getId()!=null);
        assert (productDTO.getPoints()==10);
        assert (productDTO.getPrice()==2.3);
        assert (productDTO.getDiscountPercentage()==0.0);
        assert (productDTO.getName().equals("cookie"));
        result=mockMvc.perform(MockMvcRequestBuilders.get(CatalogController.BASE_URI + "/get/Products/"+productDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        json = result.getResponse().getContentAsString();
        assertEquals(json,objectMapper.writeValueAsString(productDTO));
        mockMvc.perform(MockMvcRequestBuilders.delete(ShopController.BASE_URI + "/"+savedShop.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
    @Test
    public void deleteProductByIdTest()throws Exception{
        ShopDTO shop=new ShopDTO();
        shop.setName("Sephora");
        shop.setAddress("adresse");
        MvcResult result =mockMvc.perform(MockMvcRequestBuilders.post(ShopController.BASE_URI + "/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(shop)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON)).andReturn();
        String json = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        savedShop = mapper.readValue(json, ShopDTO.class);
        ProductDTO productDTO=new ProductDTO("cookie",10,2.3,0.0);
        result =mockMvc.perform(MockMvcRequestBuilders.post(CatalogController.BASE_URI + "/add/"+savedShop.getId()+"/Products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON)).andReturn();
        json = result.getResponse().getContentAsString();
        productDTO= mapper.readValue(json, ProductDTO.class);
        assert (productDTO.getId()!=null);
        assert (productDTO.getPoints()==10);
        assert (productDTO.getPrice()==2.3);
        assert (productDTO.getDiscountPercentage()==0.0);
        assert (productDTO.getName().equals("cookie"));
        mockMvc.perform(MockMvcRequestBuilders.delete(CatalogController.BASE_URI + "/Products/"+productDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.delete(CatalogController.BASE_URI + "/Products/"+productDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    @Test
    public void deleteGiftByIdTest()throws Exception{
        ShopDTO shop=new ShopDTO();
        shop.setName("Sephora");
        shop.setAddress("adresse");
        MvcResult result =mockMvc.perform(MockMvcRequestBuilders.post(ShopController.BASE_URI + "/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(shop)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON)).andReturn();
        String json = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        savedShop = mapper.readValue(json, ShopDTO.class);
        GiftDTO gift=new GiftDTO(10,"cookie","vfp");
        mockMvc.perform(MockMvcRequestBuilders.post(CatalogController.BASE_URI + "/add/10000000"+"/Gifts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gift)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
        result =mockMvc.perform(MockMvcRequestBuilders.post(CatalogController.BASE_URI + "/add/"+savedShop.getId()+"/Gifts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gift)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON)).andReturn();
        json = result.getResponse().getContentAsString();
        gift= mapper.readValue(json, GiftDTO.class);
        assert(gift.getPointsNeeded()==10);
        assert(gift.getStatus().equals("VFP"));
        assert(Objects.equals(gift.getDescription(), "cookie"));
        assert (gift.getGiftId()!=null);
        mockMvc.perform(MockMvcRequestBuilders.delete(CatalogController.BASE_URI + "/Gifts/"+gift.getGiftId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.delete(CatalogController.BASE_URI + "/Gifts/"+gift.getGiftId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}


