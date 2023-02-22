package fr.univcotedazur.simpletcfs.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

public abstract class Transaction {
    @Setter
    @Getter
    Date date;
    @Setter
    @Getter
    UUID id;
}
