package fr.univcotedazur.multiCredit.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univcotedazur.multiCredit.controllers.MemberController;
import fr.univcotedazur.multiCredit.controllers.dto.MemberDTO;
import fr.univcotedazur.multiCredit.entities.AccountStatus;
import fr.univcotedazur.multiCredit.exceptions.AccountNotFoundException;
import fr.univcotedazur.multiCredit.interfaces.MemberFinder;
import fr.univcotedazur.multiCredit.interfaces.MemberHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class MemberControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberHandler memberHandler;
    @Autowired
    private MemberFinder memberFinder;

    @BeforeEach
    void setUp(){
        try {
            memberHandler.deleteAccount( memberFinder.findByMail("John.Doe@mail.com").orElse(null));
        } catch (AccountNotFoundException ignored) {
        }
    }

    @Test
     void validMemberTest() throws Exception {
        MemberDTO validMember = new MemberDTO(0,"John Doe", "John.Doe@mail.com", "password", "11/04/2001");
        mockMvc.perform(MockMvcRequestBuilders.post(MemberController.BASE_URI + "/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validMember)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(MockMvcRequestBuilders.post(MemberController.BASE_URI + "/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validMember)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
     void inValidMemberTest() throws Exception {
        MemberDTO badMailAddress = new MemberDTO(0,"John Doe", "John.doe", "password", "11/04/2001");
        mockMvc.perform(MockMvcRequestBuilders.post(MemberController.BASE_URI + "/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(badMailAddress)))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));

        MemberDTO badName = new MemberDTO(0,"", "John.Doe@mail.com", "password", "11/04/2001");
        mockMvc.perform(MockMvcRequestBuilders.post(MemberController.BASE_URI + "/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(badName)))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));

        MemberDTO badPassword = new MemberDTO(0,"John Doe", "John.Doe@mail.com", "pass", "11/04/2001");
        mockMvc.perform(MockMvcRequestBuilders.post(MemberController.BASE_URI + "/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(badPassword)))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));

        MemberDTO badBirthday = new MemberDTO(0,"John Doe", "John.Doe@mail.com", "password", "11/04/2010");
        mockMvc.perform(MockMvcRequestBuilders.post(MemberController.BASE_URI + "/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(badBirthday)))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));

        MemberDTO missingInformation = new MemberDTO(0,null, "John.Doe@mail.com", "password", "11/04/2001");
        mockMvc.perform(MockMvcRequestBuilders.post(MemberController.BASE_URI + "/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(missingInformation)))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }



    @Test
    void deleteMemberTest() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        String mail = "John.Doe@mail.com";
        memberHandler.createAccount("John Doe", mail, "password", LocalDate.parse("11/04/2001",formatter));
        assertTrue(memberFinder.findByMail(mail).isPresent());
        mockMvc.perform(MockMvcRequestBuilders.delete(MemberController.BASE_URI + "/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mail)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
       assertTrue(memberFinder.findByMail(mail).isEmpty());
    }

    @Test
    void archiveMemberTest() throws Exception {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        String mail = "John.Doe@mail.com";
        memberHandler.createAccount("John Doe", mail, "password", LocalDate.parse("11/04/2001",formatter));
        assertTrue(memberFinder.findByMail(mail).isPresent());
        assertTrue(memberFinder.findByMail(mail).get().getStatus().equals(AccountStatus.REGULAR));
        mockMvc.perform(MockMvcRequestBuilders.put(MemberController.BASE_URI + "/archive")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mail)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
        assertTrue(memberFinder.findByMail(mail).get().getStatus().equals(AccountStatus.EXPIRED));

    }

    @Test
     void restoreAccountTests() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        String mail = "John.Doe@mail.com";
        memberHandler.createAccount("John Doe", mail, "password", LocalDate.parse("11/04/2001",formatter));
        assertTrue(memberFinder.findByMail(mail).isPresent());
        memberHandler.updateAccountStatus(memberFinder.findByMail("John.Doe@mail.com").get(),AccountStatus.EXPIRED);
        assertTrue(memberFinder.findByMail(mail).get().getStatus().equals(AccountStatus.EXPIRED));
        mockMvc.perform(MockMvcRequestBuilders.put(MemberController.BASE_URI + "/restore")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mail)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
        assertTrue(memberFinder.findByMail(mail).get().getStatus().equals(AccountStatus.REGULAR));

    }

}
