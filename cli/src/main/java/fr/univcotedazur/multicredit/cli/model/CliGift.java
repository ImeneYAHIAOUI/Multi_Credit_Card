package fr.univcotedazur.multicredit.cli.model;

public class CliGift {

    private Long giftId;

    private CliShop shop;

    private final int pointsNeeded;
    private final String status;

    private final String description;

    public CliGift(int pointsNeeded, String description, String status) {
        this.status = status;
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

    public void setGiftId(Long giftId) {
        this.giftId = giftId;
    }

    public int getPointsNeeded() {
        return pointsNeeded;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "CliGift{" +
                "giftId=" + giftId +
                ", shop=" + shop +
                ", pointsNeeded=" + pointsNeeded +
                ", status='" + status + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
