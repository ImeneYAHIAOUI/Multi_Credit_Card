package fr.univcotedazur.simpletcfs.cli.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CliUsePoints extends CliTransaction
{
    private int pointsUsed;
    private long gift;

    public long getGift() {
        return gift;
    }

    public void setGift(long gift) {
        this.gift = gift;
    }

    public int getPointsUsed() {
        return pointsUsed;
    }

    public void setPointsUsed(int pointsUsed) {
        this.pointsUsed = pointsUsed;
    }

    @JsonCreator
    public CliUsePoints(
            @JsonProperty("memberId")long memberId,
            @JsonProperty("shopId")long shopId,
            @JsonProperty("pointsUsed")int pointsUsed,
            @JsonProperty("gift")long gift) {
        super(memberId,shopId);
        this.pointsUsed = pointsUsed;
        this.gift = gift;
    }

    public CliUsePoints(
            long memberId,
            long shopId,
            long gift) {
        super(memberId,shopId);
        this.gift = gift;
    }

    @Override
    public String toString() {
        return "usePoints{" +
                "memberId=" + getMemberAccount() +
                ", shop=" + getShop() +
                ", pointsUsed=" + pointsUsed +
                ", gift=" + gift +
                '}';
    }



}
