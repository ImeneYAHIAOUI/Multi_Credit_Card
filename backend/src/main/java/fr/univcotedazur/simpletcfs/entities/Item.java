package fr.univcotedazur.simpletcfs.entities;



public class Item {

    public int amount;

    public Product product;
    public  Item(Product product, int amount){
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
}
