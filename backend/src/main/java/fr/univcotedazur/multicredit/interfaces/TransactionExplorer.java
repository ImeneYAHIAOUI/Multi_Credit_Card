package fr.univcotedazur.multicredit.interfaces;

import fr.univcotedazur.multicredit.entities.MemberAccount;
import fr.univcotedazur.multicredit.entities.Shop;
import fr.univcotedazur.multicredit.entities.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionExplorer {
    Optional<Transaction> findTransactionById(Long id);

    List<Transaction> findAllTransactions();

    List<Transaction> getStatisticsOnClientUsage(MemberAccount memberAccount);

    List<Transaction> getStatisticsOnClientUsageAtShop(Shop shop, MemberAccount memberAccount);
}
