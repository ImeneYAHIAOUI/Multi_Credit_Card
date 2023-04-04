package fr.univcotedazur.simpletcfs.cli.model;
public class CliMail {
    private String sender;
    private String mailContent;
    private String subject;

    public CliMail(String sender, String mailContent, String subject) {
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

}
