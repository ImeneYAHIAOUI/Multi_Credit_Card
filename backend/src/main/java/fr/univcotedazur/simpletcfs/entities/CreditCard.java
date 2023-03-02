package fr.univcotedazur.simpletcfs.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
public class CreditCard {
    @Getter
    @Setter
    String owner;
    @Getter
    @Setter
    String number;
    @Getter
    @Setter
    LocalDate expirationDate;
    @Getter
    @Setter
    String cvv;
    public String ToString(){
        return "Owner: " + owner + " Number: " + number + " Expiration Date: " + expirationDate + " CVV: " + cvv;
    }
}
