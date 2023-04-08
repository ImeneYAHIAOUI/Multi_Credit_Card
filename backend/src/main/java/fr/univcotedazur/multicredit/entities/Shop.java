package fr.univcotedazur.multicredit.entities;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Entity
public class Shop {

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "shop")
    List<Planning> planningList = new ArrayList<>();
    @Id
    @GeneratedValue
    @Column(name = "Shop_id", nullable = false)
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String address;
    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "shop")
    private List<Product> productList = new ArrayList<>();
    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "shop")
    private List<Gift> giftList = new ArrayList<>();

    @OneToOne(mappedBy = "shop", cascade = CascadeType.REMOVE)
    private ShopKeeperAccount shopKeeperAccount;

    @OneToMany(targetEntity = Transaction.class, mappedBy = "shop", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Transaction> transactions = new ArrayList<>();

    public Shop(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Shop() {
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<Gift> getGiftList() {
        return giftList;
    }

    public void setGiftList(List<Gift> giftList) {
        this.giftList = giftList;
    }

    public void addProduct(Product product) {
        productList.add(product);
    }

    public void removeProduct(Product product) {
        productList.remove(product);
    }

    public void addGift(Gift gift) {
        giftList.add(gift);
    }

    public void addPlanning(Planning planning) {
        planningList.add(planning);
    }

    public List<Planning> getPlanningList() {
        return planningList;
    }

    public void setPlanningList(List<Planning> planningList) {
        this.planningList = planningList;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ShopKeeperAccount getShopKeeperAccount() {
        return shopKeeperAccount;
    }

    public void setShopKeeperAccount(ShopKeeperAccount shopKeeperAccount) {
        this.shopKeeperAccount = shopKeeperAccount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shop shop)) return false;
        return Objects.equals(name, shop.name) &&
                Objects.equals(address, shop.address);
    }

    @Override
    public int hashCode() {
        int result = planningList != null ? planningList.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (productList != null ? productList.hashCode() : 0);
        result = 31 * result + (giftList != null ? giftList.hashCode() : 0);
        result = 31 * result + (shopKeeperAccount != null ? shopKeeperAccount.hashCode() : 0);
        result = 31 * result + (transactions != null ? transactions.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Shop" + "{\n" +
                "id= " + id + "\n" +
                "name= " + name + "\n" +
                "address= " + address + "\n" +
                "Gifts = " + giftList.stream().map(Gift::toString)
                .collect(Collectors.joining("-", "{", "}")) + "\n" +
                "Products =" + productList.stream().map(Product::toString)
                .collect(Collectors.joining("-", "{", "}")) + "\n" +
                "Planning =" + planningList.stream().map(Planning::toString)
                .collect(Collectors.joining("-", "{", "}")) + "\n" +
                "}"
                ;
    }
}

