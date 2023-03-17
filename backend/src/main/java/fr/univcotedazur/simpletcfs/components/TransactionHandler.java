package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.entities.*;
import fr.univcotedazur.simpletcfs.exceptions.AccountNotFoundException;
import fr.univcotedazur.simpletcfs.exceptions.DeclinedTransactionException;
import fr.univcotedazur.simpletcfs.exceptions.InsufficientPointsException;
import fr.univcotedazur.simpletcfs.exceptions.PaymentException;
import fr.univcotedazur.simpletcfs.interfaces.*;
import fr.univcotedazur.simpletcfs.repositories.PurchaseRepository;
import fr.univcotedazur.simpletcfs.repositories.TransactionRepository;
import fr.univcotedazur.simpletcfs.repositories.UsePointsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.StreamSupport;

@Component
public class TransactionHandler implements TransactionProcessor, TransactionExplorer {
    private final TransactionRepository transactionRepository;
    private PurchaseRepository purchaseRepository;
    private UsePointsRepository usePointsRepository;
    private final PointTrader pointTrader;
    private Payment payment;

    private final MemberFinder memberFinder;
    @Autowired
    public TransactionHandler(TransactionRepository transactionRepository,
                              PointTrader pointTrader, Payment payment,
                              MemberFinder memberFinder,
                              PurchaseRepository p, UsePointsRepository u) {
        this.transactionRepository = transactionRepository;
        this.purchaseRepository=p;
        this.usePointsRepository=u;
        this.payment=payment;
        this.pointTrader=pointTrader;
        this.memberFinder = memberFinder;
    }

    public Optional<Transaction> findTransactionById(Long id){
        return transactionRepository.findById(id);
    }
    public void processPurchase(MemberAccount memberAccount, Purchase purchase, String card) throws PaymentException, AccountNotFoundException{
        if(memberAccount.getId() == null || memberFinder.findById(memberAccount.getId()).isEmpty()) throw new AccountNotFoundException();
        else{
            payment.payment(purchase,card);
            purchase.setMemberAccount(memberAccount);
            purchase.setDate(LocalDate.now());
            pointTrader.addPoints(memberAccount,purchase);
            memberAccount.getTransactions().add(purchase);
            purchaseRepository.save(purchase);

        }
    }
    public void processPointsUsage(MemberAccount memberAccount,UsePoints usePoint)throws DeclinedTransactionException, InsufficientPointsException ,AccountNotFoundException{
        if(memberAccount.getId() == null || memberFinder.findById(memberAccount.getId()).isEmpty()) throw new AccountNotFoundException();
        else{
            if(memberAccount.getStatus()!=usePoint.getGift().getRequiredStatus()||
                    memberAccount.getTransactions().stream().
                            noneMatch(transaction -> transaction instanceof Purchase &&
                                  transaction.getShop().equals(usePoint.getGift().getShop()))
                            )
                 {
                throw new DeclinedTransactionException();
            }else{
                pointTrader.removePoints(memberAccount,usePoint);
                memberAccount.getTransactions().add(usePoint);
                usePointsRepository.save(usePoint);
            }

        }
    }
}
