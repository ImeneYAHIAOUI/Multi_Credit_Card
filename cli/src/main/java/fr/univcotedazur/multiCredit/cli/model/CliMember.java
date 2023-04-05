package fr.univcotedazur.multiCredit.cli.model;



public class CliMember extends CliAccount{
    public CliMember( String name, String mail, String password, String birthDate) {
        super( name, mail, password, birthDate);
    }



    private int points;
    private double balance;
    private String status;

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

    private String membershipCardNumber;


    @Override
    public String toString() {
        String str = "member { id="+getId()+", name=" + getName() + ", mail=" + getMail() + ", password=" + getPassword() + ", birthDate=" + getBirthDate();
        if(membershipCardNumber != null){
            str += ", membershipCardNumber=" + membershipCardNumber+", points=" + points + ", balance=" + balance + ", status=" + status;
        }
        str += " }";
        return str;
    }





}
