package fr.univcotedazur.multicredit.cucumber.Transaction;

import fr.univcotedazur.multicredit.interfaces.Bank;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@CucumberContextConfiguration
@SpringBootTest
public class TransactionCucumberConfig {
    @MockBean // Spring/Cucumber bug workaround: declare the mock here, and autowire+setup it in the step classes
    private Bank bankMock;
}

