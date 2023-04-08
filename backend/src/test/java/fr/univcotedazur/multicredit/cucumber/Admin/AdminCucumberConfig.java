package fr.univcotedazur.multicredit.cucumber.Admin;

import fr.univcotedazur.multicredit.components.AdminManager;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

@CucumberContextConfiguration
@SpringBootTest
public class AdminCucumberConfig {
    @Autowired
    @SpyBean
    AdminManager adminManager;




}
