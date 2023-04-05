package fr.univcotedazur.multiCredit.repositories;

import fr.univcotedazur.multiCredit.entities.ShopKeeperAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopKeeperAccountRepository extends JpaRepository<ShopKeeperAccount, Long> {
}

