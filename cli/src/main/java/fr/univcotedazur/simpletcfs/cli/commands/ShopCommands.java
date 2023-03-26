package fr.univcotedazur.simpletcfs.cli.commands;

import fr.univcotedazur.simpletcfs.cli.CliContext;
import fr.univcotedazur.simpletcfs.cli.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.web.client.RestTemplate;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@ShellComponent
public class ShopCommands {
    public static final String BASE_URI = "/shops";

    @Autowired
    RestTemplate restTemplate;

    private final CliContext cliContext;

    public ShopCommands(CliContext cliContext) {
        this.cliContext = cliContext;
    }

    @ShellMethod("Register a shop in the multi-credit backend (register SHOP_NAME SHOP_ADDRESS )")
    public String save(String name, String address) {
        CliShop res = restTemplate.postForObject(BASE_URI + "/save", new CliShop(name,address), CliShop.class);
        cliContext.getShops().put(res.getName(), res);
        return res.toString();

    }
    @ShellMethod("update shop address (update SHOP_id SHOP_ADDRESS )")
    public String updateShopAddress( Long id, String address) {
        if (id < 0) {
            return "Invalid shop ID";
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(address, headers);
        ResponseEntity<String> response = restTemplate.exchange(BASE_URI +"/"+id+"/address", HttpMethod.PUT, request, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return "Shop address updated successfully";
        } else {
            return "Failed to update shop address";
        }
    }
    @ShellMethod(" get shop (get SHOP_id )")
    public String getShop( Long id) {

        CliShop p= restTemplate.getForObject(BASE_URI + "/"+id.toString(),CliShop.class);
        if(p==null)
            return "invalid shop id";
        return p.toString();
    }
    @ShellMethod("List all shops")
    public String shops() {
        return   cliContext.getShops().toString();
    }
    @ShellMethod("delete a shop (delete SHOP_ID)")
    public String deleteShop(@ShellOption(value = {"-i", "--id"}) Long id) {
        if (id < 0) {
            return "Invalid shop ID";
        }
        ResponseEntity<String> response = restTemplate.exchange(BASE_URI +"/"+id, HttpMethod.DELETE, null, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return "Shop deleted successfully";
        } else {
            return "Failed to delete shop";
        }
    }
    @ShellMethod(value = "Modify the opening and closing hours of a specific day for a shop(modify-planning SHOP_ID DAY_OF_WEEK OPENING_TIME CLOSING_TIME)")
    public String modifyPlanning(@ShellOption(help = "ID du shop") long shopId,
                               @ShellOption(help = "Jour de la semaine") String dayOfWeek,
                               @ShellOption(help = "Heure d'ouverture (HH:mm)") String openingTime,
                               @ShellOption(help = "Heure de fermeture (HH:mm)") String closingTime) {
        // Vérification des paramètres
        if (shopId < 0) {
            return "Invalid shop ID";
        }
        if (!isValidDayOfWeek(dayOfWeek)) {
            return "Invalid day of week";
        }
        if (!isValidTime(openingTime) || !isValidTime(closingTime)) {
            return "Invalid time format (must be HH:mm)";
        }
        // Conversion des paramètres
        CliPlanning planning = new CliPlanning(dayOfWeek, openingTime, closingTime);
        // Création de l'URL
        String url =BASE_URI+"/" + shopId +"/planning";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CliPlanning> requestEntity = new HttpEntity<>(planning, headers);
        // Envoi de la requête HTTP PUT
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return "Planning successfully modified.";
        } else {
           return "Failed to modify planning.";
        }
    }
    // Vérification de la validité du jour de la semaine
    private boolean isValidDayOfWeek(String dayOfWeek) {
        String[] daysOfWeek = {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"};
        return Arrays.asList(daysOfWeek).contains(dayOfWeek.toUpperCase());
    }

    // Vérification de la validité du format de l'heure
    private boolean isValidTime(String time) {
        String regex = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
        return time.matches(regex);
    }



}