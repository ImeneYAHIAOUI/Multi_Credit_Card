package fr.univcotedazur.simpletcfs.repositories;

import fr.univcotedazur.repositories.BasicRepositoryImpl;
import fr.univcotedazur.simpletcfs.entities.ShopKeeperAccount;
import fr.univcotedazur.simpletcfs.entities.Transaction;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class TransactionRepository extends BasicRepositoryImpl<Transaction, UUID> {
}
