package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.entities.*;
import fr.univcotedazur.simpletcfs.exceptions.*;
import fr.univcotedazur.simpletcfs.interfaces.MemberFinder;
import fr.univcotedazur.simpletcfs.interfaces.MemberHandler;
import fr.univcotedazur.simpletcfs.interfaces.ParkingHandler;
import fr.univcotedazur.simpletcfs.repositories.MemberRepository;
import fr.univcotedazur.simpletcfs.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;


@Component
@Transactional
public class MemberManager implements MemberHandler, MemberFinder {
    private final MemberRepository memberRepository;
    private final ParkingHandler parkingHandler;
    private final TransactionRepository transactionRepository;
    private final Environment env;




    @Autowired
    public MemberManager( MemberRepository memberRepository, ParkingHandler parkingHandler, TransactionRepository transactionRepository, Environment env) {
        this.memberRepository = memberRepository;
        this.parkingHandler = parkingHandler;
        this.transactionRepository = transactionRepository;
        this.env = env;
    }


    @Override
    public MemberAccount createAccount(String name, String mail, String password, LocalDate birthDate) throws MissingInformationException, AlreadyExistingMemberException, UnderAgeException {
        if (name == null || mail == null || password == null || birthDate == null) {
            throw new MissingInformationException();
        }
        MemberAccount memberAccount = findByMail(mail).orElse(null);
        if(memberAccount != null ){
            throw new AlreadyExistingMemberException();
        }
        if(birthDate.isAfter(LocalDate.now().minusYears(16))){
            throw new UnderAgeException();
        }
        memberAccount = new MemberAccount(name,mail, password, birthDate, 0, 0);
        memberAccount.setStatus(AccountStatus.REGULAR);
        return memberRepository.save(memberAccount);
    }

    @Override
    public void archiveAccount(MemberAccount memberAccount) throws AccountNotFoundException {
        if(memberAccount.getId() == null || findById(memberAccount.getId()).isEmpty()) throw new AccountNotFoundException();
        memberAccount.setStatus(AccountStatus.EXPIRED);
    }

    @Override
    public void restoreAccount(MemberAccount memberAccount) throws AccountNotFoundException {
        if(memberAccount.getId() == null || findById(memberAccount.getId()).isEmpty()) throw new AccountNotFoundException();
        memberAccount.setStatus(AccountStatus.REGULAR);
    }

    @Override
    public void deleteAccount(MemberAccount memberAccount) throws AccountNotFoundException {
        try {
            memberRepository.delete(memberAccount);
        }
        catch (Exception e){
            throw new AccountNotFoundException();
        }
    }



    @Override
    public void updateAccount(MemberAccount memberAccount, Form form) throws AccountNotFoundException {
        if(memberAccount.getId() == null || findById(memberAccount.getId()).isEmpty()) throw new AccountNotFoundException();
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
        for (MemberAccount  memberAccount : memberRepository.findAll()) {

            System.out.println(memberAccount
                    .getTransactions().stream()
                    .filter(t -> t instanceof Purchase)
                    .filter(t2 -> t2.getDate().isAfter(LocalDate.now().minusWeeks(1)))
                    .count() );
            if(memberAccount
                    .getTransactions().stream()
                    .filter(t -> t instanceof Purchase)
                    .filter(t2 -> t2.getDate().isAfter(LocalDate.now().minusWeeks(1)))
                    .count() < Integer.parseInt(Objects.requireNonNull(env.getProperty("VFP.MinPurchasesNumber"))))
                memberAccount.setStatus(AccountStatus.REGULAR);
        }
    }

    @Override
    public void updateAccountStatus(MemberAccount memberAccount, AccountStatus status) throws AccountNotFoundException {
        if(memberAccount.getId() == null || findById(memberAccount.getId()).isEmpty()) throw new AccountNotFoundException();
        findById(memberAccount.getId()).get().setStatus(status);
    }

    @Override
    public Optional<MemberAccount> findById(Long id) {
        return memberRepository.findById(id);
    }

    @Override
    public Optional<MemberAccount> findByMail(String mail) {
        for (MemberAccount  memberAccount : memberRepository.findAll()) {

            if (memberAccount.getMail().equals(mail)) {
                return Optional.of(memberAccount);
            }
        }
        return Optional.empty();
    }

    @Override
    public void useParkingTime(MemberAccount memberAccount,String carRegistrationNumber,int parkingSpot) throws NotVFPException
    {
        if(! memberAccount.getStatus().equals(AccountStatus.VFP))
            throw new NotVFPException();
        parkingHandler.registerParking(carRegistrationNumber, parkingSpot);

    }

    @Override
    public void renewMembership(MemberAccount memberAccount) throws AccountNotFoundException, TooEarlyForRenewalException {
        if(findById(memberAccount.getId()).isEmpty())
            throw  new  AccountNotFoundException();
        if(memberAccount.getMembershipCard().getExpirationDate().isAfter(LocalDate.now().plusMonths(3)))
            throw new TooEarlyForRenewalException();
        memberAccount.getMembershipCard().setExpirationDate(memberAccount.getMembershipCard().getExpirationDate().plusMonths(18));
        if(memberAccount.getStatus().equals(AccountStatus.EXPIRED))
            updateAccountStatus(memberAccount,AccountStatus.REGULAR);
    }

    @Override
    @Scheduled(cron = "0 0 0 * * *")
    public void archiveOrDeleteExpiredAccounts() throws AccountNotFoundException {
        for (MemberAccount  memberAccount : memberRepository.findAll()) {
            if(memberAccount.getMembershipCard().getExpirationDate().isBefore(LocalDate.now().minusYears(2)))
                deleteAccount(memberAccount);
            if(memberAccount.getMembershipCard().getExpirationDate().isBefore(LocalDate.now()))
                archiveAccount(memberAccount);
        }

    }


}
