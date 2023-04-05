package fr.univcotedazur.multiCredit.cli.model;

public class CliForm {
    private String name;
    private String mail;
    private String password;
    private String birthDate;

    public String getName() {
        return name;
    }

    public CliForm(String name, String mail, String password, String birthDate) {
        this.name = name;
        this.mail = mail;
        this.password = password;
        this.birthDate = birthDate;
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
