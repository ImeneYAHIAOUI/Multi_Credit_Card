package fr.univcotedazur.simpletcfs.cli.commands;
import fr.univcotedazur.simpletcfs.cli.model.CliMember;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.anyOf;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RestClientTest(MemberCommands.class)
public class MemberCommandsTests {
    @Autowired
    private MemberCommands client;

    @Autowired
    private MockRestServiceServer server;


    /*@Test
    public void registerMemberTest() {

        server
                .expect(requestTo("/members"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("[\"John Doe\",\"John.Doe@mail.com\",\"Password\",\"11/04/2001\"]", MediaType.APPLICATION_JSON));

        assertEquals(anyOf(CliMember.class), client.register("John Doe","John.Doe@mail.com","Password","11/04/2001"));
    }*/



}
