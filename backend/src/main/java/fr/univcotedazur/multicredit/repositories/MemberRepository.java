package fr.univcotedazur.multicredit.repositories;

import fr.univcotedazur.multicredit.entities.MemberAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberAccount, Long> {
}
