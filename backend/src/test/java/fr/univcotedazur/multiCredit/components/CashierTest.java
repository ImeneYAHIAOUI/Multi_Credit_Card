package fr.univcotedazur.multiCredit.components;

import fr.univcotedazur.multiCredit.entities.*;
import fr.univcotedazur.multiCredit.exceptions.PaymentException;
import fr.univcotedazur.multiCredit.interfaces.Bank;
import fr.univcotedazur.multiCredit.interfaces.MemberFinder;
import fr.univcotedazur.multiCredit.interfaces.MemberHandler;
import fr.univcotedazur.multiCredit.interfaces.Payment;
import fr.univcotedazur.multiCredit.repositories.MemberRepository;
import fr.univcotedazur.multiCredit.repositories.PurchaseRepository;
import fr.univcotedazur.multiCredit.repositories.TransactionRepository;
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
    @Autowired
    PurchaseRepository purchaseRepository;
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
        when(bankMock.pay(eq("12345679994123456"), anyDouble())).thenReturn(true);
        when(bankMock.pay(eq("1234567999123456"), anyDouble())).thenReturn(false);
    }

    @Test
     void processToPayment() throws Exception {
        // paying order
        cashier.payment(purchaseOfJohn, "12345679994123456");
        assertNotNull(purchaseOfJohn.getDate());
    }
    @Test
     void processToPayment1()  {
        Assertions.assertThrows(PaymentException.class, () -> {
            cashier.payment(purchaseOfJohn, "1234567999123456");
        });
    }
    @Test
     void processToPayment2() {

        Assertions.assertThrows(PaymentException.class, () -> {
            cashier.payment(purchaseOfJohn, null);
        });
    }
    @Test
     void identifyPaymentError() {
        Assertions.assertThrows(PaymentException.class, () -> {
            cashier.payment(purchaseOfPat, "1234567999123456");
        });
    }
}