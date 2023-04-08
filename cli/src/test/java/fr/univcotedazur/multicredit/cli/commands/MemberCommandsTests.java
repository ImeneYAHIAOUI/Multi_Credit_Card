package fr.univcotedazur.multicredit.cli.commands;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univcotedazur.multicredit.cli.CliContext;
import fr.univcotedazur.multicredit.cli.model.CliMember;
import fr.univcotedazur.multicredit.cli.model.CliUpdateStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
    public static final String BASE_URI = "/members";
    private static final ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
    @Autowired
    private MemberCommands client;
    @MockBean
    CliContext cliContext;
    @Autowired
    private MockRestServiceServer server;
    @Test
    void registerMemberTest() throws JsonProcessingException {
        CliMember member=new CliMember("sourour","sourour.gazzrh@gmail.com","password","02/03/1999");

        String json = mapper.writeValueAsString(member);
        server.expect(requestTo(  BASE_URI + "/register"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));
        assertEquals(member.toString(),client.registerMember("sourour","sourour.gazzrh@gmail.com","password","02/03/1999").toString());
        server.verify();
    }
    @Test
    void deleteMemberTest() throws JsonProcessingException {
        String json = mapper.writeValueAsString("Member deleted");
        server.expect(requestTo(  BASE_URI + "/delete"))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));
        assertEquals("Member deleted",client.deleteMember("so.gzh@gmail.com"));
        server.verify();
    }
    @Test
    void deleteMemberWithId()throws JsonProcessingException {
        String json = mapper.writeValueAsString("Member deleted");
        server.expect(requestTo(  BASE_URI + "/1"))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));
        assertEquals("Member deleted",client.deleteMemberWithId(1L));
        server.verify();
    }
    @Test
    void archiveMember()throws JsonProcessingException {
        String json = mapper.writeValueAsString("Member restored");
        server.expect(requestTo(  BASE_URI + "/restore"))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));
        assertEquals("Member restored",client.restoreMember("sourour.gazzh@gmail.com"));
        server.verify();
    }
    @Test
    void updateStatus()throws JsonProcessingException {
        CliUpdateStatus status=new CliUpdateStatus("sourour.gazzeh@gmail.com","REGULAR");
        String json = mapper.writeValueAsString(status);
        server.expect(requestTo(  BASE_URI + "/status"))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));
        assertEquals(status.toString(),client.updateStatus("sourour.gazzeh@gmail.com","REGULAR").toString());
        server.verify();
    }
    @Test
    void chargeMembershipCard()throws JsonProcessingException {
        String json = mapper.writeValueAsString("Membership card charged");
        server.expect(requestTo(  BASE_URI + "/charge"))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));
        assertEquals("Membership card charged",client.chargeMembershipCard(10L,12.5,"123456789000").toString());
        server.verify();
    }
    @Test
    void updateAllMembersStatus() throws JsonProcessingException{
        String json = mapper.writeValueAsString("Membership card charged");
        server.expect(requestTo(  BASE_URI + "/status"))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));
        assertEquals("All members status updated",client.updateAllMembersStatus());
        server.verify();
    }
    @Test
    void updateMember()throws JsonProcessingException{
        CliMember member=new CliMember("sourour","sourour.gazzrh@gmail.com","password","02/03/1999");
        String json = mapper.writeValueAsString(member);
        server.expect(requestTo(  BASE_URI + "/2"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));
        assertEquals(member.toString(),client.updateMember(2L,"sourour","sourour.gazzrh@gmail.com","password","02/03/1999").toString());
    }
    @Test
    void renewMembership()throws JsonProcessingException{
        CliMember member=new CliMember("sourour","sourour.gazzrh@gmail.com","password","02/03/1999");
        String json = mapper.writeValueAsString(member);
        server.expect(requestTo(  BASE_URI + "/renew/2"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));
        assertEquals(member.toString(),client.renewMembership(2L).toString());
    }
    @Test
    void archiveMembert()throws JsonProcessingException
    {
        String json = mapper.writeValueAsString("Member archived");
        server.expect(requestTo(  BASE_URI + "/archive"))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));
        assertEquals("Member archived",client.archiveMember("sourour.gazzrh@gmail.com"));
    }

}
