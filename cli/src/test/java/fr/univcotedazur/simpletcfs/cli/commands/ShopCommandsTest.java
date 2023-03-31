package fr.univcotedazur.simpletcfs.cli.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univcotedazur.simpletcfs.cli.CliContext;
import fr.univcotedazur.simpletcfs.cli.model.CliShop;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(ShopCommands.class)
public class ShopCommandsTest {
    public static final String BASE_URI = "/shops";
    private static final ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
    @Autowired
    private ShopCommands client;
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
        server.expect(requestTo(BASE_URI + "/save"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(MockRestRequestMatchers.content().json("{\"name\":\"" + name + "\",\"address\":\"" + address + "\"}"))
                .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));
        assertEquals(expectedShop.toString(), client.addShop(name, address));
        server.verify();
    }
    @Test
    public  void getShopTest()throws JsonProcessingException {

        String name = "sephsssdsora";
        String address = "adresse";
        ObjectMapper mapper = new ObjectMapper();
        CliShop savedshop = new CliShop(name, address);
        savedshop.setId(10L);
        String json = mapper.writeValueAsString(savedshop);
        server.expect(requestTo(BASE_URI + "/" + savedshop.getId()))
                .andExpect(method(HttpMethod.GET))
                .andExpect(MockRestRequestMatchers.content().json("{\"id\":\"" + savedshop.getId() +"\",\"name\":\"" + name+ "\",\"address\":\"" + address + "\"}"))
                .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));
        assertEquals(savedshop.toString(), client.getShop(savedshop.getId()));
    }
    @Test
    public void  updateShopAddressTest()throws JsonProcessingException {

    }

}
