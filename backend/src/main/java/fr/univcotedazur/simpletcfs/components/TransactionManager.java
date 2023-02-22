package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.entities.*;
import fr.univcotedazur.simpletcfs.exceptions.InsufficientPointsException;
import fr.univcotedazur.simpletcfs.exceptions.PaymentException;
import fr.univcotedazur.simpletcfs.interfaces.Payment;
import fr.univcotedazur.simpletcfs.interfaces.PointTrader;
import fr.univcotedazur.simpletcfs.interfaces.TransactionProcessor;
import fr.univcotedazur.simpletcfs.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TransactionManager implements TransactionProcessor {
    private TransactionRepository transactionRepository;
    private PointTrader pointTrader;
    private Payment payment;
    @Autowired
    public TransactionManager(TransactionRepository transactionRepository, PointTrader pointTrader, Payment payment) {
        this.transactionRepository = transactionRepository;
        this.payment=payment;
        this.pointTrader=pointTrader;
    }
    public void processPurchase(MemberAccount memberAccount, Purchase purchase, CreditCard card) throws PaymentException{
            payment.payment(purchase,card);
            pointTrader.addPoints(memberAccount,purchase);
            memberAccount.addTransaction(purchase);
            transactionRepository.save(purchase, UUID.randomUUID());
    }
    public void processPointsUsage(MemberAccount memberAccount,UsePoints usePoint)throws InsufficientPointsException {
        pointTrader.removePoints(memberAccount,usePoint);
        memberAccount.addTransaction(usePoint);
        transactionRepository.save(usePoint, UUID.randomUUID());


    }
}
