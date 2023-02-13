package fr.univcotedazur.simpletcfs.controllers.dto;

import fr.univcotedazur.simpletcfs.entities.MembershipCard;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.UUID;

public class MemberDTO extends AccountDTO{

    @NotBlank(message = "membershipCard should not be blank")
    @Setter
    @Getter
    private String membershipCard;

    @Builder
    public MemberDTO(UUID id, String name, String mail, String password, LocalDate birthDate, String membershipCard) {
        super(id, name, mail, password, birthDate);
        this.membershipCard = membershipCard;
    }
}
