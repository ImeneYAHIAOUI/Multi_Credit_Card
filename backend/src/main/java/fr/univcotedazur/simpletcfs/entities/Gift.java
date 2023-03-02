package fr.univcotedazur.simpletcfs.entities;

import lombok.Getter;
import lombok.Setter;

public class Gift {
    @Getter
    @Setter
    public int pointsNeeded;
    @Getter
    @Setter
    public String description;
    @Getter
    @Setter
    public AccountStatus RequiredStatus;
}

