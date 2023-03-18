package fr.univcotedazur.simpletcfs.cucumber.member;

import fr.univcotedazur.simpletcfs.connectors.ISWUPLSProxy;
import fr.univcotedazur.simpletcfs.entities.AccountStatus;
import fr.univcotedazur.simpletcfs.entities.MemberAccount;
import fr.univcotedazur.simpletcfs.exceptions.*;
import fr.univcotedazur.simpletcfs.interfaces.MemberFinder;
import fr.univcotedazur.simpletcfs.interfaces.MemberHandler;
import fr.univcotedazur.simpletcfs.interfaces.ParkingHandler;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@SpringBootTest
public class UsePerkingTimeStepDefs {

    @Autowired
    MemberHandler memberHandler;
    @Autowired
    MemberFinder memberFinder;
    MemberAccount memberAccount;

    @Autowired
    ISWUPLSProxy parkingProxy;


    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
    @Given("a member")
    public void aMember() throws UnderAgeException, MissingInformationException {
        when(parkingProxy.startParkingTimer(anyString(),anyInt())).thenReturn(true);
        try {
            memberAccount = memberHandler.createAccount("john doe","john.doe@mail.com","password", LocalDate.parse("11/04/2004", formatter));
        } catch (AlreadyExistingMemberException e) {
            memberAccount = memberFinder.findByMail("john.doe@mail.com").orElse(null);
        }
    }

    @When("the member is a vfp")
    public void vfpMember() throws AccountNotFoundException
    {
        memberHandler.updateAccountStatus(memberAccount, AccountStatus.VFP);
    }

    @When("the member is regular")
    public void regular() throws AccountNotFoundException {
        memberHandler.updateAccountStatus(memberAccount, AccountStatus.REGULAR);
    }

    @And("they want to use parking time")
    public void AndParkingTime()  {

    }

    @Then("they get a positive response")
    public void positive()  {

        assertDoesNotThrow(() -> memberHandler.useParkingTime(memberFinder.findByMail("john.doe@mail.com").orElse(null),"123456789",50));

    }

    @Then("they get a negative response")
    public void negative() {
        assertThrows(NotVFPException.class, () -> memberHandler.useParkingTime(memberFinder.findByMail("john.doe@mail.com").orElse(null), "123456789", 50));

    }
}
