package fr.univcotedazur.simpletcfs.cli;

import fr.univcotedazur.simpletcfs.cli.model.CliAdmin;
import fr.univcotedazur.simpletcfs.cli.model.CliMember;
import fr.univcotedazur.simpletcfs.cli.model.CliShop;
import fr.univcotedazur.simpletcfs.cli.model.CliShopKeeper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CliContext {

    private Map<String, CliMember> memberAccounts;
    private Map<String, CliAdmin> adminAccounts;
    private Map<String, CliShop> shops;
    private Map<String, CliShopKeeper> shopKeepers;

    public Map<String, CliAdmin> getAdminAccounts() {
        return adminAccounts;
    }

    public CliContext()
    {
        this.memberAccounts = new HashMap<>();
        this.adminAccounts = new HashMap<>();
        shopKeepers = new HashMap<>();
        shops=new HashMap<>();
    }

    @Override
    public String toString() {
        return memberAccounts.keySet().stream()
                .map(key -> key + "=" + memberAccounts.get(key))
                .collect(Collectors.joining(", ", "{", "}"));
    }

    public String printAdminAccounts() {
        return adminAccounts.keySet().stream()
                .map(key -> key + "=" + adminAccounts.get(key))
                .collect(Collectors.joining(", ", "{", "}"));
    }
    public Map<String, CliShop> getShops() {
        return shops;
    }

    public Map<String, CliMember> getMemberAccounts() {
        return memberAccounts;
    }
    public Map<String, CliShopKeeper> getShopKeepers(){
        return  shopKeepers;
    }
}
