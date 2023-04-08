package fr.univcotedazur.multiCredit.repositories;

import fr.univcotedazur.multiCredit.entities.MemberAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberAccount, Long> {
}
