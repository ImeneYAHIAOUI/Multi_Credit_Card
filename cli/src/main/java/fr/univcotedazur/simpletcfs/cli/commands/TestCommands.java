package fr.univcotedazur.simpletcfs.cli.commands;

import fr.univcotedazur.simpletcfs.cli.CliContext;
import fr.univcotedazur.simpletcfs.cli.model.CliMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestTemplate;

@ShellComponent
public class TestCommands {

    public static final String BASE_URI = "/test";

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private CliContext cliContext;

    @ShellMethod("Register a member in the CoD backend (register CUSTOMER_NAME CREDIT_CARD_NUMBER)")
    public CliMember register(String name, String mail, String password, String birthDate) {
        CliMember res = restTemplate.postForObject(BASE_URI + "/register", new CliMember(name, mail,password,birthDate), CliMember.class);
        cliContext.getMemberAccounts().put(res.getName(), res);
        return res;
    }

    @ShellMethod("List all members")
    public String customers() {
        return cliContext.getMemberAccounts().toString();
    }
}
