package fr.univcotedazur.multiCredit.controllers.dto;

public class UsePointDTO extends TransactionDTO{
    private int pointsUsed;
    private long Gift;

    public UsePointDTO(long id, String date, long memberAccount,long shop,int pointsUsed, long Gift){
        super(id,date,memberAccount,shop);
        this.pointsUsed = pointsUsed;
        this.Gift = Gift;
    }

    public int getPointsUsed() {
        return pointsUsed;
    }

    public void setPointsUsed(int pointsUsed) {
        this.pointsUsed = pointsUsed;
    }

    public long getGift() {
        return Gift;
    }

    public void setGift(long gift) {
        Gift = gift;
    }
}
