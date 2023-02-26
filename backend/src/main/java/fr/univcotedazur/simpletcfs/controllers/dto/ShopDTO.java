package fr.univcotedazur.simpletcfs.controllers.dto;

import fr.univcotedazur.simpletcfs.entities.Gift;
import fr.univcotedazur.simpletcfs.entities.Planning;
import fr.univcotedazur.simpletcfs.entities.Product;
import fr.univcotedazur.simpletcfs.entities.WeekDay;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Map;

public class ShopDTO {
    @NotBlank(message = "name should not be blank")
    @Setter
    @Getter
    private String name;
    @Pattern(regexp = "^(.+)@(.+)$", message = "address should be valid")
    @Setter
    @Getter
    private String address;
    @Getter
    private Map<WeekDay, Planning> planning;
    @Getter
    private List<Product> productList;
    @Getter
    private List<Gift> giftList;
    public ShopDTO(String name, String address, Map<WeekDay, Planning> planning, List<Product> productList, List<Gift> giftList) {
        this.name = name;
        this.address = address;
        this.planning = planning;
        this.productList = productList;
        this.giftList = giftList;
    }

}
