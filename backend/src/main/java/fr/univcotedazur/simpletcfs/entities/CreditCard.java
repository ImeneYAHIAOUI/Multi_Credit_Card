package fr.univcotedazur.simpletcfs.entities;

import lombok.AllArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
public class CreditCard {
    String owner;
    String number;
    LocalDate expirationDate;
    String cvv;
}
