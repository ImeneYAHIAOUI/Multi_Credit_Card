package fr.univcotedazur.simpletcfs.cli.model;



public class CliParking {

    private String carRegistrationNumber;

    private String mail;

    public CliParking(String carRegistrationNumber, String mail) {
        this.carRegistrationNumber = carRegistrationNumber;
        this.mail = mail;
    }

    public String getCarRegistrationNumber() {
        return carRegistrationNumber;
    }

    public void setCarRegistrationNumber(String carRegistrationNumber) {
        this.carRegistrationNumber = carRegistrationNumber;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
