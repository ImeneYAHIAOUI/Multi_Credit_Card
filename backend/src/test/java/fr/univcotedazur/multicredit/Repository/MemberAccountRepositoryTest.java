package fr.univcotedazur.multicredit.Repository;

import fr.univcotedazur.multicredit.entities.MemberAccount;
import fr.univcotedazur.multicredit.repositories.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class MemberAccountRepositoryTest {

    @Autowired
    MemberRepository memberAccountRepository;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
    MemberAccount memberAccount;

    @BeforeEach
    void setup() {
        memberAccountRepository.deleteAll();
        memberAccount = new MemberAccount("John Doe", "John.Doe@mail.com", "password", LocalDate.parse("11/04/2001", formatter), 0, 0);
    }

    @Test
    void testSaveAndFind() {
        memberAccountRepository.save(memberAccount);
        Optional<MemberAccount> memberAccount2 = memberAccountRepository.findById(memberAccount.getId());
        assertTrue(memberAccount2.isPresent());
        assertEquals(memberAccount.getId(), memberAccount2.get().getId());
    }

    @Test
    void testDeleteAll() {
        assertEquals(0, memberAccountRepository.count());
        memberAccountRepository.save(memberAccount);
        assertEquals(1, memberAccountRepository.count());
        memberAccountRepository.deleteAll();
        assertEquals(0, memberAccountRepository.count());
    }

    @Test
    void testDeleteOneAccount() {
        memberAccount = memberAccountRepository.save(memberAccount);
        MemberAccount memberAccount2 = new MemberAccount("John Doe2", "John.Doe2@mail.com", "password", LocalDate.parse("11/04/2001", formatter), 0, 0);
        memberAccountRepository.save(memberAccount2);
        assertEquals(2, memberAccountRepository.count());
        memberAccountRepository.deleteById(memberAccount.getId());
        assertEquals(1, memberAccountRepository.count());
    }
}
