package fr.univcotedazur.simpletcfs.components;


import fr.univcotedazur.simpletcfs.entities.AccountStatus;
import fr.univcotedazur.simpletcfs.entities.MemberAccount;
import fr.univcotedazur.simpletcfs.exceptions.NotVFPException;
import fr.univcotedazur.simpletcfs.interfaces.ISWUPLS;
import fr.univcotedazur.simpletcfs.interfaces.ParkingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParkingManager implements ParkingHandler {
    private final ISWUPLS iswupls;
    @Override
    public void useParkingTime(MemberAccount memberAccount, String carRegistrationNumber, int parkingSpot) throws NotVFPException
    {
        if(! memberAccount.getStatus().equals(AccountStatus.VFP))
            throw new NotVFPException();
        registerParking(carRegistrationNumber, parkingSpot);
    }
    @Autowired
    public ParkingManager(ISWUPLS iswupls){
        this.iswupls = iswupls;
    }
    @Override
    public void registerParking(String CarRegistrationNumber,int parkingSpotNumber) {
        if (! iswupls.startParkingTimer(CarRegistrationNumber,parkingSpotNumber))
            throw new RuntimeException();

    }
}
