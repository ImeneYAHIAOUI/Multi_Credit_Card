package fr.univcotedazur.multicredit.cli.model;


public class CliAdmin extends CliAccount {
    public CliAdmin(String name, String mail, String password, String birthDate) {
        super(name, mail, password, birthDate);
    }

    @Override
    public String toString() {
        return "Admin : id: " + getId() + " name:" + getName() + " mail:" + getMail() + " birthDate:" + getBirthDate();
    }
}
