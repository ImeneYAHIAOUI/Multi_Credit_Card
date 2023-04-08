package fr.univcotedazur.multicredit.cli.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univcotedazur.multicredit.cli.CliContext;
import fr.univcotedazur.multicredit.cli.model.CliParking;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(ParkingCommands.class)
public class ParkingCommandsTest {
    public static final String BASE_URI = "/parking";
    private static final ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
    @Autowired
    private ParkingCommands client;
    @MockBean
    CliContext cliContext;
    @Autowired
    private MockRestServiceServer server;
    @Test
    void startParking() throws JsonProcessingException {
        CliParking par= new CliParking("012345678900","sorur.gsz@gmail.com",12);
        String json = mapper.writeValueAsString(par);
        server.expect(requestTo(  BASE_URI ))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));
        assertEquals(par.toString(),client.startParking("012345678900","sorur.gsz@gmail.com",12).toString());
        server.verify();
    }
}
