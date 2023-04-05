package fr.univcotedazur.multiCredit.components;


import fr.univcotedazur.multiCredit.connectors.externaldto.externaldto.ISWUPLSDTO;
import fr.univcotedazur.multiCredit.entities.AccountStatus;
import fr.univcotedazur.multiCredit.entities.MemberAccount;
import fr.univcotedazur.multiCredit.exceptions.NotVFPException;
import fr.univcotedazur.multiCredit.interfaces.ISWUPLS;
import fr.univcotedazur.multiCredit.interfaces.ParkingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParkingManager implements ParkingHandler {
    private final ISWUPLS iswupls;

    @Autowired
    public ParkingManager(ISWUPLS iswupls){
        this.iswupls = iswupls;
    }
    @Override
    public void useParkingTime(MemberAccount memberAccount, String carRegistrationNumber, int parkingSpot) throws NotVFPException
    {
        if(! memberAccount.getStatus().equals(AccountStatus.VFP))
            throw new NotVFPException();
        boolean result = iswupls.startParkingTimer(carRegistrationNumber,parkingSpot);
        if (! result)
            throw new RuntimeException();
    }

    @Override
    public ISWUPLSDTO[] getParkingInformation(String carRegistrationNumber) {
        return iswupls.getParkingInformation(carRegistrationNumber);
    }
}
