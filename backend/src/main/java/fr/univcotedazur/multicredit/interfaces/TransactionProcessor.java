package fr.univcotedazur.multicredit.interfaces;

import fr.univcotedazur.multicredit.entities.MemberAccount;
import fr.univcotedazur.multicredit.entities.Purchase;
import fr.univcotedazur.multicredit.entities.UsePoints;
import fr.univcotedazur.multicredit.exceptions.AccountNotFoundException;
import fr.univcotedazur.multicredit.exceptions.DeclinedTransactionException;
import fr.univcotedazur.multicredit.exceptions.InsufficientPointsException;
import fr.univcotedazur.multicredit.exceptions.PaymentException;

public interface TransactionProcessor {
    Purchase processPurchaseWithCreditCard(MemberAccount memberAccount, Purchase purchase, String card) throws PaymentException, AccountNotFoundException;

    Purchase processPurchaseWithMemberCard(MemberAccount memberAccount, Purchase purchase) throws PaymentException, AccountNotFoundException;

    Purchase processPurchaseWithCash(MemberAccount memberAccount, Purchase purchase) throws PaymentException, AccountNotFoundException;

    UsePoints processPointsUsage(MemberAccount memberAccount, UsePoints usePoint) throws DeclinedTransactionException, InsufficientPointsException, AccountNotFoundException;
}

