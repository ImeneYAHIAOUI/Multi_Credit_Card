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

    private final CliContext cliContext;

    public MemberCommands(CliContext cliContext) {
        this.cliContext = cliContext;
    }

    @ShellMethod("Register a member in the multi-credit backend (register MEMBER_NAME MEMBER_MAIL MEMBER_PASSWORD MEMBER_BIRTHDATE)")
    public CliMember register( String name, String mail, String password, String birthDate) {
        CliMember res = restTemplate.postForObject(BASE_URI + "/register", new CliMember(name,mail,password,birthDate), CliMember.class);
        cliContext.getMemberAccounts().put(res.getName(), res);
        return res;
    }

    @ShellMethod("List all members")
    public String members() {
        return cliContext.getMemberAccounts().toString();
    }


}
