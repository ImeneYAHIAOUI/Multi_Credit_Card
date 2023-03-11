package fr.univcotedazur.simpletcfs.entities;




import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.*;


@Entity
public class Shop {

    @Id
    @GeneratedValue
    @Column(name = "Shop_id", nullable = false)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    public Shop(String name, String address) {
        this.name = name;
        this.address = address;
    }
    public Shop() {

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}

