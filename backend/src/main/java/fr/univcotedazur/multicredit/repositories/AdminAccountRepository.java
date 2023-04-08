package fr.univcotedazur.multicredit.repositories;

import fr.univcotedazur.multicredit.entities.AdminAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminAccountRepository extends JpaRepository<AdminAccount, Long> {
}
