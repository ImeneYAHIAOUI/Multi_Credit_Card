package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.entities.*;
import fr.univcotedazur.simpletcfs.exceptions.PaymentException;
import fr.univcotedazur.simpletcfs.interfaces.Bank;
import fr.univcotedazur.simpletcfs.interfaces.MemberFinder;
import fr.univcotedazur.simpletcfs.interfaces.MemberHandler;
import fr.univcotedazur.simpletcfs.interfaces.Payment;
import fr.univcotedazur.simpletcfs.repositories.MemberRepository;
import fr.univcotedazur.simpletcfs.repositories.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.hibernate.validator.internal.util.Contracts.assertNotEmpty;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
class CashierTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    private Payment cashier;

    @MockBean
    private Bank bankMock;
    @Autowired
    MemberHandler memberHandler;
    // Test context
    MemberAccount john;
    MemberAccount pat;

    @Autowired
    MemberFinder memberFinder;
    MemberAccount account;
    @Autowired
    TransactionHandler transactionHandler;
    @Autowired
    TransactionRepository transactionRepository;
Purchase purchaseOfJohn;

    Purchase purchaseOfPat;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

    @BeforeEach
    public void setUp() throws Exception {
        memberRepository.deleteAll();
        Product product3=new Product("ring",1.0,10,0.0);
        purchaseOfJohn=new Purchase(LocalDate.now(),account,List.of(new Item(product3,2)));

        Product product=new Product("cake",1.0,10,0.0);
        purchaseOfPat=new Purchase(LocalDate.now(),account,List.of(new Item(product,5)));
        assertNull(memberFinder.findByMail("john.d@gmail.com").orElse(null));

        john = memberHandler.createAccount("john", "john.d@gmail.com", "password", LocalDate.parse("11/04/2001", formatter));
        assertNotNull(Objects.requireNonNull(memberFinder.findById(john.getId()).orElse(null)));
        pat = memberHandler.createAccount("pat", "pat.d@gmail.com", "password", LocalDate.parse("11/04/2001", formatter));
        assertNotNull(Objects.requireNonNull(memberFinder.findById(pat.getId()).orElse(null)));

        // Mocking the bank proxy
        when(bankMock.pay(eq("1234567999123456"), anyDouble())).thenReturn(true);
        when(bankMock.pay(eq("1234567999123456"), anyDouble())).thenReturn(false);
    }

    @Test
    public void processToPayment() throws Exception {
        // paying order
        //cashier.payment(purchaseOfJohn, "1234567999123456");
        //assertNotNull(transactionRepository.existsById(purchaseOfJohn.getId()));
    }
    @Test
    public void processToPayment1()  {
        //creditCardOfJohn.setExpirationDate(LocalDate.parse("11/04/2000", formatter));
        Assertions.assertThrows(PaymentException.class, () -> {
            cashier.payment(purchaseOfJohn, "1234567999123456");
        });
    }
    @Test
    public void processToPayment2() {

        Assertions.assertThrows(PaymentException.class, () -> {
            cashier.payment(purchaseOfJohn, null);
        });
    }
    @Test
    public void identifyPaymentError() {
        Assertions.assertThrows(PaymentException.class, () -> {
            cashier.payment(purchaseOfPat, "1234567999123456");
        });
    }
}