package fr.univcotedazur.multicredit.cli.model;

public class CliShop {

    private Long id;
    private String name;
    private String address;

    public CliShop(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Shop id : " + id + ", name : " + name + ", address : " + address;
    }
}
