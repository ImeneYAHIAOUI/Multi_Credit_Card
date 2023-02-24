package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.entities.AccountStatus;
import fr.univcotedazur.simpletcfs.entities.Form;
import fr.univcotedazur.simpletcfs.entities.MemberAccount;
import fr.univcotedazur.simpletcfs.entities.MembershipCard;
import fr.univcotedazur.simpletcfs.exceptions.*;
import fr.univcotedazur.simpletcfs.interfaces.MemberFinder;
import fr.univcotedazur.simpletcfs.interfaces.MemberHandler;
import fr.univcotedazur.simpletcfs.interfaces.ParkingHandler;
import fr.univcotedazur.simpletcfs.repositories.MemberAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Component
public class MemberManager implements MemberHandler, MemberFinder {
    private MemberAccountRepository memberAccountRepository;
    private ParkingHandler parkingHandler;
    @Autowired
    public MemberManager(MemberAccountRepository memberAccountRepository, ParkingHandler parkingHandler) {
        this.memberAccountRepository = memberAccountRepository;
        this.parkingHandler = parkingHandler;
    }


    @Override
    public MemberAccount createAccount(String name, String mail, String password, LocalDate birthDate) throws MissingInformationException, AlreadyExistingMemberException, UnderAgeException {
        if (name == null || mail == null || password == null || birthDate == null) {
            throw new MissingInformationException();
        }
        MemberAccount memberAccount = findByMail(mail);
        if(memberAccount != null ){
            throw new AlreadyExistingMemberException();
        }
        if(birthDate.isAfter(LocalDate.now().minusYears(16))){
            throw new UnderAgeException();
        }
        memberAccount = new MemberAccount(UUID.randomUUID(), name,mail, password, birthDate, 0, 0);
        memberAccount.setStatus(AccountStatus.REGULAR);
        memberAccountRepository.save(memberAccount,memberAccount.getId());
        return memberAccount;
    }



    @Override
    public void archiveAccount(MemberAccount memberAccount) throws AccountNotFoundException {

    }

    @Override
    public void restoreAccount(MemberAccount memberAccount) throws AccountNotFoundException {

    }

    @Override
    public void deleteAccount(MemberAccount memberAccount) throws AccountNotFoundException {
        try {
            memberAccountRepository.deleteById(memberAccount.getId());
        }
        catch (Exception e){
            throw new AccountNotFoundException();
        }
    }

    @Override
    public void updateAccount(MemberAccount memberAccount, Form form) throws AccountNotFoundException {

    }

    @Override
    public void updateAccountsStatus() {

    }

    @Override
    public void updateAccountStatus(MemberAccount memberAccount, AccountStatus status) throws AccountNotFoundException {

    }

    @Override
    public MemberAccount findMember(UUID id) {
        return memberAccountRepository.findById(id).orElse(null);
    }

    @Override
    public MemberAccount findByMail(String mail) {
        for (MemberAccount  memberAccount : memberAccountRepository.findAll()) {
            if (memberAccount.getMail().equals(mail)) {
                return memberAccount;
            }
        }
        return null;
    }

    @Override
    public void useParkingTime(MemberAccount memberAccount,String carRegistrationNumber) throws NotVFPException
    {
        if(! memberAccount.getStatus().equals(AccountStatus.VFP))
            throw new NotVFPException();
        parkingHandler.registerParking(carRegistrationNumber);

    }


}
