package fr.univcotedazur.simpletcfs.entities;

public class Mail {
    private String mailContent;
    private String subject;

    public Mail(String mailContent, String subject) {
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
}
