package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.entities.*;
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
import java.util.List;
import java.util.UUID;

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
        Gift gift=new Gift();
        gift.setRequiredStatus(AccountStatus.VFP);
        transaction.setGift(gift);
        transaction.setUsedPoints(50);
        account.setStatus(AccountStatus.VFP);
        Product product3=new Product(UUID.randomUUID(),"ring",1.0,10);
        Purchase tran=new Purchase(List.of(new Item(product3,2)));
        tran.setMemberAccount(account);
        transactionRepository.save(tran,UUID.randomUUID());
        assertDoesNotThrow(()-> transactionManager.processPointsUsage(account,transaction));
        assertEquals(50, account.getPoints());
        assertTrue(transactionRepository.existsById(transaction.getId()));
        assertTrue(transactionRepository.findById(transaction.getId()).isPresent());

    }
    @Test
    public void processPointsUsageTest1()throws AlreadyExistingMemberException, MissingInformationException, UnderAgeException{
        transactionRepository.deleteAll();
         setUp("joel.Doe@mail.com","joel");
        account.setPoints(10);
        UsePoints transaction=new UsePoints();
        transaction.setMemberAccount(account);
        Gift gift=new Gift();
        gift.setRequiredStatus(AccountStatus.VFP);
        transaction.setGift(gift);
        transaction.setUsedPoints(100);
        account.setStatus(AccountStatus.VFP);
        Product product3=new Product(UUID.randomUUID(),"ring",1.0,10);
        Purchase tran=new Purchase(List.of(new Item(product3,2)));
        tran.setMemberAccount(account);
        transactionRepository.save(tran,UUID.randomUUID());
        assertThrows(InsufficientPointsException.class,()-> transactionManager.processPointsUsage(account,transaction));
        assertEquals(10, account.getPoints());
        assertFalse(transactionRepository.existsById(transaction.getId()));
        assertTrue(transactionRepository.findById(transaction.getId()).isEmpty());
    }
    @Test
    public void processPointsUsageTest2()throws AlreadyExistingMemberException, MissingInformationException, UnderAgeException{
        transactionRepository.deleteAll();
        setUp("sourour.Doe@mail.com","joel");
        account.setPoints(10);
        UsePoints transaction=new UsePoints();
        transaction.setMemberAccount(account);
        Gift gift=new Gift();
        gift.setRequiredStatus(AccountStatus.VFP);
        transaction.setGift(gift);
        transaction.setUsedPoints(100);
        account.setStatus(AccountStatus.VFP);
        assertThrows(DeclinedTransactionException.class,()-> transactionManager.processPointsUsage(account,transaction));
        assertEquals(10, account.getPoints());
        assertFalse(transactionRepository.existsById(transaction.getId()));
        assertTrue(transactionRepository.findById(transaction.getId()).isEmpty());
    }

    @Test
    public void processPointsUsageTest3(){
        UsePoints transaction=new UsePoints();
        transaction.setUsedPoints(100);
        account = new MemberAccount(UUID.randomUUID(),"john","mail","pass",LocalDate.of(2001,11,04),0,0);
        assertThrows(AccountNotFoundException.class,()-> transactionManager.processPointsUsage(account,transaction));
        assertEquals(transaction.getId(),null);
    }
}
