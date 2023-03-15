package fr.univcotedazur.simpletcfs.controllers.dto;

import fr.univcotedazur.simpletcfs.entities.Gift;
import fr.univcotedazur.simpletcfs.entities.Planning;
import fr.univcotedazur.simpletcfs.entities.Product;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

public class ShopDTO {
    @NotBlank(message = "name should not be blank")
    private String name;
    @NotBlank(message = "address should not be blank")
    private String address;
    private Long id;
    public Long getId() {
        return id;
    }
    public ShopDTO(Long id,String name, String address) {
        this.id=id;
        this.name = name;
        this.address = address;
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


}
