package fr.univcotedazur.multiCredit.repositories;

import fr.univcotedazur.multiCredit.entities.AdminAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminAccountRepository extends JpaRepository<AdminAccount, Long> {

}
