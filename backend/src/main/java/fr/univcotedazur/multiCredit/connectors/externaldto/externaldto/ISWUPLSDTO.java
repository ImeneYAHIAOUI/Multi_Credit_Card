package fr.univcotedazur.multiCredit.connectors.externaldto.externaldto;

public class ISWUPLSDTO {

    private String carRegNum;
    private int parkingSpotNumber;
    private long parkingDateTime;
    private long parkingDuration;
    private long parkingEndDateTime;

    public ISWUPLSDTO(String carRegNum, int parkingSpotNumber, long parkingDateTime, long parkingDuration) {
        this.carRegNum = carRegNum;
        this.parkingSpotNumber = parkingSpotNumber;
        this.parkingDateTime = parkingDateTime;
        this.parkingDuration = parkingDuration;
    }

    public long getParkingEndDateTime() {
        return parkingEndDateTime;
    }

    public void setParkingEndDateTime(long parkingEndDateTime) {
        this.parkingEndDateTime = parkingEndDateTime;
    }

    public String getCarRegNum() {
        return carRegNum;
    }

    public void setCarRegNum(String carRegNum) {
        this.carRegNum = carRegNum;
    }

    public int getParkingSpotNumber() {
        return parkingSpotNumber;
    }

    public void setParkingSpotNumber(int parkingSpotNumber) {
        this.parkingSpotNumber = parkingSpotNumber;
    }

    public long getParkingDateTime() {
        return parkingDateTime;
    }

    public void setParkingDateTime(long parkingDateTime) {
        this.parkingDateTime = parkingDateTime;
    }

    public long getParkingDuration() {
        return parkingDuration;
    }

    public void setParkingDuration(long parkingDuration) {
        this.parkingDuration = parkingDuration;
    }
}
