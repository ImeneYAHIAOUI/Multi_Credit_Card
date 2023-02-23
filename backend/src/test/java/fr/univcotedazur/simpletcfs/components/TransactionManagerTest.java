package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.entities.MemberAccount;
import fr.univcotedazur.simpletcfs.entities.Purchase;
import fr.univcotedazur.simpletcfs.entities.UsePoints;
import fr.univcotedazur.simpletcfs.exceptions.*;
import fr.univcotedazur.simpletcfs.interfaces.MemberFinder;
import fr.univcotedazur.simpletcfs.interfaces.MemberHandler;
import fr.univcotedazur.simpletcfs.repositories.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TransactionManagerTest {
    @Autowired
    MemberFinder memberFinder;
    MemberAccount account;
    @Autowired
    TransactionManager transactionManager;
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
    public void processPointsUsageTest() throws AlreadyExistingMemberException, MissingInformationException, UnderAgeException{
        setUp("John.Doe@mail.com","John");
        account.setPoints(100);
        UsePoints transaction=new UsePoints();
        transaction.setUseddPoints(50);
        assertDoesNotThrow(()-> transactionManager.processPointsUsage(account,transaction));
        assertEquals(50, account.getPoints());
        assertTrue(transactionRepository.existsById(transaction.getId()));
        assertTrue(transactionRepository.findById(transaction.getId()).isPresent());
    }
    @Test
    public void processPointsUsageTest1()throws AlreadyExistingMemberException, MissingInformationException, UnderAgeException{
        setUp("joel.Doe@mail.com","joel");
        account.setPoints(10);
        UsePoints transaction=new UsePoints();
        transaction.setUseddPoints(100);
        assertThrows(InsufficientPointsException.class,()-> transactionManager.processPointsUsage(account,transaction));
        assertEquals(10, account.getPoints());
        assertFalse(transactionRepository.existsById(transaction.getId()));
        assertTrue(transactionRepository.findById(transaction.getId()).isEmpty());
    }
    @Test
    public void processPointsUsageTest2(){
        UsePoints transaction=new UsePoints();
        transaction.setUseddPoints(100);
        assertThrows(AccountNotFoundException.class,()-> transactionManager.processPointsUsage(account,transaction));
        assertEquals(transaction.getId(),null);
    }
}
