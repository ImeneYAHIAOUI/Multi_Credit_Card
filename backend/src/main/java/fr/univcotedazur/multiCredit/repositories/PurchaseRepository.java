package fr.univcotedazur.multiCredit.repositories;

import fr.univcotedazur.multiCredit.entities.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long>{

}
