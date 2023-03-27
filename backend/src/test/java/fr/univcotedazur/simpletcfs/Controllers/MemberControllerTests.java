package fr.univcotedazur.simpletcfs.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univcotedazur.simpletcfs.controllers.MemberController;
import fr.univcotedazur.simpletcfs.controllers.dto.MemberDTO;
import fr.univcotedazur.simpletcfs.controllers.dto.ParkingDTO;
import fr.univcotedazur.simpletcfs.entities.AccountStatus;
import fr.univcotedazur.simpletcfs.entities.MemberAccount;
import fr.univcotedazur.simpletcfs.exceptions.AccountNotFoundException;
import fr.univcotedazur.simpletcfs.interfaces.ISWUPLS;
import fr.univcotedazur.simpletcfs.interfaces.MemberFinder;
import fr.univcotedazur.simpletcfs.interfaces.MemberHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
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
    @SpyBean
    ISWUPLS iswupls;
    @BeforeEach
    void setUp(){
        try {
            memberHandler.deleteAccount( memberFinder.findByMail("John.Doe@mail.com").orElse(null));
        } catch (AccountNotFoundException ignored) {
        }
    }

    @Test
    public void validMemberTest() throws Exception {
        MemberDTO validMember = new MemberDTO("John Doe", "John.Doe@mail.com", "password", "11/04/2001");
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
    public void inValidMemberTest() throws Exception {
        MemberDTO badMailAddress = new MemberDTO("John Doe", "John.doe", "password", "11/04/2001");
        mockMvc.perform(MockMvcRequestBuilders.post(MemberController.BASE_URI + "/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(badMailAddress)))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));

        MemberDTO badName = new MemberDTO("", "John.Doe@mail.com", "password", "11/04/2001");
        mockMvc.perform(MockMvcRequestBuilders.post(MemberController.BASE_URI + "/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(badName)))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));

        MemberDTO badPassword = new MemberDTO("John Doe", "John.Doe@mail.com", "pass", "11/04/2001");
        mockMvc.perform(MockMvcRequestBuilders.post(MemberController.BASE_URI + "/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(badPassword)))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));

        MemberDTO badBirthday = new MemberDTO("John Doe", "John.Doe@mail.com", "password", "11/04/2010");
        mockMvc.perform(MockMvcRequestBuilders.post(MemberController.BASE_URI + "/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(badBirthday)))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));

        MemberDTO missingInformation = new MemberDTO(null, "John.Doe@mail.com", "password", "11/04/2001");
        mockMvc.perform(MockMvcRequestBuilders.post(MemberController.BASE_URI + "/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(missingInformation)))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void ParkingTests() throws Exception {

        ParkingDTO parkingDTO = new ParkingDTO("123456789","John.Doe@mail.com",1);
            mockMvc.perform(MockMvcRequestBuilders.post(MemberController.BASE_URI + "/parking")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(parkingDTO)))
                    .andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andExpect(MockMvcResultMatchers.content()
                            .contentType(MediaType.APPLICATION_JSON));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
            memberHandler.createAccount("John Doe", "John.Doe@mail.com", "pass", LocalDate.parse("11/04/2001", formatter));
            mockMvc.perform(MockMvcRequestBuilders.post(MemberController.BASE_URI + "/parking")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(parkingDTO)))
                    .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                    .andExpect(MockMvcResultMatchers.content()
                            .contentType(MediaType.APPLICATION_JSON));
            MemberAccount account = memberFinder.findByMail("John.Doe@mail.com").orElse(null);

        memberHandler.updateAccountStatus(account, AccountStatus.VFP);
            mockMvc.perform(MockMvcRequestBuilders.post(MemberController.BASE_URI + "/parking")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(parkingDTO)))
                    .andExpect(MockMvcResultMatchers.status().isCreated())
                    .andExpect(MockMvcResultMatchers.content()
                            .contentType(MediaType.APPLICATION_JSON));
            verify(iswupls).startParkingTimer("123456789",1);

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
    public void restoreAccountTests() throws Exception {
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
