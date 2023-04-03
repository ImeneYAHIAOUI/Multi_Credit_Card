package fr.univcotedazur.simpletcfs.entities;



import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Entity
public class ShopKeeperAccount extends Account{
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "shopKeeperAccount", nullable = false)
    private Shop shop;
    public ShopKeeperAccount( String name, String mail, String phoneNumber, LocalDate birthday, Shop shop) {
        super(name, mail, phoneNumber, birthday);
        this.shop = shop;
    }

    public ShopKeeperAccount() {

    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
