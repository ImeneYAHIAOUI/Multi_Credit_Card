package fr.univcotedazur.simpletcfs.controllers.dto;

import fr.univcotedazur.simpletcfs.entities.MembershipCard;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.UUID;

public class MemberDTO extends AccountDTO{

    public MemberDTO( String name, String mail, String password, String birthDate) {
        super(name, mail, password, birthDate);
    }

}
