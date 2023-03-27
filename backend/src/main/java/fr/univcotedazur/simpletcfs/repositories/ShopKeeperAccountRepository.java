package fr.univcotedazur.simpletcfs.repositories;

import fr.univcotedazur.repositories.BasicRepositoryImpl;
import fr.univcotedazur.simpletcfs.entities.AdminAccount;
import fr.univcotedazur.simpletcfs.entities.ShopKeeperAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ShopKeeperAccountRepository extends JpaRepository<ShopKeeperAccount, Long> {
}

