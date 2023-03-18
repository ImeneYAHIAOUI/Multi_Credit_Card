package fr.univcotedazur.simpletcfs.controllers.dto;

import fr.univcotedazur.simpletcfs.entities.WeekDay;


import java.time.LocalTime;

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
}
