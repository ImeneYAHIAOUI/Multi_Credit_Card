package fr.univcotedazur.simpletcfs.cli.model;

public class CliShop {

    public Long id;
    public String name;
    public String address;
    public  CliShop(String name, String address) {
        this.name = name;
        this.address = address;
    }
    public Long getId() {
        return id;
    }
public void setId(Long id) {
        this.id = id;
    }
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

    @Override
    public String toString() {
        return "Shop id : "+id +", name : "+name+", address : "+address;

    }
}
