package fr.univcotedazur.multicredit.entities;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Transaction {
    LocalDate date;
    @Id
    @GeneratedValue
    @Column(name = "Transaction_id", nullable = false)
    private Long idTransaction;
    @ManyToOne
    @NotNull
    @JoinColumn(name = "Account_id")
    private MemberAccount memberAccount;
    @ManyToOne
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    protected Transaction() {
    }

    protected Transaction(LocalDate date, MemberAccount memberAccount) {
        this.date = date;
        this.memberAccount = memberAccount;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getId() {
        return idTransaction;
    }

    public void setId(Long id) {
        this.idTransaction = id;
    }

    public MemberAccount getMemberAccount() {
        return memberAccount;
    }

    public void setMemberAccount(MemberAccount memberAccount) {
        this.memberAccount = memberAccount;
    }
}
