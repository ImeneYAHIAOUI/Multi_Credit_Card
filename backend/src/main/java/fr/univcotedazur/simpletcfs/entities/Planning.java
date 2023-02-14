package fr.univcotedazur.simpletcfs.entities;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

public class Planning {
    @Getter
    @Setter
    private LocalTime OpeningHours;
    @Getter
    @Setter
    private LocalTime ClosingHours;
    public Planning(LocalTime openingHours, LocalTime closingHours) {
        OpeningHours = openingHours;
        ClosingHours = closingHours;
    }

}
