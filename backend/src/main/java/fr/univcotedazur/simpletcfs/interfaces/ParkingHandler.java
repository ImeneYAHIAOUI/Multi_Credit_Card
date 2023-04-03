package fr.univcotedazur.simpletcfs.interfaces;


import fr.univcotedazur.simpletcfs.entities.MemberAccount;
import fr.univcotedazur.simpletcfs.exceptions.NotVFPException;

public interface ParkingHandler {
    void registerParking(String CarRegistrationNumber, int parkingSpotNumber) ;
    void useParkingTime(MemberAccount memberAccount, String carRegistrationNumber, int parkingSpotNumber) throws NotVFPException;


}
