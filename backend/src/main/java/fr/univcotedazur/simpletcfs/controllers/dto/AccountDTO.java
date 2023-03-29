package fr.univcotedazur.simpletcfs.controllers.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class AccountDTO {


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private long id;

    @NotBlank(message = "name should not be blank")

    private String name;

    @Pattern(regexp = "^(.+)@(.+)$", message = "email should be valid")

    private String mail;

    @Pattern(regexp = ".{8,20}", message = "password should be at least 8 characters long and at most 20 characters long")

    private String password;

    @NotEmpty(message = "BirthDate should not be empty")

    private String birthDate;

    public AccountDTO(long id,String name, String mail, String password, String birthDate) {
        this.id=id;
        this.name = name;
        this.mail = mail;
        this.password = password;
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
}
