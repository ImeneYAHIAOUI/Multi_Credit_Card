package fr.univcotedazur.multiCredit.controllers.dto;

import javax.validation.constraints.Pattern;

public class UpdateStatusDTO {

    @Pattern(regexp = "^(.+)@(.+)$", message = "email should be valid")
    private String mail;
    private String status;

    public UpdateStatusDTO(String mail, String status) {
        this.mail = mail;
        this.status = status;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
