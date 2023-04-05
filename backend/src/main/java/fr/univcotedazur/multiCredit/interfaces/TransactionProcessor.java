package fr.univcotedazur.multiCredit.interfaces;

import fr.univcotedazur.multiCredit.entities.MemberAccount;
import fr.univcotedazur.multiCredit.entities.Purchase;
import fr.univcotedazur.multiCredit.entities.UsePoints;
import fr.univcotedazur.multiCredit.exceptions.AccountNotFoundException;
import fr.univcotedazur.multiCredit.exceptions.DeclinedTransactionException;
import fr.univcotedazur.multiCredit.exceptions.InsufficientPointsException;
import fr.univcotedazur.multiCredit.exceptions.PaymentException;

public interface TransactionProcessor {
    Purchase processPurchaseWithCreditCard(MemberAccount memberAccount, Purchase purchase, String card) throws PaymentException, AccountNotFoundException;

    Purchase processPurchaseWithMemberCard(MemberAccount memberAccount, Purchase purchase) throws PaymentException, AccountNotFoundException;

    Purchase processPurchaseWithCash(MemberAccount memberAccount, Purchase purchase) throws PaymentException, AccountNotFoundException;

    UsePoints processPointsUsage(MemberAccount memberAccount, UsePoints usePoint)throws DeclinedTransactionException,
            InsufficientPointsException ,AccountNotFoundException;
}

