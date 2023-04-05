package fr.univcotedazur.multiCredit.interfaces;

import fr.univcotedazur.multiCredit.entities.MemberAccount;
import fr.univcotedazur.multiCredit.entities.Purchase;
import fr.univcotedazur.multiCredit.entities.UsePoints;
import fr.univcotedazur.multiCredit.exceptions.InsufficientPointsException;

public interface PointTrader {
    void removePoints(MemberAccount memberAccount, UsePoints usePoints) throws InsufficientPointsException;
    void addPoints(MemberAccount memberAccount, Purchase purchase);

}
