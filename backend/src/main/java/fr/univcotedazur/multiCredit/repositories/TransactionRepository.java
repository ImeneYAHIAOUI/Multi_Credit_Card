package fr.univcotedazur.multiCredit.repositories;

import fr.univcotedazur.multiCredit.entities.MemberAccount;
import fr.univcotedazur.multiCredit.entities.Transaction;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT o FROM Transaction o WHERE o.memberAccount = :member ")
    List<Transaction> findMemberTransactions(@Param("member") MemberAccount member, Sort sort);
}
