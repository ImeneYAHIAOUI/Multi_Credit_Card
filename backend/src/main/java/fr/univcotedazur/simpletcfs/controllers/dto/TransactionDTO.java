package fr.univcotedazur.simpletcfs.controllers.dto;

import fr.univcotedazur.simpletcfs.entities.Shop;

public class TransactionDTO {
    private long id;
    private String date;
    private long memberAccount;

    private long shop;


    public long getShop() {
        return shop;
    }

    public void setShop(long shop) {
        this.shop = shop;
    }

    public TransactionDTO(long id, String date, long memberId, long shop) {
        this.id = id;
        this.date = date;
        this.memberAccount = memberId;
        this.shop = shop;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMemberAccount() {
        return memberAccount;
    }

    public void setMemberAccount(long memberAccount) {
        this.memberAccount = memberAccount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



}
