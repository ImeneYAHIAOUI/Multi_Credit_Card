package fr.univcotedazur.multicredit.controllers.dto;

public class UsePointDTO extends TransactionDTO {
    private int pointsUsed;
    private long gift;

    public UsePointDTO(long id, String date, long memberAccount, long shop, int pointsUsed, long gift) {
        super(id, date, memberAccount, shop);
        this.pointsUsed = pointsUsed;
        this.gift = gift;
    }

    public int getPointsUsed() {
        return pointsUsed;
    }

    public void setPointsUsed(int pointsUsed) {
        this.pointsUsed = pointsUsed;
    }

    public long getGift() {
        return gift;
    }

    public void setGift(long gift) {
        this.gift = gift;
    }
}
