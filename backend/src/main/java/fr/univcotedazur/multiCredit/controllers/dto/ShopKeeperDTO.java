package fr.univcotedazur.multiCredit.controllers.dto;

public class ShopKeeperDTO extends AccountDTO {
    public long shopId;
    public ShopKeeperDTO(long id,String name, String mail, String password, String birthDate) {
        super(id,name, mail, password, birthDate);
    }

    public void setShopId(long id){
        shopId =id;
    }

    public long getShopId() {
        return shopId;
    }

}
