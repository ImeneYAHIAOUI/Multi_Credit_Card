package fr.univcotedazur.simpletcfs.entities;



import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
public class MemberAccount extends Account {

    @Embedded
    private MembershipCard membershipCard;

    @OneToMany( targetEntity=Transaction.class, mappedBy="memberAccount" ,cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    List<Transaction> transactions = new ArrayList<>();
    int points;
    double balance;
    AccountStatus status;

    public MemberAccount(String name, String mail, String password, LocalDate birthDate, int points, double balance) {
        super( name, mail,password, birthDate);
        this.membershipCard =  new MembershipCard(LocalDate.now(), LocalDate.now().plusYears(2));
        this.points = points;
        this.balance = balance;
    }

    public MemberAccount() {

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
        if (!(o instanceof MemberAccount memberAccount )) return false;
        return Objects.equals(membershipCard, memberAccount.membershipCard)
                && Objects.equals(getName(), memberAccount.getName())
                && Objects.equals(getMail(), memberAccount.getMail())
                && Objects.equals(getPoints(), memberAccount.getPoints())
                && Objects.equals(getPassword(), memberAccount.getPassword())
                && Objects.equals(getBirthDate(), memberAccount.getBirthDate());


    }

}
