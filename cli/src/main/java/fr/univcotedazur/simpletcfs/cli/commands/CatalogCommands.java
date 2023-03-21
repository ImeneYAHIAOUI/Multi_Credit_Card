package fr.univcotedazur.simpletcfs.cli.commands;

import fr.univcotedazur.simpletcfs.cli.CliContext;
import fr.univcotedazur.simpletcfs.cli.model.CliGift;
import fr.univcotedazur.simpletcfs.cli.model.CliProduct;
import fr.univcotedazur.simpletcfs.cli.model.CliShop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.GitProperties;
import org.springframework.http.*;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

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
        return "Product added successfully : ";
    }
    @ShellMethod("add gift (add-gift SHOP_ID POINTS_NEEDED DESCRIPTION STATUS) )")
    public String addGift(Long id, int points,String description,String status) {
        String[]    accountStatus = {"EXPIRED", "REGULAR", "VFP"};
        if(! Arrays.asList(accountStatus).contains(status.toUpperCase()))
            return "Invalid status";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println("staaaaaaaaaaaaaaaaaaaaay"+status);
        HttpEntity<CliGift> request = new HttpEntity<>(new CliGift(points, description, status), headers);
        ResponseEntity<CliGift> response = restTemplate.postForEntity(BASE_URI + "/" + id + "/Gifts/add", request, CliGift.class);
        if(response.getStatusCode() == HttpStatus.CREATED) {
            return "Gift added successfully: " + response.getBody().getDescription();
        } else if(response.getStatusCode() == HttpStatus.CONFLICT) {
            return "Gift already exists";
        } else {
            return "Error during adding gift";
        }
    }
}
