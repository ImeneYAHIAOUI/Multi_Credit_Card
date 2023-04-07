package fr.univcotedazur.multiCredit.cucumber.Admin;

import fr.univcotedazur.multiCredit.components.AdminManager;
import fr.univcotedazur.multiCredit.connectors.ISWUPLSProxy;
import fr.univcotedazur.multiCredit.interfaces.Bank;
import fr.univcotedazur.multiCredit.interfaces.MemberHandler;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.TestPropertySource;

@CucumberContextConfiguration
@SpringBootTest
public class AdminCucumberConfig {
    @Autowired
    @SpyBean
    AdminManager adminManager;




}
