package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.MemberAccount;
import fr.univcotedazur.simpletcfs.entities.Shop;
import fr.univcotedazur.simpletcfs.entities.ShopKeeperAccount;
import fr.univcotedazur.simpletcfs.entities.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionExplorer {
     Optional<Transaction> findTransactionById(Long id);
     List<Transaction> findAllTransactions();

     List<Transaction> getStatisticsOnClientUsage(MemberAccount memberAccount);
     List<Transaction> getStatisticsOnClientUsageAtShop(Shop shop, MemberAccount memberAccount);
}
