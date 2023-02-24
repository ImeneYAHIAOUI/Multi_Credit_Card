package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.entities.MemberAccount;
import fr.univcotedazur.simpletcfs.entities.Purchase;
import fr.univcotedazur.simpletcfs.entities.UsePoints;
import fr.univcotedazur.simpletcfs.exceptions.InsufficientPointsException;
import fr.univcotedazur.simpletcfs.interfaces.PointTrader;
import org.springframework.stereotype.Component;

@Component
public class PointsManager implements PointTrader {

    public void removePoints(MemberAccount memberAccount, UsePoints usePoints) throws InsufficientPointsException{
        if(memberAccount.getPoints()>=usePoints.getUsedPoints())
            memberAccount.setPoints(memberAccount.getPoints()-usePoints.getUsedPoints());
        else throw new InsufficientPointsException();
    }
    public void addPoints(MemberAccount memberAccount, Purchase purchase) {
        if(memberAccount!=null)
            memberAccount.setPoints(memberAccount.getPoints()+ purchase.getEarnedPoints());
    }
}
