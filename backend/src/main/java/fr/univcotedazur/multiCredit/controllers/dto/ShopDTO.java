package fr.univcotedazur.multiCredit.controllers.dto;

public class ShopDTO {
    private String name;
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
    public ShopDTO() {
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
