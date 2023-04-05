package fr.univcotedazur.simpletcfs.cli.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univcotedazur.simpletcfs.cli.CliContext;
import fr.univcotedazur.simpletcfs.cli.model.CliShop;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(ShopCommands.class)
public class ShopCommandsTest {
    public static final String BASE_URI_SHOPS = "/shops";
    private static final ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
    @Autowired
    private ShopCommands client;
    @MockBean
    CliContext cliContext;
    @Autowired
    private MockRestServiceServer server;

    @Test
    public  void getShopTest()throws JsonProcessingException {

        String name = "sephsssdsora";
        String address = "adresse";
        ObjectMapper mapper = new ObjectMapper();
        CliShop savedshop = new CliShop(name, address);
        savedshop.setId(10L);
        String json = mapper.writeValueAsString(savedshop);
        server.expect(requestTo(BASE_URI_SHOPS + "/" + savedshop.getId()))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));

        assertEquals("{\"id\":10,\"name\":\"sephsssdsora\",\"address\":\"adresse\"}", client.getShop(savedshop.getId()));
        server.verify();
    }
    @Test
    public  void getShopTestNotFound()throws JsonProcessingException {

        server.expect(requestTo(BASE_URI_SHOPS + "/10" ))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.NOT_FOUND)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("shop not found"));
        assertEquals("Invalid shop id : shop not found", client.getShop(10L));
        server.verify();
    }


    @Test
    public void testupdateShopAddress() {
        server.expect(requestTo(BASE_URI_SHOPS + "/2/address"))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body("Shop address updated successfully"));
        String result=client.updateShopAddress(2L,"newAddress");
        assertEquals("Shop address updated successfully", result);
        server.verify();
    }

    @Test
    public void testupdateShopAddressNotFound() {
        server.expect(requestTo(BASE_URI_SHOPS + "/2/address"     ))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.NOT_FOUND)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("shop not found"));
        String result=client.updateShopAddress(2L,"newAddress");
        assertEquals("Invalid shop id : shop not found", result);
        server.verify();
    }
    @Test
    public void testmodifyPlanning() {
        server.expect(requestTo(BASE_URI_SHOPS + "/2/planning"))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("Shop planning for friday updated successfully"));
        String result=client.modifyPlanning(2L,"Friday","10:00","12:00");
        assertEquals("Planning successfully modified.", result);
        server.verify();
    }
    @Test
    public void testmodifyPlanningNotFound() {
        server.expect(requestTo(BASE_URI_SHOPS + "/2/planning"))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.NOT_FOUND)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("shop not found"));
        String result=client.modifyPlanning(2L,"Friday","10:00","12:00");
        assertEquals("Failed to modify planning : shop not found", result);
        server.verify();
    }

    @Test
    public void testmodifyPlanningError1() {
        server.expect(requestTo(BASE_URI_SHOPS + "/2/planning"))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.BAD_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("Invalid opening/closing hours parameters"));
        String result=client.modifyPlanning(2L,"Friday","14:00","12:00");
        assertEquals( "Failed to modify planning : Invalid opening/closing hours parameters", result);
        server.verify();
    }
    @Test
    public void testmodifyPlanningError2() {
        String result=client.modifyPlanning(2L,"Home","10:00","12:00");
        assertEquals( "Failed to modify planning : Invalid day of week", result);
        server.verify();
    }
    @Test
    public void testmodifyPlanningError3() {
        String result=client.modifyPlanning(2L,"Friday","4444","12:00");
        assertEquals( "Failed to modify planning : Invalid time format (must be HH:mm)", result);
        server.verify();
    }

}
