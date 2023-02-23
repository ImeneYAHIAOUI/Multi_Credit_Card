package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.entities.MemberAccount;
import fr.univcotedazur.simpletcfs.entities.Purchase;
import fr.univcotedazur.simpletcfs.entities.Transaction;
import fr.univcotedazur.simpletcfs.entities.UsePoints;
import fr.univcotedazur.simpletcfs.exceptions.InsufficientPointsException;
import fr.univcotedazur.simpletcfs.interfaces.PointTrader;
import org.springframework.stereotype.Component;

import java.nio.file.attribute.UserDefinedFileAttributeView;

@Component
public class PointsManager implements PointTrader {

    public void removePoints(MemberAccount memberAccount, UsePoints usePoints) throws InsufficientPointsException{
        if(memberAccount.getPoints()>=usePoints.getUseddPoints())
            memberAccount.setPoints(memberAccount.getPoints()-usePoints.getUseddPoints());
        else throw new InsufficientPointsException();
    }
    public void addPoints(MemberAccount memberAccount, Purchase purchase) {
            memberAccount.setPoints(memberAccount.getPoints()+ purchase.getEarnedPoints());
    }
}
