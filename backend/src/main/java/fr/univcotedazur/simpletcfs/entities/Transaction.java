package fr.univcotedazur.simpletcfs.entities;


import org.apache.tomcat.jni.Local;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public abstract class Transaction {

    public LocalDate getDate() {
        return date;
    }

    public UUID getId() {
        return id;
    }

    public MemberAccount getMemberAccount() {
        return memberAccount;
    }

    public Shop getShop() {
        return shop;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setMemberAccount(MemberAccount memberAccount) {
        this.memberAccount = memberAccount;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    LocalDate date;

    UUID id;

    MemberAccount memberAccount;

    Shop shop;

}
