package fr.univcotedazur.multiCredit.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class Mail {

    @NotBlank
    private String sender;
    @NotBlank
    private String mailContent;
    @NotBlank
    private String subject;
    @Id
    @GeneratedValue
    private Long id;

    public Mail(String sender, String mailContent, String subject) {
        this.sender = sender;
        this.mailContent = mailContent;
        this.subject = subject;
    }

    public Mail() {

    }

    public String getMailContent() {
        return mailContent;
    }

    public void setMailContent(String mailContent) {
        this.mailContent = mailContent;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
