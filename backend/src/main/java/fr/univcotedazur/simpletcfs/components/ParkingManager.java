package fr.univcotedazur.simpletcfs.components;


import fr.univcotedazur.simpletcfs.interfaces.ISWUPLS;
import fr.univcotedazur.simpletcfs.interfaces.ParkingHandler;
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
    public void registerParking(String CarRegistrationNumber,int parkingSpotNumber) {
        if (! iswupls.startParkingTimer(CarRegistrationNumber,parkingSpotNumber))
            throw new RuntimeException();

    }
}
