package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.ShopKeeperAccount;
import fr.univcotedazur.simpletcfs.entities.Transaction;

import java.util.Optional;
import java.util.UUID;

public interface TransactionExplorer {
    public Optional<Transaction> findTransactionById(Long id);

}
