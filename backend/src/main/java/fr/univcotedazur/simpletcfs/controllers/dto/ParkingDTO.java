package fr.univcotedazur.simpletcfs.controllers.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class ParkingDTO {

    @Pattern(regexp = "^(.+)@(.+)$", message = "email should be valid")
    private String mail;

    @NotBlank(message = "car registration number should not be blank")
    private String carRegistrationNumber;

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getParkingSpotNumber() {
        return parkingSpotNumber;
    }

    public void setParkingSpotNumber(int parkingSpotNumber) {
        this.parkingSpotNumber = parkingSpotNumber;
    }

    private int parkingSpotNumber;


    public ParkingDTO(String carRegistrationNumber, String mail, int parkingSpotNumber) {
        this.carRegistrationNumber = carRegistrationNumber;
        this.mail = mail;
        this.parkingSpotNumber = parkingSpotNumber;
    }


    public String getCarRegistrationNumber()
    {
        return carRegistrationNumber;
    }

    public void setCarRegistrationNumber(String carRegistrationNumber)
    {
        this.carRegistrationNumber = carRegistrationNumber;
    }

    public String getMail() {
        return mail;
    }
}
