package fr.univcotedazur.multicredit.interfaces;

import fr.univcotedazur.multicredit.entities.AccountStatus;
import fr.univcotedazur.multicredit.entities.Form;
import fr.univcotedazur.multicredit.entities.MemberAccount;
import fr.univcotedazur.multicredit.exceptions.*;

import java.time.LocalDate;


public interface MemberHandler {
    MemberAccount createAccount(String name, String mail, String password, LocalDate birthDate) throws MissingInformationException, AlreadyExistingMemberException, UnderAgeException;

    void archiveAccount(MemberAccount memberAccount) throws AccountNotFoundException;

    void restoreAccount(MemberAccount memberAccount) throws AccountNotFoundException;

    void deleteAccount(MemberAccount memberAccount) throws AccountNotFoundException;

    void updateAccount(MemberAccount memberAccount, Form form) throws AccountNotFoundException;

    void updateAccountsStatus();

    void updateAccountStatus(MemberAccount memberAccount, AccountStatus status) throws AccountNotFoundException;

    void renewMembership(MemberAccount memberAccount) throws AccountNotFoundException, TooEarlyForRenewalException;

    void archiveOrDeleteExpiredAccounts() throws AccountNotFoundException;

    void chargeMembershipCard(MemberAccount memberAccount, double amount, String creditCard) throws AccountNotFoundException, PaymentException;
}
