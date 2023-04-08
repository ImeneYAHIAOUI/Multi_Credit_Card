package fr.univcotedazur.multiCredit.controllers.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class ParkingDTO {

    @Pattern(regexp = "^(.+)@(.+)$", message = "email should be valid")
    private String mail;

    @NotBlank(message = "car registration number should not be blank")
    private String carRegistrationNumber;
    private String startTime;
    private String endTime;
    private int parkingSpotNumber;

    public ParkingDTO(String carRegistrationNumber, String mail, int parkingSpotNumber) {
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
