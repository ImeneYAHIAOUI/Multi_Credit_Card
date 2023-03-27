package fr.univcotedazur.simpletcfs.cli.model;

public class CliGift {

    private Long giftId;

    private CliShop shop;

    private int pointsNeeded;
    private String status;

    private String description;

    public CliGift( int pointsNeeded, String description, String status) {
        this.status=status;
        this.pointsNeeded = pointsNeeded;
        this.description = description;
    }
    public CliShop getShop() {
        return shop;
    }

    public String getDescription() {
        return description;
    }

    public Long getGiftId() {
        return giftId;
    }

    public int getPointsNeeded() {
        return pointsNeeded;
    }

    public String getStatus() {
        return status;
    }
}
