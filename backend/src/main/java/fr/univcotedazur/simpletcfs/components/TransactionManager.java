package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.entities.*;
import fr.univcotedazur.simpletcfs.exceptions.AccountNotFoundException;
import fr.univcotedazur.simpletcfs.exceptions.DeclinedTransactionException;
import fr.univcotedazur.simpletcfs.exceptions.InsufficientPointsException;
import fr.univcotedazur.simpletcfs.exceptions.PaymentException;
import fr.univcotedazur.simpletcfs.interfaces.Payment;
import fr.univcotedazur.simpletcfs.interfaces.PointTrader;
import fr.univcotedazur.simpletcfs.interfaces.TransactionExplorer;
import fr.univcotedazur.simpletcfs.interfaces.TransactionProcessor;
import fr.univcotedazur.simpletcfs.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class TransactionManager implements TransactionProcessor, TransactionExplorer {
    private TransactionRepository transactionRepository;
    private PointTrader pointTrader;
    private Payment payment;
    @Autowired
    public TransactionManager(TransactionRepository transactionRepository, PointTrader pointTrader, Payment payment) {
        this.transactionRepository = transactionRepository;
        this.payment=payment;
        this.pointTrader=pointTrader;
    }

    public Optional<Transaction> findTransactionById(UUID id){
        return transactionRepository.findById(id);
    }
    public void processPurchase(MemberAccount memberAccount, Purchase purchase, CreditCard card) throws PaymentException, AccountNotFoundException{
        if(memberAccount==null)
            throw new AccountNotFoundException();
        else{
            payment.payment(purchase,card);
            purchase.setMemberAccount(memberAccount);
            long millis = System.currentTimeMillis();
            purchase.setDate( new java.util.Date(millis));
            pointTrader.addPoints(memberAccount,purchase);
            memberAccount.addTransaction(purchase);
            transactionRepository.save(purchase, UUID.randomUUID());
        }
    }
    public void processPointsUsage(MemberAccount memberAccount,UsePoints usePoint)throws DeclinedTransactionException, InsufficientPointsException ,AccountNotFoundException{
        if(memberAccount==null)
            throw new AccountNotFoundException();
        else{
            if(memberAccount.getStatus()!=usePoint.getGift().RequiredStatus||
                    StreamSupport.stream(transactionRepository.findAll().spliterator(), false)
                            .filter(transaction -> transaction.getMemberAccount().equals(memberAccount))
                            .noneMatch(transaction -> transaction instanceof Purchase)){
                throw new DeclinedTransactionException();
            }else{
                pointTrader.removePoints(memberAccount,usePoint);
                memberAccount.addTransaction(usePoint);
                UUID id=UUID.randomUUID();
                usePoint.setId(id);
                transactionRepository.save(usePoint, id);
            }

        }
    }
}
