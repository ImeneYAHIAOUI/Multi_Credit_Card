package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.entities.*;
import fr.univcotedazur.simpletcfs.exceptions.*;
import fr.univcotedazur.simpletcfs.interfaces.MemberFinder;
import fr.univcotedazur.simpletcfs.interfaces.MemberHandler;
import fr.univcotedazur.simpletcfs.interfaces.ParkingHandler;
import fr.univcotedazur.simpletcfs.repositories.MemberAccountRepository;
import fr.univcotedazur.simpletcfs.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Component
public class MemberManager implements MemberHandler, MemberFinder {
    private final MemberAccountRepository memberAccountRepository;
    private final ParkingHandler parkingHandler;
    private final TransactionRepository transactionRepository;

    private final Environment env;




    @Autowired
    public MemberManager(MemberAccountRepository memberAccountRepository, ParkingHandler parkingHandler, TransactionRepository transactionRepository, Environment env) {
        this.memberAccountRepository = memberAccountRepository;
        this.parkingHandler = parkingHandler;
        this.transactionRepository = transactionRepository;
        this.env = env;
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
        if(findMember(memberAccount.getId()) == null) throw new AccountNotFoundException();
        memberAccount.setStatus(AccountStatus.EXPIRED);
    }

    @Override
    public void restoreAccount(MemberAccount memberAccount) throws AccountNotFoundException {
        if(findMember(memberAccount.getId()) == null) throw new AccountNotFoundException();
        memberAccount.setStatus(AccountStatus.REGULAR);
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
        if(findMember(memberAccount.getId()) == null) throw new AccountNotFoundException();
        if (form.getName() != null)
            memberAccount.setName(form.getName());
        if(form.getPassword() != null)
            memberAccount.setPassword(form.getPassword());
        if(form.getBirthDate() != null)
            memberAccount.setBirthDate(form.getBirthDate());
        if(form.getMail() != null)
            memberAccount.setMail(form.getMail());
    }

    @Scheduled(cron = "${VFP.updateRate.cron}" )
    @Override
    public void updateAccountsStatus() {
        for (MemberAccount  memberAccount : memberAccountRepository.findAll()) {
            if(StreamSupport.stream(transactionRepository.findAll().spliterator(), false).filter(t2 -> t2.getDate().isAfter(LocalDate.now().minusWeeks(1))).filter(t -> t.getMemberAccount().equals(memberAccount)).count() >= Integer.parseInt(Objects.requireNonNull(env.getProperty("VFP.MinPurchasesNumber"))))
                memberAccount.setStatus(AccountStatus.VFP);
            else
                memberAccount.setStatus(AccountStatus.REGULAR);
        }
    }

    @Override
    public void updateAccountStatus(MemberAccount memberAccount, AccountStatus status) throws AccountNotFoundException {
        if(findMember(memberAccount.getId()) == null) throw new AccountNotFoundException();
        memberAccount.setStatus(status);
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
