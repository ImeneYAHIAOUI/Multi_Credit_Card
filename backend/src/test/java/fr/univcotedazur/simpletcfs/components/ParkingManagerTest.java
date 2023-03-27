package fr.univcotedazur.simpletcfs.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univcotedazur.simpletcfs.entities.MemberAccount;
import fr.univcotedazur.simpletcfs.interfaces.ISWUPLS;
import fr.univcotedazur.simpletcfs.interfaces.ParkingHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.mockito.Mockito.verify;

@SpringBootTest
@Transactional
public class ParkingManagerTest {


    @Autowired
    ParkingHandler parkingHandler;

    @SpyBean
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



}
