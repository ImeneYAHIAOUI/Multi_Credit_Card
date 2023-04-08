package fr.univcotedazur.multicredit.components;


import fr.univcotedazur.multicredit.connectors.externaldto.externaldto.ISWUPLSDTO;
import fr.univcotedazur.multicredit.entities.AccountStatus;
import fr.univcotedazur.multicredit.entities.MemberAccount;
import fr.univcotedazur.multicredit.exceptions.NotVFPException;
import fr.univcotedazur.multicredit.interfaces.ISWUPLS;
import fr.univcotedazur.multicredit.interfaces.ParkingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParkingManager implements ParkingHandler {
    private final ISWUPLS iswupls;

    @Autowired
    public ParkingManager(ISWUPLS iswupls) {
        this.iswupls = iswupls;
    }

    @Override
    public void useParkingTime(MemberAccount memberAccount, String carRegistrationNumber, int parkingSpot) throws NotVFPException {
        if (!memberAccount.getStatus().equals(AccountStatus.VFP)) throw new NotVFPException();
        boolean result = iswupls.startParkingTimer(carRegistrationNumber, parkingSpot);
        if (!result) throw new ParkingException("Parking time could not be started.");
    }

    @Override
    public ISWUPLSDTO[] getParkingInformation(String carRegistrationNumber) {
        return iswupls.getParkingInformation(carRegistrationNumber);
    }

    private static class ParkingException extends RuntimeException {
        public ParkingException(String message) {
            super(message);
        }
    }
}
