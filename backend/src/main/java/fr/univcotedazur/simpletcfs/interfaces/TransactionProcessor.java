package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.CreditCard;
import fr.univcotedazur.simpletcfs.entities.MemberAccount;
import fr.univcotedazur.simpletcfs.entities.Purchase;
import fr.univcotedazur.simpletcfs.entities.UsePoints;
import fr.univcotedazur.simpletcfs.exceptions.InsufficientPointsException;
import fr.univcotedazur.simpletcfs.exceptions.PaymentException;

public interface TransactionProcessor {
    void processPurchase(MemberAccount memberAccount, Purchase purchase, CreditCard card) throws PaymentException;
    public void processPointsUsage(MemberAccount memberAccount,UsePoints usePoint)throws InsufficientPointsException;
}
