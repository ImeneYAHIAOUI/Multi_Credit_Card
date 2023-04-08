package fr.univcotedazur.multicredit.controllers.dto;

public class PlanningDTO {
    private String closingHours;
    private String openingHours;
    private String dayWorking;

    public String getClosingHours() {
        return closingHours;
    }

    public void setClosingHours(String closingHours) {
        this.closingHours = closingHours;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public String getDayWorking() {
        return dayWorking;
    }

    public void setDayWorking(String dayWorking) {
        this.dayWorking = dayWorking;
    }
}
