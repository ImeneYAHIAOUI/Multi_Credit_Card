package fr.univcotedazur.multicredit.cli.model;



public class CliParking {

    private String carRegistrationNumber;

    private String mail;

    private int parkingSpotNumber;

    private String startTime;
    private String endTime;

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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString()
    {
        return
                "parking {"+
                        "car registration number : " + carRegistrationNumber +
                        ", " +
                        "mail : " + mail +
                        ", " +
                        "parking spot number : " + parkingSpotNumber +
                        ", " +
                        "starting time : " + startTime +
                        ", " +
                      "ending time : " + endTime +
                        "}";
    }
}
