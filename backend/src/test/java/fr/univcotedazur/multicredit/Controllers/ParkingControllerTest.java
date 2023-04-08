package fr.univcotedazur.multicredit.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univcotedazur.multicredit.controllers.ParkingController;
import fr.univcotedazur.multicredit.controllers.dto.ParkingDTO;
import fr.univcotedazur.multicredit.entities.AccountStatus;
import fr.univcotedazur.multicredit.entities.MemberAccount;
import fr.univcotedazur.multicredit.exceptions.AccountNotFoundException;
import fr.univcotedazur.multicredit.interfaces.ISWUPLS;
import fr.univcotedazur.multicredit.interfaces.MemberFinder;
import fr.univcotedazur.multicredit.interfaces.MemberHandler;
import fr.univcotedazur.multicredit.interfaces.ParkingHandler;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class ParkingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberHandler memberHandler;
    @Autowired
    private MemberFinder memberFinder;
    @Autowired
    private ParkingHandler parkingHandler;
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
    void ParkingTests() throws Exception {
        ParkingDTO parkingDTO = new ParkingDTO("123456789","John.Doe@mail.com",1);
        mockMvc.perform(MockMvcRequestBuilders.post(ParkingController.BASE_URI )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(parkingDTO)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        memberHandler.createAccount("John Doe", "John.Doe@mail.com", "pass", LocalDate.parse("11/04/2001", formatter));
        mockMvc.perform(MockMvcRequestBuilders.post(ParkingController.BASE_URI )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(parkingDTO)))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
        MemberAccount account = memberFinder.findByMail("John.Doe@mail.com").orElse(null);

        memberHandler.updateAccountStatus(account, AccountStatus.VFP);
        mockMvc.perform(MockMvcRequestBuilders.post(ParkingController.BASE_URI )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(parkingDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
        verify(iswupls).startParkingTimer("123456789",1);

    }

}
