package fr.univcotedazur.simpletcfs.cli.commands;

import fr.univcotedazur.simpletcfs.cli.CliContext;
import fr.univcotedazur.simpletcfs.cli.model.CliAdmin;
import fr.univcotedazur.simpletcfs.cli.model.CliShop;
import fr.univcotedazur.simpletcfs.cli.model.CliShopKeeper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@ShellComponent
public class AdminCommands {

    public static final String BASE_URI = "/admin";

    @Autowired
    RestTemplate restTemplate;
    private final CliContext cliContext;
    public AdminCommands(CliContext cliContext) {
        this.cliContext = cliContext;
    }
    @ShellMethod("Register an admin in the multi-credit backend (register ADMIN_NAME ADMIN_MAIL ADMIN_PASSWORD ADMIN_BIRTHDATE)")
    public String registerAdmin(String name, String mail, String password, String birthDate) {
        try {
            CliAdmin res = restTemplate.postForObject(BASE_URI + "/register", new CliAdmin(name,mail,password,birthDate), CliAdmin.class);
            cliContext.getAdminAccounts().put(res.getName(), res);
            return res.toString();
        }catch (HttpClientErrorException ex) {
            if(HttpStatus.CONFLICT.equals(ex.getStatusCode())){
                return "409 Failed to add admin account : account already exists";
            }
            else if(HttpStatus.UNPROCESSABLE_ENTITY.equals(ex.getStatusCode())){
                return "422 Failed tto add admin account : invalid parameters";
            }
            return "Error while adding admin account ";
        }
    }
    @ShellMethod("List all admins")
    public String admins() {
        return cliContext.printAdminAccounts();
    }
    @ShellMethod("Register a shop in the multi-credit backend (register SHOP_NAME SHOP_ADDRESS )")
    public String addShop(String name, String address) {
        try {
            CliShop res = restTemplate.postForObject(BASE_URI + "/shops/save", new CliShop(name, address), CliShop.class);
            cliContext.getShops().put(res.getName(), res);
            return res.toString();
        }catch (HttpClientErrorException ex) {
            if(HttpStatus.CONFLICT.equals(ex.getStatusCode())){
                return "409 Failed to add shop : shop already exists";
            }
            else if(HttpStatus.UNPROCESSABLE_ENTITY.equals(ex.getStatusCode())){
                return "422 Failed to add shop  :  invalid parameters";
            }
            return "Error while adding shop ";
        }

    }
    @ShellMethod("Register a shop keeper account in the multi-credit backend (register SHOP_ID  SHOPKEEPER_NAME SHOPKEEPER_MAIL SHOPKEEPER_PASSWORD SHOPKEEPER_BIRTHDATE)")
    public String addShopKeeper(Long Shopid, String name, String mail, String password, String birthDate) {
        try {
            CliShopKeeper s=new CliShopKeeper(name,mail ,password,birthDate);
            s.setShopId(Shopid);
            CliShopKeeper res = restTemplate.postForObject(BASE_URI + "/shops/save", s, CliShopKeeper.class);
            cliContext.getShopKeepers().put(res.getName(), res);
            return res.toString();
        }catch (HttpClientErrorException ex) {
            if(HttpStatus.NOT_FOUND.equals(ex.getStatusCode())) {
                return "404 Failed to add shop keeper : shop not found";
            }
            else if(HttpStatus.CONFLICT.equals(ex.getStatusCode())){
                return "409 Failed to add shop keeper : shop keeper account already exists";
            }
            if(HttpStatus.UNPROCESSABLE_ENTITY.equals(ex.getStatusCode())){
                return "422 Failed to add shop keeper :  invalid parameters";
            }
            return "Error while adding shop keeper";
        }

    }
    @ShellMethod("delete a shop (delete SHOP_ID)")
    public String deleteShop(@ShellOption(value = {"-i", "--id"}) Long id) {
        if (id < 0) {
            return "Invalid shop ID";
        }
        try {
            ResponseEntity<String> response = restTemplate.exchange(BASE_URI + "/shops/" + id, HttpMethod.DELETE, null, String.class);
            return "Shop deleted successfully";

        }catch (HttpClientErrorException ex) {
            if(HttpStatus.NOT_FOUND.equals(ex.getStatusCode())){
                return "Failed to delete shop : shop not found";
            }
            return "Error while deleting shop";
        }
    }
    @ShellMethod("delete a shop keeêr (delete SHOPKEEPER_ID)")
    public String deleteShopKeeper(@ShellOption(value = {"-i", "--id"}) Long id) {
        if (id < 0) {
            return "Invalid shop keeper ID";
        }
        try {
            ResponseEntity<String> response = restTemplate.exchange(BASE_URI + "/shopkeepers/" + id, HttpMethod.DELETE, null, String.class);
            return "Shop keeper deleted successfully";

        }catch (HttpClientErrorException ex) {
            if(HttpStatus.NOT_FOUND.equals(ex.getStatusCode())){
                return "404 Failed to delete shop : shop not found";
            }
            return "Error while deleting shop";
        }
    }
}
