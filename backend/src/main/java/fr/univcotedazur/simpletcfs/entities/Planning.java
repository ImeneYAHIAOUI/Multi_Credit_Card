package fr.univcotedazur.simpletcfs.entities;



import java.time.LocalTime;

public class Planning {

    private LocalTime OpeningHours;

    private LocalTime ClosingHours;
    public Planning(LocalTime openingHours, LocalTime closingHours) {
        OpeningHours = openingHours;
        ClosingHours = closingHours;
    }

    public LocalTime getOpeningHours() {
        return OpeningHours;
    }

    public void setOpeningHours(LocalTime openingHours) {
        OpeningHours = openingHours;
    }

    public LocalTime getClosingHours() {
        return ClosingHours;
    }

    public void setClosingHours(LocalTime closingHours) {
        ClosingHours = closingHours;
    }
}
