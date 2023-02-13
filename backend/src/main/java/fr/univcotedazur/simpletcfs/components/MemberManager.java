package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.entities.AccountStatus;
import fr.univcotedazur.simpletcfs.entities.Form;
import fr.univcotedazur.simpletcfs.entities.MemberAccount;
import fr.univcotedazur.simpletcfs.entities.MembershipCard;
import fr.univcotedazur.simpletcfs.exceptions.AccountNotFoundException;
import fr.univcotedazur.simpletcfs.exceptions.AlreadyExistingMemberException;
import fr.univcotedazur.simpletcfs.exceptions.MissingInformationException;
import fr.univcotedazur.simpletcfs.interfaces.MemberFinder;
import fr.univcotedazur.simpletcfs.interfaces.MemberHandler;
import fr.univcotedazur.simpletcfs.repositories.MemberAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Component
public class MemberManager implements MemberHandler, MemberFinder {
    private MemberAccountRepository memberAccountRepository;

    @Autowired
    public MemberManager(MemberAccountRepository memberAccountRepository) {
        this.memberAccountRepository = memberAccountRepository;
    }


    @Override
    public MemberAccount createAccount(String name, String mail, String password, LocalDate birthDate) throws MissingInformationException , AlreadyExistingMemberException {
        if (name == null || mail == null || password == null || birthDate == null) {
            throw new MissingInformationException();
        }
        if(findByMail(mail) != null ){
            throw new AlreadyExistingMemberException();
        }
        MemberAccount memberAccount = new MemberAccount(UUID.randomUUID(), name,mail, password, birthDate, 0, 0);
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
        while (memberAccountRepository.findAll().iterator().hasNext()) {
            MemberAccount memberAccount = memberAccountRepository.findAll().iterator().next();
            if (memberAccount.getMail().equals(mail)) {
                return memberAccount;
            }
        }
        return null;
    }


}
