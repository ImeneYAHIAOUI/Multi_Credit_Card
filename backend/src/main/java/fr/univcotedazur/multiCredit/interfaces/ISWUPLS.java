package fr.univcotedazur.multiCredit.interfaces;

import fr.univcotedazur.multiCredit.connectors.externaldto.externaldto.ISWUPLSDTO;

public interface ISWUPLS {
     boolean startParkingTimer(String carRegistrationNumber, int parkingSpotNumber);

    ISWUPLSDTO[] getParkingInformation(String carRegistrationNumber);
}
