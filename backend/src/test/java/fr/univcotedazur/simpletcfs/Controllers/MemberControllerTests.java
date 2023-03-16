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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.mockito.Mockito.verify;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DirtiesContext

public class MemberControllerTests {
    @SpyBean
    ISWUPLS iswupls;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MemberHandler memberHandler;
    @Autowired
    private MemberFinder memberFinder;

    @BeforeEach
    void setUp() {
        try {
            memberHandler.deleteAccount(memberFinder.findByMail("John.Doe@mail.com"));
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

        ParkingDTO parkingDTO = new ParkingDTO("123456789", "John.Doe@mail.com");
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
        MemberAccount account = memberFinder.findByMail("John.Doe@mail.com");
        account.setStatus(AccountStatus.VFP);

        mockMvc.perform(MockMvcRequestBuilders.post(MemberController.BASE_URI + "/parking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(parkingDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
        verify(iswupls).startParkingTimer("123456789");

    }
}
