package fr.univcotedazur.multicredit.cli.model;

public class CliPlanning {

    private String closingHours;
    private String openingHours;
    private String dayWorking;

    public String getClosingHours() {
        return closingHours;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public String getDayWorking() {
        return dayWorking;
    }
    public CliPlanning(String dayWorking, String openingHours, String closingHours) {
        this.dayWorking = dayWorking;
        this.openingHours = openingHours;
        this.closingHours = closingHours;
    }
}
