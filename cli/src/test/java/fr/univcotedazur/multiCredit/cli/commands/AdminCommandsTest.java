package fr.univcotedazur.multiCredit.cli.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univcotedazur.multiCredit.cli.CliContext;
import fr.univcotedazur.multiCredit.cli.model.CliAdmin;
import fr.univcotedazur.multiCredit.cli.model.CliShop;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
@RestClientTest(AdminCommands.class)
public class AdminCommandsTest {
    public static final String BASE_URI = "/admin";
    private static final ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
    @Autowired
    private AdminCommands client;
    @MockBean
    CliContext cliContext;
    @Autowired
    private MockRestServiceServer server;

    @Test
    public void addShopTest() throws JsonProcessingException {
        String name = "sephora";
        String address = "adresse";
        CliShop expectedShop = new CliShop(name, address);
        String json = mapper.writeValueAsString(expectedShop);
        server.expect(requestTo(BASE_URI + "/shops/save"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(MockRestRequestMatchers.content().json("{\"name\":\"" + name + "\",\"address\":\"" + address + "\"}"))
                .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));
        assertEquals(expectedShop.toString(), client.addShop(name, address));
        server.verify();
    }
    @Test
    public void testDeleteShopSuccess() {
        // configure server to return HTTP OK status code
        server.expect(requestTo(BASE_URI + "/shops/1"))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK));
        String result =  client.deleteShop(1L);
        // assert that the command returned success message
        assertEquals("Shop deleted successfully", result);
        server.verify();
    }
    @Test
    public void testDeleteShopNotFound() {
        // configure server to return HTTP NOT_FOUND status code
        server.expect(requestTo(BASE_URI + "/shops/2"))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.NOT_FOUND)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("shop not found"));
        String result=client.deleteShop(2L);
        assertEquals("Failed to delete shop : shop not found", result);
        server.verify();
    }
    @Test
    public void testDeleteShopError() {
        server.expect(requestTo(BASE_URI + "/shops/2"))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.REQUEST_TIMEOUT)
                );
        String result=client.deleteShop(2L);
        assertEquals("Error while deleting shop", result);
        server.verify();
    }
    @Test
    public void testAddAdmin() throws JsonProcessingException {
        CliAdmin admin=new CliAdmin("admin","admin","admin","admin");
        String json = mapper.writeValueAsString(admin);
        server.expect(requestTo(  "/admin/register"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));
        assertEquals(admin.toString(),client.registerAdmin("admin","admin","admin","admin"));
        server.verify();
    }

}
