package fr.univcotedazur.simpletcfs.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public abstract class Transaction {
    @Setter
    @Getter
    Date date;
}
