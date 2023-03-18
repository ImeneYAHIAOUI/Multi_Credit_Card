package fr.univcotedazur.simpletcfs.cli.model;



public class CliParking {

    private String carRegistrationNumber;

    private String mail;

    private int parkingSpotNumber;

    public CliParking(String carRegistrationNumber, String mail, int parkingSpotNumber) {
        this.carRegistrationNumber = carRegistrationNumber;
        this.mail = mail;
        this.parkingSpotNumber = parkingSpotNumber;
    }

    public int getParkingSpotNumber() {
        return parkingSpotNumber;
    }

    public void setParkingSpotNumber(int parkingSpotNumber) {
        this.parkingSpotNumber = parkingSpotNumber;
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

    @Override
    public String toString()
    {
        return carRegistrationNumber + ", " + mail +", "+ parkingSpotNumber;
    }
}
