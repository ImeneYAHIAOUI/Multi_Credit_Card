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

    private final MemberFinder memberFinder;
    @Autowired
    public TransactionManager(TransactionRepository transactionRepository, PointTrader pointTrader, Payment payment, MemberFinder memberFinder) {
        this.transactionRepository = transactionRepository;
        this.payment=payment;
        this.pointTrader=pointTrader;
        this.memberFinder = memberFinder;
    }

    public Optional<Transaction> findTransactionById(UUID id){
        return transactionRepository.findById(id);
    }
    public void processPurchase(MemberAccount memberAccount, Purchase purchase, CreditCard card) throws PaymentException, AccountNotFoundException{
        if(memberFinder.findMember(memberAccount.getId()) == null) throw new AccountNotFoundException();
        else{
            payment.payment(purchase,card);
            purchase.setMemberAccount(memberAccount);
            purchase.setDate(LocalDate.now());
            pointTrader.addPoints(memberAccount,purchase);
            memberAccount.addTransaction(purchase);
            UUID id=UUID.randomUUID();
            purchase.setId(id);
            transactionRepository.save(purchase,id);
        }
    }
    public void processPointsUsage(MemberAccount memberAccount,UsePoints usePoint)throws DeclinedTransactionException, InsufficientPointsException ,AccountNotFoundException{
        if(memberFinder.findMember(memberAccount.getId()) == null) throw new AccountNotFoundException();
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
