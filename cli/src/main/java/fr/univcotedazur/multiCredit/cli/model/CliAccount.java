package fr.univcotedazur.multiCredit.cli.model;



// A cli side class being equivalent to the backend AccountDTO, in terms of attributes
// so that the automatic JSON (de-)/serialization will make the two compatible on each side

public class CliAccount {



    private long id;
    private String name;

    private String mail;

    private String password;

    private String birthDate;

    public CliAccount(String name, String mail, String password, String birthDate) {
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
