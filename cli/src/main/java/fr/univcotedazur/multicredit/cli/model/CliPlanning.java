package fr.univcotedazur.multicredit.cli.model;

public class CliPlanning {

    private final String closingHours;
    private final String openingHours;
    private final String dayWorking;

    public CliPlanning(String dayWorking, String openingHours, String closingHours) {
        this.dayWorking = dayWorking;
        this.openingHours = openingHours;
        this.closingHours = closingHours;
    }

    public String getClosingHours() {
        return closingHours;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public String getDayWorking() {
        return dayWorking;
    }
}
