package fr.univcotedazur.multiCredit.cli.commands;
import fr.univcotedazur.multiCredit.cli.CliContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.anyOf;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RestClientTest(MemberCommands.class)
public class MemberCommandsTests {
    @Autowired
    private MemberCommands client;

    @Autowired
    private MockRestServiceServer server;
@MockBean
    CliContext cliContext;

    /*@Test
    public void registerMemberTest() {

        server
                .expect(requestTo("/members"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("[\"John Doe\",\"John.Doe@mail.com\",\"Password\",\"11/04/2001\"]", MediaType.APPLICATION_JSON));

        assertEquals(anyOf(CliMember.class), client.register("John Doe","John.Doe@mail.com","Password","11/04/2001"));
    }*/



}
