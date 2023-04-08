package fr.univcotedazur.multicredit.interfaces;


import fr.univcotedazur.multicredit.connectors.externaldto.externaldto.ISWUPLSDTO;
import fr.univcotedazur.multicredit.entities.MemberAccount;
import fr.univcotedazur.multicredit.exceptions.NotVFPException;

public interface ParkingHandler {
    void useParkingTime(MemberAccount memberAccount, String carRegistrationNumber, int parkingSpotNumber) throws NotVFPException;

    ISWUPLSDTO[] getParkingInformation(String carRegistrationNumber);
}
