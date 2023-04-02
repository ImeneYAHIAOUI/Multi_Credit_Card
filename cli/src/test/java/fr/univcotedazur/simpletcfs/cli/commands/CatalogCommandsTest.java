package fr.univcotedazur.simpletcfs.cli.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univcotedazur.simpletcfs.cli.CliContext;
import fr.univcotedazur.simpletcfs.cli.model.CliGift;
import fr.univcotedazur.simpletcfs.cli.model.CliProduct;
import fr.univcotedazur.simpletcfs.cli.model.CliShop;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(CatalogCommands.class)
public class CatalogCommandsTest {
    public static final String BASE_URI = "/catalog";
    private static final ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
    @Autowired
    private CatalogCommands client;
    @MockBean
    CliContext cliContext;
    @Autowired
    private MockRestServiceServer server;
    @Test
    public void addProductTest() {
        server.expect(requestTo(BASE_URI + "/add/1/Products"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.NOT_FOUND)
                        .contentType(MediaType.APPLICATION_JSON));
        assertEquals("Failed to add product : Shop not found", client.addProduct(1L,"product", 1.2,0,0.0));
        server.verify();
    }
    @Test
    public void addProductTestConflict() {
        server.expect(requestTo(BASE_URI + "/add/1/Products"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.CONFLICT)
                        .contentType(MediaType.APPLICATION_JSON));
        assertEquals("Failed to add product :Product already exists", client.addProduct(1L,"product", 1.2,0,0.0));
        server.verify();
    }
    @Test
    public void addProductTestNotFound() {
        server.expect(requestTo(BASE_URI + "/add/1/Products"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.NOT_FOUND)
                        .contentType(MediaType.APPLICATION_JSON));
        assertEquals("Failed to add product : Shop not found", client.addProduct(1L,"product", 1.2,0,0.0));
        server.verify();
    }
    @Test
    public void addGiftTestNotFound() {
        server.expect(requestTo(BASE_URI + "/add/1/Gifts"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.NOT_FOUND)
                        .contentType(MediaType.APPLICATION_JSON));
        assertEquals("Failed to add gift : Shop not found",client.addGift(1L,2,"gift", "VFP"));
        server.verify();
    }
    @Test
    public void addGiftTest() {
        server.expect(requestTo(BASE_URI + "/add/1/Gifts"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.NOT_FOUND)
                        .contentType(MediaType.APPLICATION_JSON));
        assertEquals("Failed to add gift : Shop not found", client.addGift(1L,2,"gift", "VFP"));
        server.verify();
    }
    @Test
    public void addGiftTestConflict() {
        server.expect(requestTo(BASE_URI + "/add/1/Gifts"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.CONFLICT)
                        .contentType(MediaType.APPLICATION_JSON));
        assertEquals("Failed to add gift : Gift already exists", client.addGift(1L,2,"gift", "VFP")  );
        server.verify();
    }
    @Test
    public void addGiftTestErrorStatus() {
        assertEquals("Failed to add gift : Invalid status", client.addGift(1L,2,"gift", "status"));
    }
    @Test public void getGiftTestNotFound() {
        server.expect(requestTo(BASE_URI + "/Gifts/1"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.NOT_FOUND)
                        .contentType(MediaType.APPLICATION_JSON));
        assertEquals("Gift id 1 unknown", client.getGift(1L));
        server.verify();
    }
    @Test
    public void getProductTestNotFound() {
        server.expect(requestTo(BASE_URI + "/Products/1"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.NOT_FOUND)
                        .contentType(MediaType.APPLICATION_JSON));
        assertEquals("Product id 1 unknown", client.getProduct(1L));
        server.verify();
    }
    @Test
    public void getProductTest() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        CliProduct p = new CliProduct("product", 1,1.0,0.0);
        p.setId(1L);
        String json = mapper.writeValueAsString(p);
        server.expect(requestTo(BASE_URI + "/Products/1"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));
        assertEquals(p.toString(), client.getProduct(1L));
        server.verify();
    }
    @Test
    public void getGiftTest() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        CliGift p = new CliGift(5,"gift", "REGULAR");
        p.setGiftId(1L);
        String json = mapper.writeValueAsString(p);
        server.expect(requestTo(BASE_URI + "/Gifts/1"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));
        assertEquals(p.toString(), client.getGift(1L));
        server.verify();
    }
     @Test
    public void deleteGiftTestNotFound(){
        server.expect(requestTo(BASE_URI + "/Gifts/1"))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON));
        assertEquals("Failed to delete gift : Gift id 1 unknown", client.deleteGift(1L));
        server.verify();
     }
    @Test
    public void deleteGiftTest(){
        server.expect(requestTo(BASE_URI + "/Gifts/1"))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON));
        assertEquals("gift deleted successfully", client.deleteGift(1L));
        server.verify();
    }
    @Test
    public void deleteProductTest(){
        server.expect(requestTo(BASE_URI + "/Products/1"))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON));
        assertEquals("product deleted successfully", client.deleteProduct(1L));
        server.verify();
    }
    @Test
    public void deleteProductTestNotFound(){
        server.expect(requestTo(BASE_URI + "/Products/1"))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.NOT_FOUND)
                        .contentType(MediaType.APPLICATION_JSON));
        assertEquals("Failed to delete product : Product id 1 unknown", client.deleteProduct(1L));
        server.verify();
    }
}
