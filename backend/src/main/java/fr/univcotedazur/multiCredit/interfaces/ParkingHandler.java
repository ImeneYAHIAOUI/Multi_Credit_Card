package fr.univcotedazur.multiCredit.interfaces;


import fr.univcotedazur.multiCredit.connectors.externaldto.externaldto.ISWUPLSDTO;
import fr.univcotedazur.multiCredit.entities.MemberAccount;
import fr.univcotedazur.multiCredit.exceptions.NotVFPException;

public interface ParkingHandler {
    void useParkingTime(MemberAccount memberAccount, String carRegistrationNumber, int parkingSpotNumber) throws NotVFPException;

    ISWUPLSDTO[] getParkingInformation(String carRegistrationNumber);
}
