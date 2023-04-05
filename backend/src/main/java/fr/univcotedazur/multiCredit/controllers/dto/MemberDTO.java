package fr.univcotedazur.multiCredit.controllers.dto;


public class MemberDTO extends AccountDTO{
    int points;
    double balance;
    String status;
    String membershipCardNumber;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMembershipCardNumber() {
        return membershipCardNumber;
    }

    public void setMembershipCardNumber(String membershipCardNumber) {
        this.membershipCardNumber = membershipCardNumber;
    }

    public MemberDTO(long id, String name, String mail, String password, String birthDate) {
        super(id,name, mail, password, birthDate);
    }




}
