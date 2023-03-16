package fr.univcotedazur.simpletcfs.cucumber.member;

import fr.univcotedazur.simpletcfs.entities.MemberAccount;
import fr.univcotedazur.simpletcfs.interfaces.MemberFinder;
import fr.univcotedazur.simpletcfs.interfaces.MemberHandler;
import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UsePerkingTimeStepDefs {

    @Autowired
    MemberHandler memberHandler;
    @Autowired
    MemberFinder memberFinder;
    MemberAccount memberAccount;
    @Given("a regular member")
    public void aRegularMember() {

    }

}
