package fr.univcotedazur.simpletcfs.cli.commands;

import fr.univcotedazur.simpletcfs.cli.CliContext;
import fr.univcotedazur.simpletcfs.cli.model.CliProduct;
import fr.univcotedazur.simpletcfs.cli.model.CliShop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestTemplate;

@ShellComponent
public class CatalogCommands {
    public static final String BASE_URI = "/catalog";
    @Autowired
    RestTemplate restTemplate;

    private final CliContext cliContext;

    public CatalogCommands(CliContext cliContext) {
        this.cliContext = cliContext;
    }

    @ShellMethod("add product (add-product SHOP_ID PRODUCT_NAME PRODUCT_PRICE PRODUCT_POINTS PRODUCT_DISCOUNT) )")
    public String addProduct(Long id,String name, Double price,int points,Double discount) {
        CliProduct p= restTemplate.postForObject(BASE_URI +"/"+id+"/"+"Products/add", new CliProduct(name,points,price,discount), CliProduct.class);
        return "Product added successfully : "+p.toString();
    }
}
