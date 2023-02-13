package fr.univcotedazur.simpletcfs.cli.commands;

import fr.univcotedazur.simpletcfs.cli.CliContext;
import fr.univcotedazur.simpletcfs.cli.model.CliMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.UUID;

@ShellComponent
public class MemberCommands {

    public static final String BASE_URI = "/members";

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private CliContext cliContext;

    @ShellMethod("Register a member in the CoD backend (register CUSTOMER_NAME CREDIT_CARD_NUMBER)")
    public CliMember register(UUID id, String name, String mail, String password, LocalDate birthDate, String membershipCard) {
        CliMember res = restTemplate.postForObject(BASE_URI + "/register", new CliMember(id,name,mail,password,birthDate,membershipCard), CliMember.class);
        cliContext.getMemberAccounts().put(res.getName(), res);
        return res;
    }

    @ShellMethod("List all members")
    public String customers() {
        return cliContext.getMemberAccounts().toString();
    }

}
