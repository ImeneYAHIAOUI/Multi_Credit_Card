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
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component
public class TransactionHandler implements TransactionProcessor, TransactionExplorer,PointTrader {
    private final TransactionRepository transactionRepository;
    private PurchaseRepository purchaseRepository;
    private UsePointsRepository usePointsRepository;
    private Payment payment;
    private final Environment env;
    private final MemberHandler memberHandler;
    private final MemberFinder memberFinder;
    @Autowired
    public TransactionHandler(TransactionRepository transactionRepository,
                             Payment payment,
                              MemberFinder memberFinder,
                              PurchaseRepository p, UsePointsRepository u, Environment env, MemberHandler memberHandler) {
        this.transactionRepository = transactionRepository;
        this.purchaseRepository=p;
        this.usePointsRepository=u;
        this.payment=payment;
        this.memberFinder = memberFinder;
        this.env = env;
        this.memberHandler = memberHandler;
    }

    public Optional<Transaction> findTransactionById(Long id){
        return transactionRepository.findById(id);
    }

    @Override
    public List<Transaction> findAllTransactions() {
        return transactionRepository.findAll();
    }
    
     public void removePoints(MemberAccount memberAccount, UsePoints usePoints) throws InsufficientPointsException{
        if(memberAccount.getPoints()>=usePoints.getUsedPoints())
            memberAccount.setPoints(memberAccount.getPoints()-usePoints.getUsedPoints());
        else throw new InsufficientPointsException();
    }
    public void addPoints(MemberAccount memberAccount, Purchase purchase) {
        memberAccount.setPoints(memberAccount.getPoints()+ purchase.getEarnedPoints());
    }

    @Override
    public Purchase processPurchaseWithCreditCard(MemberAccount memberAccount, Purchase purchase, String card) throws PaymentException, AccountNotFoundException{

        if(memberAccount.getId() == null || memberFinder.findById(memberAccount.getId()).isEmpty()) throw new AccountNotFoundException();
        else{
            payment.payment(purchase,card);
            purchase.setMemberAccount(memberAccount);
            purchase.setPaymentMethod(PaymentMethod.CREDIT_CARD);
            return registerPurchase(memberAccount, purchase);
        }
    }

    @Override
    public Purchase processPurchaseWithMemberCard(MemberAccount memberAccount, Purchase purchase) throws PaymentException, AccountNotFoundException{
        if(memberAccount.getId() == null || memberFinder.findById(memberAccount.getId()).isEmpty()) throw new AccountNotFoundException();
        else{
            if(memberAccount.getBalance()<purchase.getTotalPrice()) throw new PaymentException();
            memberAccount.setBalance(memberAccount.getBalance()-purchase.getTotalPrice());
            purchase.setPaymentMethod(PaymentMethod.MEMBERSHIP_CARD);
            purchase.setMemberAccount(memberAccount);
            return registerPurchase(memberAccount, purchase);

        }
    }

    @Override
    public Purchase processPurchaseWithCash(MemberAccount memberAccount, Purchase purchase) throws AccountNotFoundException{
        if(memberAccount.getId() == null || memberFinder.findById(memberAccount.getId()).isEmpty()) throw new AccountNotFoundException();
        else{
            purchase.setMemberAccount(memberAccount);
            purchase.setPaymentMethod(PaymentMethod.CASH);
            return registerPurchase(memberAccount, purchase);

        }
    }

    private Purchase registerPurchase(MemberAccount memberAccount, Purchase purchase) throws AccountNotFoundException {
        purchase.setDate(LocalDate.now());
        addPoints(memberAccount,purchase);
        memberAccount.getTransactions().add(purchase);
        if(memberAccount.getTransactions().stream()
                .filter(t -> t instanceof Purchase)
                .filter(t2 -> t2.getDate().isAfter(LocalDate.now().minusWeeks(1)))
                .count() == Integer.parseInt(Objects.requireNonNull(env.getProperty("VFP.MinPurchasesNumber"))))
            memberHandler.updateAccountStatus(memberAccount, AccountStatus.VFP);
        return purchaseRepository.save(purchase);
    }


    @Override
    public UsePoints processPointsUsage(MemberAccount memberAccount, UsePoints usePoint)throws DeclinedTransactionException, InsufficientPointsException ,AccountNotFoundException{
        if(memberAccount.getId() == null || memberFinder.findById(memberAccount.getId()).isEmpty()) throw new AccountNotFoundException();
        else{
            if((usePoint.getGift().getRequiredStatus() == AccountStatus.VFP  && memberAccount.getStatus() != AccountStatus.VFP ) ||
                    memberAccount.getTransactions().stream().
                            noneMatch(transaction -> transaction instanceof Purchase &&
                                  transaction.getShop().equals(usePoint.getGift().getShop()))
                            )
                 {
                throw new DeclinedTransactionException();
            }else{
                removePoints(memberAccount,usePoint);
                memberAccount.getTransactions().add(usePoint);
                return usePointsRepository.save(usePoint);
            }

        }
    }


}
