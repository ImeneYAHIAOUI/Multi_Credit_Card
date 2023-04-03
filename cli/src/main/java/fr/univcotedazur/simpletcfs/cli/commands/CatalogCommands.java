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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;

@ShellComponent
public class  CatalogCommands {
    public static final String BASE_URI = "/catalog";
    @Autowired
    RestTemplate restTemplate;

    private final CliContext cliContext;

    public CatalogCommands(CliContext cliContext) {
        this.cliContext = cliContext;
    }

    @ShellMethod("add product (add-product SHOP_ID PRODUCT_NAME PRODUCT_PRICE PRODUCT_POINTS PRODUCT_DISCOUNT) )")
    public String addProduct(Long id,String name, Double price,int points,Double discount) {

        try {
            CliProduct p = restTemplate.postForObject(BASE_URI + "/add/" + id + "/" + "Products", new CliProduct(name, points, price, discount), CliProduct.class);
         return "Product added successfully : " + p.toString();

        }catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.CONFLICT)
                return "Failed to add product :Product already exists";
            if(ex.getStatusCode() == HttpStatus.NOT_FOUND)
                return "Failed to add product : Shop not found";
            return "Error during adding product";
        }
    }
    @ShellMethod("echo (ECHO TEXTE) )")
    public String echo(String message) {
        return message;
    }
    @ShellMethod("add gift (add-gift SHOP_ID POINTS_NEEDED DESCRIPTION STATUS) )")
    public String addGift(Long id, int points,String description,String status) {
        String[] accountStatus = {"EXPIRED", "REGULAR", "VFP"};
        if(! Arrays.asList(accountStatus).contains(status.toUpperCase()))
            return "Failed to add gift : Invalid status";
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<CliGift> request = new HttpEntity<>(new CliGift(points, description, status), headers);
            ResponseEntity<CliGift> response = restTemplate.postForEntity(BASE_URI + "/add/" + id + "/Gifts", request, CliGift.class);
            return "Gift added successfully: " + response.getBody().getDescription();
        }catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.CONFLICT)
                return "Failed to add gift : Gift already exists";
            if(ex.getStatusCode() == HttpStatus.NOT_FOUND)
                return "Failed to add gift : Shop not found";
            return "Error during adding gift";
        }
    }
    @ShellMethod("delete a gift (delete GIFT_ID)")
    public String deleteGift(@ShellOption(value = {"-i", "--id"}) Long id) {
        if (id < 0) {
            return "Invalid gift ID";
        }
        try {
            ResponseEntity<String> response = restTemplate.exchange( BASE_URI + "/Gifts/"+ id, HttpMethod.DELETE, null, String.class);
            return "gift deleted successfully";
        }catch (HttpClientErrorException ex) {
            if(HttpStatus.NOT_FOUND.equals(ex.getStatusCode())){
                return "Failed to delete gift : Gift id " + id + " unknown";
            }
            return "Failed to delete gift";
        }
    }
    @ShellMethod("delete a product (delete PRODUCT_ID)")
    public String deleteProduct(@ShellOption (value = {"-i", "--id"}) Long id) {
        if (id < 0) {
            return "Invalid product ID";
        }
        try {
            ResponseEntity<String> response = restTemplate.exchange(BASE_URI +"/Products/"+id, HttpMethod.DELETE, null, String.class);
            return "product deleted successfully";
        }catch (HttpClientErrorException ex) {
            if(HttpStatus.NOT_FOUND.equals(ex.getStatusCode())){
                return "Failed to delete product : Product id " + id + " unknown";
            }
            return "Failed to delete product";
        }
    }
    @ShellMethod("get gift (get-gift GIFT_ID ) )")
    public String getGift(Long id) {
        try
        {
            CliGift p= restTemplate.getForObject(BASE_URI + "/Gifts/"+id.toString(),CliGift.class);
            return p.toString();

        }catch (HttpClientErrorException ex) {
            if(HttpStatus.NOT_FOUND.equals(ex.getStatusCode())){
                return "Gift id " + id + " unknown";
            }
            return "Failed to get gift";
        }
    }
    @ShellMethod("get product (get-product PRODUCT_ID ) )")
    public String getProduct(Long id) {
        try
        {
            CliProduct p= restTemplate.getForObject(BASE_URI + "/Products/"+id.toString(),CliProduct.class);
            return p.toString();
        }catch (HttpClientErrorException ex) {
            if(HttpStatus.NOT_FOUND.equals(ex.getStatusCode())){
                return "Product id " + id + " unknown";
            }
            return "Failed to get product";
        }
    }
}
