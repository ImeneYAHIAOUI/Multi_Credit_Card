package fr.univcotedazur.simpletcfs.entities;




import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.*;
import java.util.Map;
import java.util.UUID;


@Entity
public class Shop {

    @Id
    @GeneratedValue
    @Column(name="Shop_id", nullable=false)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @OneToMany( cascade = CascadeType.ALL)
    Map<WeekDay, Planning> planning=new HashMap<WeekDay, Planning>();

    @OneToMany
    public List<Product> productList=new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL)
    public List<Gift> giftList=new ArrayList<>();


    public Shop(String name, String address, Map<WeekDay, Planning> planning, List<Product> products, List<Gift> gifts) {

        this.name = name;
        this.address = address;
        this.planning = planning;
        productList=products;
        giftList=gifts;
    }

    public Shop() {

    }

    public void addProduct(Product product){
        productList.add(product);
    }
    public void removeProduct(Product product){
        productList.remove(product);
    }
public void addGift(Gift gift){
        giftList.add(gift);
    }
    public Long getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Map<WeekDay, Planning> getPlanning() {
        return planning;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public List<Gift> getGiftList() {
        return giftList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPlanning(Map<WeekDay, Planning> planning) {
        this.planning = planning;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public void setGiftList(List<Gift> giftList) {
        this.giftList = giftList;
    }
}

