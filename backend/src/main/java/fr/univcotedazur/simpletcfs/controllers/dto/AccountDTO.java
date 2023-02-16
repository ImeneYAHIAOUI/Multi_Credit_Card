package fr.univcotedazur.simpletcfs.controllers.dto;

import fr.univcotedazur.simpletcfs.entities.MembershipCard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
public class AccountDTO {



    @NotBlank(message = "name should not be blank")
    @Setter
    @Getter
    private String name;

    @Pattern(regexp = "^(.+)@(.+)$", message = "email should be valid")
    @Setter
    @Getter
    private String mail;

    @Pattern(regexp = ".{8,20}", message = "password should be at least 8 characters long and at most 20 characters long")
    @Setter
    @Getter
    private String password;

    @NotEmpty(message = "BirthDate should not be empty")
    @Setter
    @Getter
    private String birthDate;










}
