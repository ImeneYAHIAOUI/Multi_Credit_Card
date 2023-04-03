package fr.univcotedazur.simpletcfs.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univcotedazur.simpletcfs.controllers.MemberController;
import fr.univcotedazur.simpletcfs.controllers.ParkingController;
import fr.univcotedazur.simpletcfs.controllers.dto.ParkingDTO;
import fr.univcotedazur.simpletcfs.entities.AccountStatus;
import fr.univcotedazur.simpletcfs.entities.MemberAccount;
import fr.univcotedazur.simpletcfs.exceptions.*;
import fr.univcotedazur.simpletcfs.interfaces.ISWUPLS;
import fr.univcotedazur.simpletcfs.interfaces.MemberFinder;
import fr.univcotedazur.simpletcfs.interfaces.MemberHandler;
import fr.univcotedazur.simpletcfs.interfaces.ParkingHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource(properties = {"VFP.updateRate.cron=*/1 * * * * *","VFP.MinPurchasesNumber=5"})
@Commit
@Transactional
public class ParkingManagerTest {
    @Autowired
    MemberHandler memberHandler;
    @SpyBean
    ParkingHandler parkingHandler;
    @Autowired
    MemberFinder memberFinder;
    @MockBean
    ISWUPLS iswupls;

    @Test
    void registerParkingTest(){
        try {
            parkingHandler.registerParking("123456789",0);
        }
        catch(Exception ignored){

        }
        verify(iswupls).startParkingTimer("123456789",0);
    }
    @Test
    public void testStartParkingTime() throws AlreadyExistingMemberException, UnderAgeException, MissingInformationException, AccountNotFoundException, NotVFPException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        MemberAccount account;
        try {
            account = memberHandler.createAccount("John Doe", "John.Doe@mail.com", "password", LocalDate.parse("11/04/2001", formatter));
        }catch (AlreadyExistingMemberException e){
            memberHandler.deleteAccount(memberFinder.findByMail("John.Doe@mail.com").get());
            account = memberHandler.createAccount("John Doe", "John.Doe@mail.com", "password", LocalDate.parse("11/04/2001", formatter));
        }
        MemberAccount finalAccount = account;
        assertThrows(NotVFPException.class, () ->parkingHandler.useParkingTime(finalAccount, "123456789",0));
        account.setStatus(AccountStatus.VFP);
        when(iswupls.startParkingTimer(anyString(),anyInt())).thenReturn(true);
        parkingHandler.useParkingTime(account, "123456789",0);
    }



}
