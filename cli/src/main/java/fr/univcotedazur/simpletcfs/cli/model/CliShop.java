package fr.univcotedazur.simpletcfs.cli.model;

public class CliShop {
    public  CliShop(String name, String address) {
        this.name = name;
        this.address = address;

    }
    public String name;
    public String address;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
