package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.entities.*;
import fr.univcotedazur.simpletcfs.exceptions.AlreadyExistingMemberException;
import fr.univcotedazur.simpletcfs.exceptions.InsufficientPointsException;
import fr.univcotedazur.simpletcfs.exceptions.MissingInformationException;
import fr.univcotedazur.simpletcfs.exceptions.UnderAgeException;
import fr.univcotedazur.simpletcfs.interfaces.MemberFinder;
import fr.univcotedazur.simpletcfs.interfaces.MemberHandler;
import fr.univcotedazur.simpletcfs.repositories.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PointsManagerTest {
    @Autowired
    MemberFinder memberFinder;
    MemberAccount account;
    @Autowired
    PointsManager pointsManager;
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    MemberHandler memberHandler;

    void setUp(String mail,String name)throws AlreadyExistingMemberException, MissingInformationException, UnderAgeException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        try {
            account = memberHandler.createAccount(name, mail, "password", LocalDate.parse("11/04/2001", formatter));
        }
        catch (AlreadyExistingMemberException e){
            account = memberFinder.findByMail(mail).get();
        }
        assertNotNull(memberFinder.findById(account.getId()).orElse(null));
    }
    @Test
    public void removePointsTest()throws AlreadyExistingMemberException, MissingInformationException, UnderAgeException{
        setUp("John.Doe@mail.com","John");
        account.setPoints(100);
        UsePoints transaction=new UsePoints(LocalDate.now(),account);
        transaction.setUsedPoints(50);
        assertDoesNotThrow(()-> pointsManager.removePoints(account,transaction));
        assertEquals(50, account.getPoints());
    }
    @Test
    public void removePointsTest1() throws AlreadyExistingMemberException, UnderAgeException, MissingInformationException {
        setUp("John.Doe@mail.com","John");
        account=memberFinder.findByMail("John.Doe@mail.com").orElse(null);
        account.setPoints(10);
        UsePoints transaction=new UsePoints(LocalDate.now(),account);
        transaction.setUsedPoints(50);
        assertThrows(InsufficientPointsException.class,()-> pointsManager.removePoints(account,transaction));
        assertEquals(10, account.getPoints());
    }

    @Test
    public void addPointsTest(){
        account=memberFinder.findByMail("John.Doe@mail.com").orElse(null);
        account.setPoints(100);
        Product product3=new Product("ring",1.0,10,0.0);
        Purchase transaction=new Purchase(LocalDate.now(),account,List.of(new Item(product3,2)));
        assertDoesNotThrow(()-> pointsManager.addPoints(account,transaction));
        assertEquals(120, account.getPoints());
    }

}
