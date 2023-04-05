package fr.univcotedazur.multiCredit.cucumber.Shop;


import fr.univcotedazur.multiCredit.connectors.MailProxy;
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

