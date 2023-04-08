package fr.univcotedazur.multicredit.cli.model;

public class CliShopKeeper extends CliAccount {
    private long shopId;

    public CliShopKeeper(String name, String mail, String password, String birthDate) {
        super(name, mail, password, birthDate);
    }

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long id) {
        this.shopId = id;
    }

    @Override
    public String toString() {
        return "Shop id : " + shopId + ", name : " + this.getName() + ", mail : " + getMail()
                + ", birthDate : " + getBirthDate();
    }
}
