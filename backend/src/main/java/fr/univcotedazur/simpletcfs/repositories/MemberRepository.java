package fr.univcotedazur.simpletcfs.repositories;

import fr.univcotedazur.simpletcfs.entities.MemberAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberAccount, Long> {
}
