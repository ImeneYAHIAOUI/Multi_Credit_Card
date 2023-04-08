package fr.univcotedazur.multiCredit.connectors.externaldto.externaldto;


import java.util.List;

public class MailSenderDTO {
    private String sender;
    private List<String> receivers;
    private String subject;
    private String mailContent;

    private String id;

    public MailSenderDTO() {
    }

    public MailSenderDTO(String sender, List<String> receivers, String subject, String mailContent) {
        this.sender = sender;
        this.receivers = receivers;
        this.subject = subject;
        this.mailContent = mailContent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public List<String> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<String> receivers) {
        this.receivers = receivers;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMailContent() {
        return mailContent;
    }

    public void setMailContent(String mailContent) {
        this.mailContent = mailContent;
    }
}
