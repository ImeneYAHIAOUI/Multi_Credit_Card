package fr.univcotedazur.multiCredit.components;

import fr.univcotedazur.multiCredit.entities.AccountStatus;
import fr.univcotedazur.multiCredit.entities.Form;
import fr.univcotedazur.multiCredit.entities.MemberAccount;
import fr.univcotedazur.multiCredit.entities.Purchase;
import fr.univcotedazur.multiCredit.exceptions.*;
import fr.univcotedazur.multiCredit.interfaces.Bank;
import fr.univcotedazur.multiCredit.interfaces.MemberFinder;
import fr.univcotedazur.multiCredit.interfaces.MemberHandler;
import fr.univcotedazur.multiCredit.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Component
@Transactional
public class MemberManager implements MemberHandler, MemberFinder {
    private final MemberRepository memberRepository;
    private final Environment env;
    private final Bank bank;

    @Autowired
    public MemberManager(MemberRepository memberRepository, Environment env, Bank bank) {
        this.memberRepository = memberRepository;
        this.env = env;
        this.bank = bank;
    }

    @Override
    public MemberAccount createAccount(String name, String mail, String password, LocalDate birthDate) throws MissingInformationException, AlreadyExistingMemberException, UnderAgeException {
        if (name == null || mail == null || password == null || birthDate == null)
            throw new MissingInformationException();
        MemberAccount memberAccount = findByMail(mail).orElse(null);

        if (memberAccount != null) throw new AlreadyExistingMemberException();
        if (birthDate.isAfter(LocalDate.now().minusYears(16))) throw new UnderAgeException();

        memberAccount = new MemberAccount(name, mail, password, birthDate, 0, 0);
        memberAccount.setStatus(AccountStatus.REGULAR);
        return memberRepository.save(memberAccount);
    }

    @Override
    public void archiveAccount(MemberAccount memberAccount) throws AccountNotFoundException {
        if (memberAccount.getId() == null || findById(memberAccount.getId()).isEmpty())
            throw new AccountNotFoundException();
        memberAccount.setStatus(AccountStatus.EXPIRED);
    }

    @Override
    public void restoreAccount(MemberAccount memberAccount) throws AccountNotFoundException {
        if (memberAccount.getId() == null || findById(memberAccount.getId()).isEmpty())
            throw new AccountNotFoundException();
        memberAccount.setStatus(AccountStatus.REGULAR);
    }

    @Override
    public void deleteAccount(MemberAccount memberAccount) throws AccountNotFoundException {
        try {
            memberRepository.delete(memberAccount);
        } catch (Exception e) {
            throw new AccountNotFoundException();
        }
    }


    @Override
    public void updateAccount(MemberAccount memberAccount, Form form) throws AccountNotFoundException {
        if (memberAccount.getId() == null || findById(memberAccount.getId()).isEmpty())
            throw new AccountNotFoundException();
        if (form.getName() != null) memberAccount.setName(form.getName());
        if (form.getPassword() != null) memberAccount.setPassword(form.getPassword());
        if (form.getBirthDate() != null) memberAccount.setBirthDate(form.getBirthDate());
        if (form.getMail() != null) memberAccount.setMail(form.getMail());
    }

    @Scheduled(cron = "${VFP.updateRate.cron}")
    @Override
    public void updateAccountsStatus() {
        for (MemberAccount memberAccount : memberRepository.findAll()) {
            System.out.println(memberAccount
                    .getTransactions().stream()
                    .filter(Purchase.class::isInstance)
                    .filter(t2 -> t2.getDate().isAfter(LocalDate.now().minusWeeks(1)))
                    .count());
            if (memberAccount.getTransactions().stream()
                    .filter(Purchase.class::isInstance)
                    .filter(t2 -> t2.getDate().isAfter(LocalDate.now().minusWeeks(1)))
                    .count() < Integer.parseInt(Objects.requireNonNull(env.getProperty("VFP.MinPurchasesNumber"))))
                memberAccount.setStatus(AccountStatus.REGULAR);
        }
    }

    @Override
    public void updateAccountStatus(MemberAccount memberAccount, AccountStatus status) throws AccountNotFoundException {
        if (memberAccount.getId() == null || findById(memberAccount.getId()).isEmpty())
            throw new AccountNotFoundException();
        findById(memberAccount.getId()).get().setStatus(status);
    }

    @Override
    public Optional<MemberAccount> findById(Long id) {
        return memberRepository.findById(id);
    }

    @Override
    public Optional<MemberAccount> findByMail(String mail) {
        for (MemberAccount memberAccount : memberRepository.findAll()) {
            if (memberAccount.getMail().equals(mail)) {
                return Optional.of(memberAccount);
            }
        }
        return Optional.empty();
    }


    @Override
    public void renewMembership(MemberAccount memberAccount) throws AccountNotFoundException, TooEarlyForRenewalException {
        if (findById(memberAccount.getId()).isEmpty()) throw new AccountNotFoundException();
        if (memberAccount.getMembershipCard().getExpirationDate().isAfter(LocalDate.now().plusMonths(3)))
            throw new TooEarlyForRenewalException();

        memberAccount.getMembershipCard().setExpirationDate(memberAccount.getMembershipCard().getExpirationDate().plusMonths(18));
        if (memberAccount.getStatus().equals(AccountStatus.EXPIRED))
            updateAccountStatus(memberAccount, AccountStatus.REGULAR);
    }

    @Override
    @Scheduled(cron = "0 0 0 * * *")
    public void archiveOrDeleteExpiredAccounts() throws AccountNotFoundException {
        for (MemberAccount memberAccount : memberRepository.findAll()) {
            if (memberAccount.getMembershipCard().getExpirationDate().isBefore(LocalDate.now().minusYears(2)))
                deleteAccount(memberAccount);
            if (memberAccount.getMembershipCard().getExpirationDate().isBefore(LocalDate.now()))
                archiveAccount(memberAccount);
        }
    }

    @Override
    public List<MemberAccount> findAll() {
        return memberRepository.findAll();
    }

    @Override
    public void chargeMembershipCard(MemberAccount memberAccount, double amount, String creditCard) throws AccountNotFoundException, PaymentException {
        if (memberAccount.getId() == null || findById(memberAccount.getId()).isEmpty())
            throw new AccountNotFoundException();
        if (bank.pay(creditCard, amount)) memberAccount.setBalance(memberAccount.getBalance() + amount);
        else throw new PaymentException();
    }
}
