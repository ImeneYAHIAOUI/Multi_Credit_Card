package fr.univcotedazur.multiCredit.cli.model;

public class CliUpdateStatus {
    private String mail;
    private String status;

    public CliUpdateStatus(String mail, String status) {
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
