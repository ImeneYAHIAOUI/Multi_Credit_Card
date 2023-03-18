package fr.univcotedazur.simpletcfs.cli.commands;

import fr.univcotedazur.simpletcfs.cli.CliContext;
import fr.univcotedazur.simpletcfs.cli.model.CliMember;
import fr.univcotedazur.simpletcfs.cli.model.CliParking;
import fr.univcotedazur.simpletcfs.cli.model.CliShop;
import fr.univcotedazur.simpletcfs.cli.model.CliUpdateStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestTemplate;

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
    @ShellMethod("modify shop's address in the multi-credit backend (modify SHOP_id SHOP_ADDRESS )")
    public void modifyAddress( Long id, String address) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(address, headers);
        restTemplate.postForEntity(BASE_URI +"/"+id+"/address",requestEntity,String.class);
    }
    @ShellMethod(" get shop (get SHOP_id )")
    public String getShop( Long id) {
        return restTemplate.getForObject(BASE_URI + "/"+id.toString(),String.class);
    }
    @ShellMethod("List all shops")
    public String shops() {
        return   cliContext.getShops().toString();
    }
    @ShellMethod("delete a shop (delete SHOP_ADDRESS)")
    public String delete(String address) {
        restTemplate.delete(BASE_URI + address);
        return "Shop deleted";
    }





}
