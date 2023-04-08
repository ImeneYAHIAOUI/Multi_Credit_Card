package fr.univcotedazur.multicredit.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

public class Mail {

    private String sender;
    private String mailContent;
    private String subject;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
