package fr.univcotedazur.simpletcfs.entities;



import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MemberAccount extends Account {

    private MembershipCard membershipCard;

    int points = 0;

    double balance = 0;


    AccountStatus status;

    List<Transaction> transactions;

    public MemberAccount(UUID id, String name, String mail, String password, LocalDate birthDate, int points, double balance) {
        super(id, name, mail,password, birthDate);
        this.membershipCard =  new MembershipCard(LocalDate.now(), LocalDate.now().plusYears(2));
        this.points = points;
        this.balance = balance;
        transactions= new ArrayList<Transaction>();
    }
    public void addTransaction(Transaction transaction){
        transactions.add(transaction);
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

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
