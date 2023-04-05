package fr.univcotedazur.multiCredit.entities;

import javax.persistence.*;
import java.time.LocalTime;
@Entity
public class Planning {

    @Id
    @GeneratedValue
    @Column(name="Planning_id", nullable=false)
    private Long id;
    private LocalTime closingHours;
    private LocalTime openingHours;
    @Enumerated(EnumType.STRING)
    private WeekDay dayWorking;
    @ManyToOne
    @JoinColumn(name="shop")
    private Shop shop;

    public Planning(){

    }
    public Long getId() {
        return id;
    }
    public Planning(WeekDay dayWorking, LocalTime openingHours, LocalTime closingHours) {
        this.dayWorking = dayWorking;
        this.openingHours = openingHours;
        this.closingHours = closingHours;
    }
    public WeekDay getDayWorking(){
        return dayWorking;
    }

    public void setDayWorking(WeekDay dayWorking) {
        this.dayWorking = dayWorking;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Shop getShop() {
        return shop;
    }

    public LocalTime getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(LocalTime openingHours) {
        this.openingHours = openingHours;
    }

    public LocalTime getClosingHours() {
        return closingHours;
    }

    public void setClosingHours(LocalTime closingHours) {
        this.closingHours = closingHours;
    }
    @Override
    public String toString(){
        return "Day: "+dayWorking+" Opening Hours: "+openingHours+" Closing Hours: "+closingHours;
    }
}