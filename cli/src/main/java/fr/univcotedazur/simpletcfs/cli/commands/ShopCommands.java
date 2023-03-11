package fr.univcotedazur.simpletcfs.cli.commands;

import fr.univcotedazur.simpletcfs.cli.CliContext;
import fr.univcotedazur.simpletcfs.cli.model.CliMember;
import fr.univcotedazur.simpletcfs.cli.model.CliParking;
import fr.univcotedazur.simpletcfs.cli.model.CliShop;
import fr.univcotedazur.simpletcfs.cli.model.CliUpdateStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestTemplate;

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
    public CliShop save(String name, String address) {
        CliShop res = restTemplate.postForObject(BASE_URI + "/save", new CliShop(name,address), CliShop.class);
        cliContext.getShops().put(res.getName(), res);
        return res;
    }
    @ShellMethod("Register a shop in the multi-credit backend (register SHOP_NAME SHOP_ADDRESS )")
    public String modifyAddress( String address) {
        return restTemplate.postForObject(BASE_URI + "/save/"+address,null, String.class);
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
