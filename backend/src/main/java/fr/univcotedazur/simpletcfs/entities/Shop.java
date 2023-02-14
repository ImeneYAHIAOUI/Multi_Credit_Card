package fr.univcotedazur.simpletcfs.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.UUID;

public class Shop {
    @Getter
    private UUID id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String address;
    @Getter
    @Setter
    private Map<WeekDay, Planning> planning;
    public Shop(UUID id, String name, String address, Map<WeekDay, Planning> planning) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.planning = planning;
    }

}

