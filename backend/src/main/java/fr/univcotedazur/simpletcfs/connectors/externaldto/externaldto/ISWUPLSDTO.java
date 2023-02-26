package fr.univcotedazur.simpletcfs.connectors.externaldto.externaldto;

public class ISWUPLSDTO {

    private String carRegistrationNumber;
    private String startingTime;
    private String endingTime;
    public ISWUPLSDTO(String carRegistrationNumber, String startingTime, String endingTime)
    {
        this.carRegistrationNumber = carRegistrationNumber;
        this.startingTime=startingTime;
        this.endingTime=endingTime;
    }
    public String getCarRegistrationNumber()
    {
        return carRegistrationNumber;
    }

    public String getStartingTime()
    {
        return startingTime;
    }

    public void setStartingTime(String startingTime)
    {
        this.startingTime=startingTime;
    }

    public String getEndingTime()
    {
        return endingTime;
    }

    public void setEndingTime(String endingTime)
    {
        this.endingTime=endingTime;
    }

    public ISWUPLSDTO() {
    }
}
