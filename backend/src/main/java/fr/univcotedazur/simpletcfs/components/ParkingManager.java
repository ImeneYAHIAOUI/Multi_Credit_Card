package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.connectors.ISWUPLSProxy;
import fr.univcotedazur.simpletcfs.entities.AccountStatus;
import fr.univcotedazur.simpletcfs.entities.MemberAccount;
import fr.univcotedazur.simpletcfs.exceptions.NotVFPException;
import fr.univcotedazur.simpletcfs.interfaces.ISWUPLS;
import fr.univcotedazur.simpletcfs.interfaces.ParkingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParkingManager implements ParkingHandler {

    private ISWUPLS iswupls;

    @Autowired
    public ParkingManager(ISWUPLS iswupls){
        this.iswupls = iswupls;
    }
    @Override
    public void registerParking(String CarRegistrationNumber) {
        iswupls.startParkingTimer(CarRegistrationNumber);
    }
}
