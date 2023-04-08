package fr.univcotedazur.multicredit.components;

import fr.univcotedazur.multicredit.entities.AccountStatus;
import fr.univcotedazur.multicredit.entities.MemberAccount;
import fr.univcotedazur.multicredit.exceptions.*;
import fr.univcotedazur.multicredit.interfaces.ISWUPLS;
import fr.univcotedazur.multicredit.interfaces.MemberFinder;
import fr.univcotedazur.multicredit.interfaces.MemberHandler;
import fr.univcotedazur.multicredit.interfaces.ParkingHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.TestPropertySource;

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
     void testStartParkingTime() throws AlreadyExistingMemberException, UnderAgeException, MissingInformationException, AccountNotFoundException, NotVFPException {
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
