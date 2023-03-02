package fr.univcotedazur.simpletcfs.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univcotedazur.simpletcfs.controllers.MemberController;
import fr.univcotedazur.simpletcfs.entities.AccountStatus;
import fr.univcotedazur.simpletcfs.entities.MemberAccount;
import fr.univcotedazur.simpletcfs.exceptions.*;
import fr.univcotedazur.simpletcfs.interfaces.MemberFinder;
import fr.univcotedazur.simpletcfs.interfaces.MemberHandler;
import fr.univcotedazur.simpletcfs.interfaces.ParkingHandler;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MemberManagerTests {

    @Autowired
    MemberHandler memberHandler;
    @Autowired
    MemberFinder memberFinder;

    @SpyBean
    ParkingHandler parkingHandler;



    @BeforeEach
    void setUp(){
        try {
            memberHandler.deleteAccount( memberFinder.findByMail("John.Doe@mail.com"));
        } catch (AccountNotFoundException ignored) {
        }
    }
    @Test
    public void testCreateAccount() throws AlreadyExistingMemberException, MissingInformationException, UnderAgeException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        assertNull(memberFinder.findByMail("John.Doe@mail.com"));
        MemberAccount account = memberHandler.createAccount("John Doe", "John.Doe@mail.com", "password", LocalDate.parse("11/04/2001", formatter));
        assertNotNull(memberFinder.findMember(account.getId()));
        assertThrows(AlreadyExistingMemberException.class, () -> memberHandler.createAccount("John Doe", "John.Doe@mail.com", "password", LocalDate.parse("11/04/2001", formatter)));
        assertThrows(MissingInformationException.class, () -> memberHandler.createAccount(null, "John.Doe2@mail.com", "password", LocalDate.parse("11/04/2001", formatter)));
        assertThrows(MissingInformationException.class, () -> memberHandler.createAccount("John Doe", null, "password", LocalDate.parse("11/04/2001", formatter)));
        assertThrows(MissingInformationException.class, () -> memberHandler.createAccount("John Doe", "John2.Doe@mail.com", null, LocalDate.parse("11/04/2001", formatter)));
        assertThrows(MissingInformationException.class, () -> memberHandler.createAccount("John Doe", "John2.Doe@mail.com", "password", null));
        assertThrows(UnderAgeException.class, () -> memberHandler.createAccount("John Doe", "John2.Doe@mail.com", "password", LocalDate.parse("11/04/2010", formatter)));
    }

    @Test
    public void testDeleteAccount() throws AlreadyExistingMemberException, MissingInformationException, UnderAgeException, AccountNotFoundException, AccountNotFoundException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        MemberAccount account = memberHandler.createAccount("John Doe", "John.Doe@mail.com", "password", LocalDate.parse("11/04/2001", formatter));
        assertNotNull(memberFinder.findMember(account.getId()));
        memberHandler.deleteAccount(account);
        assertNull(memberFinder.findMember(account.getId()));
    }

    @Test
    public void testStartParkingTime() throws AlreadyExistingMemberException, UnderAgeException, MissingInformationException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        MemberAccount account = memberHandler.createAccount("John Doe", "John.Doe@mail.com", "password", LocalDate.parse("11/04/2001", formatter));
        assertThrows(NotVFPException.class, () -> memberHandler.useParkingTime(account, "123456789"));
        account.setStatus(AccountStatus.VFP);
        try {
            memberHandler.useParkingTime(account, "123456789");
        } catch (Exception ignored) {

            verify(parkingHandler).registerParking("123456789");
        }
    }

}
