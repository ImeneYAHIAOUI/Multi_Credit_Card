package fr.univcotedazur.simpletcfs.connectors.externaldto.externaldto;

public class ISWUPLSDTO {

    private String carRegistrationNumber;
    private int parkingSpotNumber;
    private long startingTime;
    private long duration;

    public ISWUPLSDTO(String carRegistrationNumber, int parkingSpotNumber, long startingTime, long duration) {
        this.carRegistrationNumber = carRegistrationNumber;
        this.parkingSpotNumber = parkingSpotNumber;
        this.startingTime = startingTime;
        this.duration = duration;
    }

    public String getCarRegistrationNumber() {
        return carRegistrationNumber;
    }

    public void setCarRegistrationNumber(String carRegistrationNumber) {
        this.carRegistrationNumber = carRegistrationNumber;
    }

    public int getParkingSpotNumber() {
        return parkingSpotNumber;
    }

    public void setParkingSpotNumber(int parkingSpotNumber) {
        this.parkingSpotNumber = parkingSpotNumber;
    }

    public long getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(int startingTime) {
        this.startingTime = startingTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
