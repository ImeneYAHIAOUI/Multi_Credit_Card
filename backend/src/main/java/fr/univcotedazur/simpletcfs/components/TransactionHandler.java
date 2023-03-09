package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.entities.*;
import fr.univcotedazur.simpletcfs.exceptions.AccountNotFoundException;
import fr.univcotedazur.simpletcfs.exceptions.DeclinedTransactionException;
import fr.univcotedazur.simpletcfs.exceptions.InsufficientPointsException;
import fr.univcotedazur.simpletcfs.exceptions.PaymentException;
import fr.univcotedazur.simpletcfs.interfaces.*;
import fr.univcotedazur.simpletcfs.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.StreamSupport;

@Component
public class TransactionHandler implements TransactionProcessor, TransactionExplorer {
    private final TransactionRepository transactionRepository;
    private final PointTrader pointTrader;
    private Payment payment;

    private final MemberFinder memberFinder;
    @Autowired
    public TransactionHandler(TransactionRepository transactionRepository, PointTrader pointTrader, Payment payment, MemberFinder memberFinder) {
        this.transactionRepository = transactionRepository;
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
            transactionRepository.save(purchase);
        }
    }
    public void processPointsUsage(MemberAccount memberAccount,UsePoints usePoint)throws DeclinedTransactionException, InsufficientPointsException ,AccountNotFoundException{
        if(memberAccount.getId() == null || memberFinder.findById(memberAccount.getId()).isEmpty()) throw new AccountNotFoundException();
        else{
            if(memberAccount.getStatus()!=usePoint.getGift().RequiredStatus||
                    transactionRepository.findAll().stream().
                            filter(transaction -> transaction.getMemberAccount().getId().equals(memberAccount.getId()))
                            .noneMatch(transaction -> transaction instanceof Purchase))
                 {
                throw new DeclinedTransactionException();
            }else{
                pointTrader.removePoints(memberAccount,usePoint);
                transactionRepository.save(usePoint);
            }

        }
    }
}
