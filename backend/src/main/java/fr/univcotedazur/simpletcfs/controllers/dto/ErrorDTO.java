package fr.univcotedazur.simpletcfs.controllers.dto;

import lombok.Getter;
import lombok.Setter;

public class ErrorDTO {

    @Getter
    @Setter
    private String error;
    @Getter
    @Setter
    private String details;
}
