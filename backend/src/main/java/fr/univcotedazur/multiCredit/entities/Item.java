package fr.univcotedazur.multiCredit.entities;


import javax.persistence.*;

@Entity
public class Item {
    @Id
    @GeneratedValue
    private Long id;
    private int amount;
    @OneToOne
    @JoinColumn(name = "Product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "Transaction_id")
    private Purchase purchase;

    public Item() {
    }

    public Item(Product product, int amount) {
        this.product = product;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setPurchase(Purchase tran) {
        this.purchase = tran;
    }
}
