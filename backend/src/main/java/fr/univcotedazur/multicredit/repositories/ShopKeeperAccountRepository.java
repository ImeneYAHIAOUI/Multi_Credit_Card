package fr.univcotedazur.multicredit.repositories;

import fr.univcotedazur.multicredit.entities.ShopKeeperAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopKeeperAccountRepository extends JpaRepository<ShopKeeperAccount, Long> {
}
