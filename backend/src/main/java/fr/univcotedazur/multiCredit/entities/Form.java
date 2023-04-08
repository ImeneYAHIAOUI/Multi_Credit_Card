package fr.univcotedazur.multiCredit.entities;


import java.time.LocalDate;


public class Form {

    private String name;
    private String mail;
    private String password;
    private LocalDate birthDate;

    public Form(String name, String mail, String password, LocalDate birthDate) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}

