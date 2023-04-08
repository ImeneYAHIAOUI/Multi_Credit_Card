package fr.univcotedazur.multicredit.controllers.dto;

public class AdminDTO extends AccountDTO {

    public AdminDTO(long id, String name, String mail, String password, String birthDate) {
        super(id, name, mail, password, birthDate);
    }
}
