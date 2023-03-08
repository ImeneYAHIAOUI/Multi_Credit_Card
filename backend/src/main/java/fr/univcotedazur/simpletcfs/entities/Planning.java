package fr.univcotedazur.simpletcfs.entities;



import io.cucumber.java.af.En;

import javax.persistence.*;
import java.time.LocalTime;


@Entity
public class Planning {


    @Id
    @GeneratedValue
    private Long Id;
    private LocalTime OpeningHours;
    @ManyToOne
    @JoinColumn(name="Shop_id", nullable=false)
    private Shop  shop;

    private LocalTime ClosingHours;
    public Planning(){

    }
    public Long getId() {
        return Id;
    }
    public Planning(LocalTime openingHours, LocalTime closingHours) {
        OpeningHours = openingHours;
        ClosingHours = closingHours;
    }
    public Planning(Shop shop,LocalTime openingHours, LocalTime closingHours) {
        shop=shop;
        OpeningHours = openingHours;
        ClosingHours = closingHours;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
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
