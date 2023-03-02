package fr.univcotedazur.simpletcfs.Repository;

import fr.univcotedazur.simpletcfs.entities.MemberAccount;
import fr.univcotedazur.simpletcfs.repositories.MemberAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class MemberAccountRepositoryTest {

    @Autowired
    MemberAccountRepository memberAccountRepository;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
    MemberAccount memberAccount;
    @BeforeEach
    void setup() {
        memberAccountRepository.deleteAll();
        UUID id = UUID.randomUUID();
        memberAccount = new MemberAccount(id,"John Doe", "John.Doe@mail.com", "password", LocalDate.parse("11/04/2001", formatter),0,0);
    }

    @Test
    void testSaveAndFind() {
        memberAccountRepository.save(memberAccount,memberAccount.getId());
        Optional<MemberAccount> memberAccount2 = memberAccountRepository.findById(memberAccount.getId());
        assertTrue(memberAccount2.isPresent());
        assertEquals(memberAccount, memberAccount2.get());
    }

    @Test
    void testDeleteAll() {
        assertEquals(0, memberAccountRepository.count());
        memberAccountRepository.save(memberAccount,memberAccount.getId());
        assertEquals(1, memberAccountRepository.count());
        memberAccountRepository.deleteAll();
        assertEquals(0, memberAccountRepository.count());
    }

    @Test
    void testDeleteOneAccount() {
        memberAccountRepository.save(memberAccount,memberAccount.getId());
        MemberAccount memberAccount2 = new MemberAccount(UUID.randomUUID(),"John Doe2", "John.Doe2@mail.com", "password", LocalDate.parse("11/04/2001", formatter),0,0);
        memberAccountRepository.save(memberAccount2,memberAccount2.getId());
        assertEquals(2, memberAccountRepository.count());
        memberAccountRepository.deleteById(memberAccount.getId());
        assertEquals(1, memberAccountRepository.count());
    }


}
