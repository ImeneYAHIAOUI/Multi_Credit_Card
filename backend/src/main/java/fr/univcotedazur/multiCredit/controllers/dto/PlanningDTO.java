package fr.univcotedazur.multiCredit.controllers.dto;

public class PlanningDTO {
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

    public void setClosingHours(String closingHours) {
        this.closingHours = closingHours;
    }

    public void setDayWorking(String dayWorking) {
        this.dayWorking = dayWorking;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }
}
