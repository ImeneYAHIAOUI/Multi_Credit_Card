package fr.univcotedazur.multiCredit.controllers.dto;

import javax.validation.constraints.NotBlank;

public class MailDTO {
    @NotBlank
    private String sender;
    @NotBlank
    private String mailContent;
    @NotBlank
    private String subject;
    private Long id;

    public MailDTO(String sender, String mailContent, String subject) {
        this.sender = sender;
        this.mailContent = mailContent;
        this.subject = subject;
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
