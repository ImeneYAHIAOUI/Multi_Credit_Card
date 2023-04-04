package fr.univcotedazur.simpletcfs.cli.model;

public class CliShopKeeper  extends CliAccount{
    private long ShopId;
    public   CliShopKeeper(  String name, String mail, String password, String birthDate) {
        super(  name, mail, password, birthDate);
    }


    public long getShopId() {
        return ShopId;
    }
     public void setShopId(long id){
        this.ShopId=id;
     }
}
