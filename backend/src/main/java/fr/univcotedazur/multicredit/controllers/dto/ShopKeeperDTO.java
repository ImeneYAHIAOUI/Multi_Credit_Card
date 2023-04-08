package fr.univcotedazur.multicredit.controllers.dto;

public class ShopKeeperDTO extends AccountDTO {
    private long shopId;

    public ShopKeeperDTO(long id, String name, String mail, String password, String birthDate) {
        super(id, name, mail, password, birthDate);
    }

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long id) {
        shopId = id;
    }
}
