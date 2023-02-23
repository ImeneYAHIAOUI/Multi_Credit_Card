package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.MemberAccount;
import fr.univcotedazur.simpletcfs.entities.Purchase;
import fr.univcotedazur.simpletcfs.entities.Transaction;
import fr.univcotedazur.simpletcfs.entities.UsePoints;
import fr.univcotedazur.simpletcfs.exceptions.InsufficientPointsException;

public interface PointTrader {
    void removePoints(MemberAccount memberAccount, UsePoints usePoints) throws InsufficientPointsException;
    void addPoints(MemberAccount memberAccount, Purchase purchase);

}
