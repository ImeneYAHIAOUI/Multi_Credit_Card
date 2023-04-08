package fr.univcotedazur.multicredit.interfaces;

import fr.univcotedazur.multicredit.connectors.externaldto.externaldto.ISWUPLSDTO;

public interface ISWUPLS {
    boolean startParkingTimer(String carRegistrationNumber, int parkingSpotNumber);

    ISWUPLSDTO[] getParkingInformation(String carRegistrationNumber);
}
