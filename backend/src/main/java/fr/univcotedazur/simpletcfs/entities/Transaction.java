package fr.univcotedazur.simpletcfs.entities;


import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Transaction {
    LocalDate date;
    @Id
    @GeneratedValue
    @Column(name="Transaction_id", nullable=false)
    private Long idTransaction ;
    @ManyToOne
    @NotNull
    @JoinColumn(name = "Account_id")
    MemberAccount memberAccount;
    public Transaction() {
    }
    public Transaction(LocalDate date, MemberAccount memberAccount) {
        this.date = date;
        this.memberAccount = memberAccount;
    }

    public LocalDate getDate() {
        return date;
    }

    public Long getId() {
        return idTransaction;
    }

    public MemberAccount getMemberAccount() {
        return memberAccount;
    }


    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setId(Long id) {
        this.idTransaction = id;
    }

    public void setMemberAccount(MemberAccount memberAccount) {
        this.memberAccount = memberAccount;
    }




}
