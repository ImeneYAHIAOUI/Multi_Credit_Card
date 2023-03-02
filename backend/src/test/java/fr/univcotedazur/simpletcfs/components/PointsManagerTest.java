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
import java.util.ArrayList;
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
        assertNull(memberFinder.findByMail(mail));
        account = memberHandler.createAccount(name, mail, "password", LocalDate.parse("11/04/2001", formatter));
        assertNotNull(memberFinder.findMember(account.getId()));
    }
    @Test
    public void removePointsTest()throws AlreadyExistingMemberException, MissingInformationException, UnderAgeException{
        setUp("John.Doe@mail.com","John");
        account.setPoints(100);
        UsePoints transaction=new UsePoints();
        transaction.setUsedPoints(50);
        assertDoesNotThrow(()-> pointsManager.removePoints(account,transaction));
        assertEquals(50, account.getPoints());
    }
    @Test
    public void removePointsTest1(){
        account=memberFinder.findByMail("John.Doe@mail.com");
        account.setPoints(10);
        UsePoints transaction=new UsePoints();
        transaction.setUsedPoints(50);
        assertThrows(InsufficientPointsException.class,()-> pointsManager.removePoints(account,transaction));
        assertEquals(10, account.getPoints());
    }

    @Test
    public void addPointsTest(){
        account=memberFinder.findByMail("John.Doe@mail.com");
        account.setPoints(100);
        Product product3=new Product(UUID.randomUUID(),"ring",1.0,10);
        Purchase transaction=new Purchase(List.of(new Item(product3,2)));
        assertDoesNotThrow(()-> pointsManager.addPoints(account,transaction));
        assertEquals(120, account.getPoints());
    }

}
