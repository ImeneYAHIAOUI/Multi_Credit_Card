package fr.univcotedazur.simpletcfs.cli.commands;

import fr.univcotedazur.simpletcfs.cli.CliContext;
import fr.univcotedazur.simpletcfs.cli.model.CliAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestTemplate;

@ShellComponent
public class AdminCommands {

    public static final String BASE_URI = "/admins";

    @Autowired
    RestTemplate restTemplate;

    private final CliContext cliContext;

    public AdminCommands(CliContext cliContext) {
        this.cliContext = cliContext;
    }

    @ShellMethod("Register an admin in the multi-credit backend (register ADMIN_NAME ADMIN_MAIL ADMIN_PASSWORD ADMIN_BIRTHDATE)")
    public CliAdmin registerAdmin(String name, String mail, String password, String birthDate) {
        CliAdmin res = restTemplate.postForObject(BASE_URI + "/register", new CliAdmin(name,mail,password,birthDate), CliAdmin.class);
        cliContext.getAdminAccounts().put(res.getName(), res);
        return res;
    }

    @ShellMethod("List all admins")
    public String admins() {
        return cliContext.printAdminAccounts();
    }



}
