package fr.univcotedazur.multicredit.interfaces;

import fr.univcotedazur.multicredit.entities.MemberAccount;
import fr.univcotedazur.multicredit.entities.Purchase;
import fr.univcotedazur.multicredit.entities.UsePoints;
import fr.univcotedazur.multicredit.exceptions.InsufficientPointsException;

public interface PointTrader {
    void removePoints(MemberAccount memberAccount, UsePoints usePoints) throws InsufficientPointsException;

    void addPoints(MemberAccount memberAccount, Purchase purchase);
}
