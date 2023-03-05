package fr.univcotedazur.simpletcfs.entities;



import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.LocalDate;


@Entity
public class MemberAccount extends Account {

    @OneToOne(cascade = CascadeType.ALL)
    private MembershipCard membershipCard;

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



}
