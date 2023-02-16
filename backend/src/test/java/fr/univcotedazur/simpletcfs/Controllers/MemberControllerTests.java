package fr.univcotedazur.simpletcfs.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univcotedazur.simpletcfs.controllers.MemberController;
import fr.univcotedazur.simpletcfs.controllers.dto.MemberDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class MemberControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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
                .andExpect(MockMvcResultMatchers.status().isConflict())
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
}
