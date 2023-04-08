package fr.univcotedazur.multiCredit.entities;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
public class MemberAccount extends Account {

    @OneToMany(targetEntity = Transaction.class, mappedBy = "memberAccount", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private final List<Transaction> transactions = new ArrayList<>();
    int points;
    double balance;
    AccountStatus status;
    @Embedded
    private MembershipCard membershipCard;

    public MemberAccount(String name, String mail, String password, LocalDate birthDate, int points, double balance) {
        super(name, mail, password, birthDate);
        this.membershipCard = new MembershipCard(LocalDate.now(), LocalDate.now().plusMonths(18));
        this.points = points;
        this.balance = balance;
    }

    public MemberAccount() {
    }


    public List<Transaction> getTransactions() {
        return transactions;
    }


    public MembershipCard getMembershipCard() {
        return membershipCard;
    }

    public void setMembershipCard(MembershipCard membershipCard) {
        this.membershipCard = membershipCard;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MemberAccount memberAccount)) return false;
        return Objects.equals(membershipCard, memberAccount.membershipCard)
                && Objects.equals(getName(), memberAccount.getName())
                && Objects.equals(getMail(), memberAccount.getMail())
                && Objects.equals(getPoints(), memberAccount.getPoints())
                && Objects.equals(getPassword(), memberAccount.getPassword())
                && Objects.equals(getBirthDate(), memberAccount.getBirthDate());
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = transactions.hashCode();
        result = 31 * result + points;
        temp = Double.doubleToLongBits(balance);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (membershipCard != null ? membershipCard.hashCode() : 0);
        return result;
    }
}