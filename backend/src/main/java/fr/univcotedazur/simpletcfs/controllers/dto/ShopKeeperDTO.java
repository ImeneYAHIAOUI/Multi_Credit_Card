package fr.univcotedazur.simpletcfs.controllers.dto;

public class ShopKeeperDTO extends AccountDTO {
    public long ShopId;
    public ShopKeeperDTO(long id,String name, String mail, String password, String birthDate) {
        super(id,name, mail, password, birthDate);
    }

    public void setShopId(long id){
        ShopId=id;
    }

    public long getShopId() {
        return ShopId;
    }

}
