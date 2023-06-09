package fr.univcotedazur.multicredit.cli;

import fr.univcotedazur.multicredit.cli.model.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CliContext {
    private final Map<String, CliMember> memberAccounts;
    private final Map<Long, CliAdmin> adminAccounts;
    private final Map<Long, CliShop> shops;
    private final Map<Long, CliShopKeeper> shopKeepers;
    private final Map<String, CliMail> mails;
    private final Map<String, CliSurvey> surveys;

    public CliContext() {
        this.memberAccounts = new HashMap<>();
        this.adminAccounts = new HashMap<>();
        shopKeepers = new HashMap<>();
        shops = new HashMap<>();
        mails = new HashMap<>();
        surveys = new HashMap<>();
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

    public String printShops() {

        return shops.keySet().stream()
                .map(key -> key + "=" + shops.get(key))
                .collect(Collectors.joining(", ", "{", "}"));
    }

    public String printShopKeepers() {

        return shopKeepers.keySet().stream()
                .map(key -> key + "=" + shopKeepers.get(key))
                .collect(Collectors.joining(", ", "{", "}"));
    }

    public Map<Long, CliShop> getShops() {
        return shops;
    }

    public Map<String, CliMember> getMemberAccounts() {
        return memberAccounts;
    }

    public Map<Long, CliShopKeeper> getShopKeepers() {
        return shopKeepers;
    }

    public Map<String, CliMail> getMails() {
        return mails;
    }

    public Map<String, CliSurvey> getSurveys() {
        return surveys;
    }

    public Map<Long, CliAdmin> getAdminAccounts() {
        return adminAccounts;
    }
}
