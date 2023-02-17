package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.entities.MemberAccount;
import fr.univcotedazur.simpletcfs.exceptions.AccountNotFoundException;
import fr.univcotedazur.simpletcfs.exceptions.AlreadyExistingMemberException;
import fr.univcotedazur.simpletcfs.exceptions.MissingInformationException;
import fr.univcotedazur.simpletcfs.exceptions.UnderAgeException;
import fr.univcotedazur.simpletcfs.interfaces.MemberFinder;
import fr.univcotedazur.simpletcfs.interfaces.MemberHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MemberManagerTests {

    @Autowired
    MemberHandler memberHandler;
    @Autowired
    MemberFinder memberFinder;

    @Test
    public void testCreateAccount() throws AlreadyExistingMemberException, MissingInformationException, UnderAgeException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        assertNull(memberFinder.findByMail("John.Doe@mail.com"));
        MemberAccount account = memberHandler.createAccount("John Doe", "John.Doe@mail.com", "password", LocalDate.parse("11/04/2001", formatter));
        assertNotNull(memberFinder.findMember(account.getId()));
        assertThrows(AlreadyExistingMemberException.class, () -> memberHandler.createAccount("John Doe", "John.Doe@mail.com", "password", LocalDate.parse("11/04/2001", formatter)));
        assertThrows(MissingInformationException.class, () -> memberHandler.createAccount(null, "John.Doe2@mail.com", "password", LocalDate.parse("11/04/2001", formatter)));
        assertThrows(MissingInformationException.class, () -> memberHandler.createAccount("John Doe", null, "password", LocalDate.parse("11/04/2001", formatter)));
        assertThrows(MissingInformationException.class, () -> memberHandler.createAccount("John Doe", "John2.Doe@mail.com", null, LocalDate.parse("11/04/2001", formatter)));
        assertThrows(MissingInformationException.class, () -> memberHandler.createAccount("John Doe", "John2.Doe@mail.com", "password", null));
        assertThrows(UnderAgeException.class, () -> memberHandler.createAccount("John Doe", "John2.Doe@mail.com", "password", LocalDate.parse("11/04/2010", formatter)));
    }

    @Test
    public void testDeleteAccount() throws AlreadyExistingMemberException, MissingInformationException, UnderAgeException, AccountNotFoundException, AccountNotFoundException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        MemberAccount account = memberHandler.createAccount("John Doe", "John.Doe@mail.com", "password", LocalDate.parse("11/04/2001", formatter));
        assertNotNull(memberFinder.findMember(account.getId()));
        memberHandler.deleteAccount(account);
        assertNull(memberFinder.findMember(account.getId()));
    }

    }
