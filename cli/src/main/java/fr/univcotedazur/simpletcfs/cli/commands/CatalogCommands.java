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
import org.springframework.shell.standard.ShellOption;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
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
        CliProduct p= restTemplate.postForObject(BASE_URI +"/add/"+id+"/"+"Products/add", new CliProduct(name,points,price,discount), CliProduct.class);
        if(p!=null)
            return "Product added successfully : "+p.toString();
        else
            return "Error during adding product";
    }
    @ShellMethod("add gift (add-gift SHOP_ID POINTS_NEEDED DESCRIPTION STATUS) )")
    public String addGift(Long id, int points,String description,String status) {
        String[]    accountStatus = {"EXPIRED", "REGULAR", "VFP"};
        if(! Arrays.asList(accountStatus).contains(status.toUpperCase()))
            return "Invalid status";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CliGift> request = new HttpEntity<>(new CliGift(points, description, status), headers);
        ResponseEntity<CliGift> response = restTemplate.postForEntity(BASE_URI + "/add/" + id + "/Gifts/add", request, CliGift.class);
        if(response.getStatusCode() == HttpStatus.CREATED) {
            return "Gift added successfully: " + response.getBody().getDescription();
        } else if(response.getStatusCode() == HttpStatus.CONFLICT) {
            return "Gift already exists";
        } else {
            return "Error during adding gift";
        }
    }
    @ShellMethod("delete a gift (delete GIFT_ID)")
    public String deleteGift(@ShellOption(value = {"-i", "--id"}) Long id) {
        if (id < 0) {
            return "Invalid gift ID";
        }
        ResponseEntity<String> response = restTemplate.exchange( BASE_URI + "/Gifts/"+ id, HttpMethod.DELETE, null, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return "gift deleted successfully";
        } else {
            return "Failed to delete gift";
        }
    }
    @ShellMethod("delete a product (delete PRODUCT_ID)")
    public String deleteProduct(@ShellOption(value = {"-i", "--id"}) Long id) {
        if (id < 0) {
            return "Invalid product ID";
        }
        ResponseEntity<String> response = restTemplate.exchange(BASE_URI +"/Products/"+id, HttpMethod.DELETE, null, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return "product deleted successfully";
        } else {
            return "Failed to delete product";
        }
    }
    @ShellMethod("get gift (get-gift GIFT_ID ) )")
    public String getGift(Long id) {
        return restTemplate.getForObject(BASE_URI + "/get/Gifts/"+id.toString(),String.class);
    }
    @ShellMethod("get product (get-product PRODUCT_ID ) )")
    public String getProduct(Long id) {
        return restTemplate.getForObject(BASE_URI + "/get/Products/"+id.toString(),String.class);
    }
}
