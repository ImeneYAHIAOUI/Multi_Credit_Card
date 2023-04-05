package fr.univcotedazur.multiCredit.interfaces;

import fr.univcotedazur.multiCredit.entities.MemberAccount;
import fr.univcotedazur.multiCredit.entities.Shop;
import fr.univcotedazur.multiCredit.entities.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionExplorer {
     Optional<Transaction> findTransactionById(Long id);
     List<Transaction> findAllTransactions();

     List<Transaction> getStatisticsOnClientUsage(MemberAccount memberAccount);
     List<Transaction> getStatisticsOnClientUsageAtShop(Shop shop, MemberAccount memberAccount);
}
