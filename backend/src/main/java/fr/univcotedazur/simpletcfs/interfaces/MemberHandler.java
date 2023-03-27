package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.AccountStatus;
import fr.univcotedazur.simpletcfs.entities.Form;
import fr.univcotedazur.simpletcfs.entities.MemberAccount;
import fr.univcotedazur.simpletcfs.exceptions.*;

import java.time.LocalDate;


public interface MemberHandler {
    MemberAccount createAccount(String name, String mail, String password, LocalDate birthDate) throws MissingInformationException, AlreadyExistingMemberException, UnderAgeException;
    void archiveAccount(MemberAccount memberAccount) throws AccountNotFoundException;
    void restoreAccount(MemberAccount memberAccount) throws AccountNotFoundException;
     void deleteAccount(MemberAccount memberAccount) throws AccountNotFoundException;
    void updateAccount(MemberAccount memberAccount,Form form) throws
            AccountNotFoundException;
    void updateAccountsStatus();
    void updateAccountStatus(MemberAccount memberAccount, AccountStatus status) throws
            AccountNotFoundException;

    void useParkingTime(MemberAccount memberAccount,String carRegistrationNumber,int parkingSpotNumber) throws NotVFPException;

    void renewMembership(MemberAccount memberAccount) throws AccountNotFoundException, TooEarlyForRenewalException;

    void archiveOrDeleteExpiredAccounts() throws AccountNotFoundException;
}
