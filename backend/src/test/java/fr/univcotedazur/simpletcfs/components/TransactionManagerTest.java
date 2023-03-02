package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.entities.*;
import fr.univcotedazur.simpletcfs.exceptions.*;
import fr.univcotedazur.simpletcfs.interfaces.Bank;
import fr.univcotedazur.simpletcfs.interfaces.MemberFinder;
import fr.univcotedazur.simpletcfs.interfaces.MemberHandler;
import fr.univcotedazur.simpletcfs.repositories.MemberAccountRepository;
import fr.univcotedazur.simpletcfs.repositories.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TransactionManagerTest {
    @Autowired
    MemberFinder memberFinder;
    @MockBean
    private Bank bankMock;
    MemberAccount account;
    @Autowired
    TransactionManager transactionManager;
    @Autowired
    TransactionRepository transactionRepository;
    CreditCard creditCardOfJohn;
    CreditCard creditCardOfPat;
    Purchase purchaseOfJohn;
    MemberAccount john;
    MemberAccount pat;
    Purchase purchaseOfPat;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
    @Autowired
    MemberAccountRepository memberAccoutRepository;

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
        assertThrows(AccountNotFoundException.class,()-> transactionManager.processPointsUsage(account,transaction));
        assertEquals(transaction.getId(),null);
    }
    @Test
    public void processPurchaseTest()throws Exception{
        transactionRepository.deleteAll();
        memberAccoutRepository.deleteAll();

        Product product3=new Product(UUID.randomUUID(),"ring",1.0,10);
        purchaseOfJohn=new Purchase(List.of(new Item(product3,2)));
        assertNull(memberFinder.findByMail("john.d@gmail.com"));
        john = memberHandler.createAccount("john", "john.d@gmail.com", "password", LocalDate.parse("11/04/2001", formatter));
        assertNotNull(memberFinder.findMember(john.getId()));
        assertEquals(0, john.getPoints());
        assertFalse(john.getTransactions().contains(purchaseOfJohn));
        creditCardOfJohn=new CreditCard("1234567890123456","John", LocalDate.parse("11/04/2025", formatter),"123");
        // Mocking the bank proxy
        when(bankMock.pay(eq(creditCardOfJohn), anyDouble())).thenReturn(true);
        transactionManager.processPurchase(john, purchaseOfJohn,creditCardOfJohn);
        assertEquals(purchaseOfJohn.getEarnedPoints(), john.getPoints());
        assertTrue(john.getTransactions().contains(purchaseOfJohn));
        assertTrue(transactionRepository.existsById(purchaseOfJohn.getId()));
    }
    @Test
    public void processPurchaseTest1()throws Exception{
        transactionRepository.deleteAll();
        memberAccoutRepository.deleteAll();
        Product product=new Product(UUID.randomUUID(),"cake",1.0,10);
        purchaseOfPat=new Purchase(List.of(new Item(product,5)));
        pat = memberHandler.createAccount("pat", "pat.d@gmail.com", "password", LocalDate.parse("11/04/2001", formatter));
        assertNotNull(memberFinder.findMember(pat.getId()));
        creditCardOfPat=new CreditCard("1234567999123456","Pat", LocalDate.parse("11/04/2028", formatter),"123");
        // Mocking the bank proxy
        when(bankMock.pay(eq(creditCardOfPat), anyDouble())).thenReturn(false);
        assertThrows(PaymentException.class,()->transactionManager.processPurchase(pat, purchaseOfPat,creditCardOfPat));
        assertFalse(transactionRepository.existsById(purchaseOfPat.getId()));
        assertEquals(0, pat.getPoints());
        assertNotEquals(purchaseOfPat.getEarnedPoints(), pat.getPoints());
        assertFalse(pat.getTransactions().contains(purchaseOfPat));
    }
    @Test
    public void processPurchaseTest2(){
        assertThrows(AccountNotFoundException.class,()->transactionManager.processPurchase(null, purchaseOfPat,creditCardOfPat));
    }
}
