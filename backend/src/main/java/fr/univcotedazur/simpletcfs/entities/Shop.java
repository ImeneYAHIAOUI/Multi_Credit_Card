package fr.univcotedazur.simpletcfs.entities;




import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.*;
import java.util.stream.Collectors;


@Entity
public class Shop {

    @Id
    @GeneratedValue
    @Column(name = "Shop_id", nullable = false)
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String address;
    @OneToMany(cascade = CascadeType.REMOVE,fetch = FetchType.LAZY ,mappedBy = "shop")
    List<Planning> planningList =new ArrayList<>();
    @OneToMany(cascade = CascadeType.REMOVE,fetch = FetchType.LAZY,mappedBy = "shop")
    private List<Product> productList=new ArrayList<>();
    @OneToMany(cascade = CascadeType.REMOVE,fetch = FetchType.LAZY,mappedBy = "shop")
    private List<Gift> giftList=new ArrayList<>();


    public Shop(String name, String address) {
        this.name = name;
        this.address = address;
    }
    public Shop() {

    }


    public List<Gift> getGiftList() {
        return giftList;
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
    public void addPlanning(Planning planning){
        planningList.add(planning);
    }
    public List<Planning> getPlanningList() {
        return planningList;
    }

    public List<Product> getProductList() {
        return productList;
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
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shop shop)) return false;
        return Objects.equals(name, shop.name) &&
                Objects.equals(address, shop.address);
    }
    @Override
    public String toString(){
        return "Shop"+"{\n" +
                "id= " + id +"\n"+
                "name= " + name + "\n" +
                "address= " + address + "\n" +
                "Gifts = "+ giftList.stream().map(n -> n.getGiftId().toString())
                .collect(Collectors.joining("-", "{", "}"))+"\n"+
                 "Products ="+ productList.stream().map(n -> n.getId().toString())
                .collect(Collectors.joining("-", "{", "}"))+"\n"+
                "Planning ="+ planningList.stream().map(plan -> plan.toString())
                .collect(Collectors.joining("-", "{", "}"))+"\n"+
                "}"
                ;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}

