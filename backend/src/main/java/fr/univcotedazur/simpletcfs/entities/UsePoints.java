package fr.univcotedazur.simpletcfs.entities;

import lombok.Getter;
import lombok.Setter;

public class UsePoints extends Transaction{
    @Getter
    @Setter
    public int useddPoints;
    @Setter
    @Getter
    public Gift gift;
}
