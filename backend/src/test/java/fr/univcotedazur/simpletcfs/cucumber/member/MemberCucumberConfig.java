package fr.univcotedazur.simpletcfs.cucumber.member;

import fr.univcotedazur.simpletcfs.connectors.ISWUPLSProxy;
import fr.univcotedazur.simpletcfs.interfaces.Bank;
import fr.univcotedazur.simpletcfs.interfaces.MemberHandler;
import fr.univcotedazur.simpletcfs.interfaces.ParkingHandler;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.TestPropertySource;

@CucumberContextConfiguration
@SpringBootTest
@TestPropertySource(properties = {"VFP.updateRate.cron=*/1 * * * * *","VFP.MinPurchasesNumber=5"})
public class MemberCucumberConfig {
    @Autowired
    @SpyBean
    MemberHandler memberHandler;

    @Autowired
    @MockBean
    ISWUPLSProxy parkingProxy;

    @Autowired
    @MockBean
    Bank bank;
}
