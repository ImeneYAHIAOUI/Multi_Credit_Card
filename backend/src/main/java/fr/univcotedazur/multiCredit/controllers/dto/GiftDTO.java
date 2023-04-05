package fr.univcotedazur.multiCredit.controllers.dto;

public class GiftDTO {

    private Long giftId;

    private ShopDTO shop;

    private int pointsNeeded;
    private String status;

    private String description;

    public GiftDTO(Long giftId,ShopDTO shop,int pointsNeeded, String description, String status) {
        this.giftId = giftId;
        this.status=status;
        this.shop=shop;
        this.pointsNeeded = pointsNeeded;
        this.description = description;
    }
    public GiftDTO(int pointsNeeded, String description, String status) {
        this.status=status;
        this.pointsNeeded = pointsNeeded;
        this.description = description;
    }
    public GiftDTO(){

    }
    public Long getGiftId() {
        return giftId;
    }

    public int getPointsNeeded() {
        return pointsNeeded;
    }

    public String getDescription() {
        return description;
    }

    public void setShop(ShopDTO shop) {
        this.shop = shop;
    }

    public String getStatus() {
        return status;
    }
    public ShopDTO getShop() {
        return shop;
    }
    public String toString(){
        return "GiftId : "+giftId+" PointsNeeded : "+pointsNeeded+" Description : "+description+" Status : "+status;
    }

}
