package fr.univcotedazur.multicredit.cli.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univcotedazur.multicredit.cli.CliContext;
import fr.univcotedazur.multicredit.cli.model.CliAdmin;
import fr.univcotedazur.multicredit.cli.model.CliShop;
import fr.univcotedazur.multicredit.cli.model.CliShopKeeper;
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
class AdminCommandsTest {
    static final String BASE_URI = "/admin";
    private static final ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
    @MockBean
    CliContext cliContext;
    @Autowired
    private AdminCommands client;
    @Autowired
    private MockRestServiceServer server;

    @Test
    void addShopTest() throws JsonProcessingException {
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
    void testDeleteShopSuccess() {
        // configure server to return HTTP OK status code
        server.expect(requestTo(BASE_URI + "/shops/1"))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK));
        String result = client.deleteShop(1L);
        // assert that the command returned success message
        assertEquals("Shop deleted successfully", result);
        server.verify();
    }

    @Test
    void testDeleteShopNotFound() {
        // configure server to return HTTP NOT_FOUND status code
        server.expect(requestTo(BASE_URI + "/shops/2"))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.NOT_FOUND)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("shop not found"));
        String result = client.deleteShop(2L);
        assertEquals("Failed to delete shop : shop not found", result);
        server.verify();
    }

    @Test
    void testDeleteShopError() {
        server.expect(requestTo(BASE_URI + "/shops/2"))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.REQUEST_TIMEOUT)
                );
        String result = client.deleteShop(2L);
        assertEquals("Error while deleting shop", result);
        server.verify();
    }

    @Test
    void testAddAdmin() throws JsonProcessingException {
        CliAdmin admin = new CliAdmin("admin", "admin", "admin", "admin");
        String json = mapper.writeValueAsString(admin);
        server.expect(requestTo("/admin/register"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));
        assertEquals(admin.toString(), client.registerAdmin("admin", "admin", "admin", "admin"));
        server.verify();
    }

    @Test
    void testAddAdminConflict() throws JsonProcessingException {
        CliAdmin admin = new CliAdmin("admin", "admin", "admin", "admin");
        String json = mapper.writeValueAsString(admin);
        server.expect(requestTo("/admin/register"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.CONFLICT));
        assertEquals("409 Failed to add admin account : account already exists", client.registerAdmin("admin", "admin", "admin", "admin"));
        server.verify();
    }

    @Test
    void testAddAdminUnprocessable() throws JsonProcessingException {
        CliAdmin admin = new CliAdmin("admin", "admin", "admin", "admin");
        String json = mapper.writeValueAsString(admin);
        server.expect(requestTo("/admin/register"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.UNPROCESSABLE_ENTITY));
        assertEquals("422 Failed tto add admin account : invalid parameters", client.registerAdmin("admin", "admin", "admin", "admin"));
        server.verify();
    }

    @Test
    void testAddAdminError() throws JsonProcessingException {
        CliAdmin admin = new CliAdmin("admin", "admin", "admin", "admin");
        String json = mapper.writeValueAsString(admin);
        server.expect(requestTo("/admin/register"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.REQUEST_TIMEOUT));
        assertEquals("Error while adding admin account ", client.registerAdmin("admin", "admin", "admin", "admin"));
        server.verify();
    }

    @Test
    void testDeleteAdmin() throws JsonProcessingException {
        server.expect(requestTo(BASE_URI + "/1"))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK));
        assertEquals("Admin deleted successfully", client.deleteAdmin(1L));
        server.verify();
    }

    @Test
    void testDeleteAdminError() throws JsonProcessingException {
        CliAdmin admin = new CliAdmin("admin", "admin", "admin", "admin");
        String json = mapper.writeValueAsString(admin);
        server.expect(requestTo("/admin/1"))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.REQUEST_TIMEOUT));
        assertEquals("Error while deleting admin", client.deleteAdmin(1L));
        server.verify();
    }

    @Test
    void testDeleteAdminNotFound() throws JsonProcessingException {
        CliAdmin admin = new CliAdmin("admin", "admin", "admin", "admin");
        String json = mapper.writeValueAsString(admin);
        server.expect(requestTo("/admin/1"))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.NOT_FOUND)
                        .contentType(MediaType.APPLICATION_JSON));
        assertEquals("404 Failed to delete admin : admin not found", client.deleteAdmin(1L));
        server.verify();
    }

    @Test
    void addShopKeeperTest() throws JsonProcessingException {
        CliShopKeeper expectedShop = new CliShopKeeper("Sephora", "mail@gmail.com", "password", "11/04/2001");
        expectedShop.setShopId(1);
        String json = mapper.writeValueAsString(expectedShop);
        server.expect(requestTo(BASE_URI + "/shopKeepers/save"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));
        assertEquals(expectedShop.toString(), client.addShopKeeper(1L, "Sephora", "mail@gmail.com", "password", "11/04/2001"));
    }

    @Test
    void addShopKeeperNotFoundTest() throws JsonProcessingException {
        CliShopKeeper expectedShop = new CliShopKeeper("Sephora", "mail@gmail.com", "password", "11/04/2001");
        expectedShop.setShopId(1);
        String json = mapper.writeValueAsString(expectedShop);
        server.expect(requestTo(BASE_URI + "/shopKeepers/save"))
                .andRespond(withStatus(HttpStatus.NOT_FOUND)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("shop not found"));
        assertEquals("404 Failed to add shop keeper : shop not found", client.addShopKeeper(1L, "Sephora", "mail@gmail.com", "password", "11/04/2001"));
    }

    @Test
    void addShopKeeperConflictTest() throws JsonProcessingException {
        CliShopKeeper expectedShop = new CliShopKeeper("Sephora", "mail@gmail.com", "password", "11/04/2001");
        expectedShop.setShopId(1);
        String json = mapper.writeValueAsString(expectedShop);
        server.expect(requestTo(BASE_URI + "/shopKeepers/save"))
                .andRespond(withStatus(HttpStatus.CONFLICT)
                        .contentType(MediaType.APPLICATION_JSON));
        assertEquals("409 Failed to add shop keeper : shop keeper account already exists", client.addShopKeeper(1L, "Sephora", "mail@gmail.com", "password", "11/04/2001"));
    }

    @Test
    void addShopKeeperUnprocessableEntityTest() throws JsonProcessingException {
        CliShopKeeper expectedShop = new CliShopKeeper("Sephora", "mail@gmail.com", "password", "11/04/2001");
        expectedShop.setShopId(1);
        String json = mapper.writeValueAsString(expectedShop);
        server.expect(requestTo(BASE_URI + "/shopKeepers/save"))
                .andRespond(withStatus(HttpStatus.UNPROCESSABLE_ENTITY)
                        .contentType(MediaType.APPLICATION_JSON));
        assertEquals("422 Failed to add shop keeper :  invalid parameters", client.addShopKeeper(1L, "Sephora", "mail@gmail.com", "password", "11/04/2001"));
    }

    @Test
    void DeleteShopKeeper() throws JsonProcessingException {
        CliShopKeeper expectedShop = new CliShopKeeper("Sephora", "mail@gmail.com", "password", "11/04/2001");
        expectedShop.setShopId(1);
        server.expect(requestTo(BASE_URI + "/shopKeepers/1"))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK));
        String result = client.deleteShopKeeper(1L);
        assertEquals("Shop keeper deleted successfully", result);
    }

    @Test
    void DeleteShopKeeperError() throws JsonProcessingException {
        CliShopKeeper expectedShop = new CliShopKeeper("Sephora", "mail@gmail.com", "password", "11/04/2001");
        expectedShop.setShopId(1);
        server.expect(requestTo(BASE_URI + "/shopKeepers/1"))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.REQUEST_TIMEOUT));
        String result = client.deleteShopKeeper(1L);
        assertEquals("Error while deleting shop", result);
    }

    @Test
    void DeleteShopKeeperNotFound() throws JsonProcessingException {
        CliShopKeeper expectedShop = new CliShopKeeper("Sephora", "mail@gmail.com", "password", "11/04/2001");
        expectedShop.setShopId(1);
        server.expect(requestTo(BASE_URI + "/shopKeepers/1"))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.NOT_FOUND));
        String result = client.deleteShopKeeper(1L);
        assertEquals("404 Failed to delete shop : shop not found", result);
    }
}
