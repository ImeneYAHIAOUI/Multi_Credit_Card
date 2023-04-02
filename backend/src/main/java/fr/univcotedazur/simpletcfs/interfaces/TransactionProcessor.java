package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.MemberAccount;
import fr.univcotedazur.simpletcfs.entities.Purchase;
import fr.univcotedazur.simpletcfs.entities.UsePoints;
import fr.univcotedazur.simpletcfs.exceptions.AccountNotFoundException;
import fr.univcotedazur.simpletcfs.exceptions.DeclinedTransactionException;
import fr.univcotedazur.simpletcfs.exceptions.InsufficientPointsException;
import fr.univcotedazur.simpletcfs.exceptions.PaymentException;

public interface TransactionProcessor {
    Purchase processPurchaseWithCreditCard(MemberAccount memberAccount, Purchase purchase, String card) throws PaymentException, AccountNotFoundException;

    Purchase processPurchaseWithMemberCard(MemberAccount memberAccount, Purchase purchase) throws PaymentException, AccountNotFoundException;

    Purchase processPurchaseWithCash(MemberAccount memberAccount, Purchase purchase) throws PaymentException, AccountNotFoundException;

    UsePoints processPointsUsage(MemberAccount memberAccount, UsePoints usePoint)throws DeclinedTransactionException,
            InsufficientPointsException ,AccountNotFoundException;
}

