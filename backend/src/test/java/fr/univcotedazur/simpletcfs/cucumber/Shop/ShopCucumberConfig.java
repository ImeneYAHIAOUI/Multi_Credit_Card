package fr.univcotedazur.simpletcfs.cucumber.Shop;


import fr.univcotedazur.simpletcfs.connectors.ISWUPLSProxy;
import fr.univcotedazur.simpletcfs.connectors.MailProxy;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@CucumberContextConfiguration
@SpringBootTest
public class ShopCucumberConfig {
    @Autowired
    @MockBean
    private MailProxy mailSender;
}

