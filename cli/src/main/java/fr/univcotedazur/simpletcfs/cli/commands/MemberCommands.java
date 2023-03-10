package fr.univcotedazur.simpletcfs.cli.commands;

import fr.univcotedazur.simpletcfs.cli.CliContext;
import fr.univcotedazur.simpletcfs.cli.model.CliMember;
import fr.univcotedazur.simpletcfs.cli.model.CliParking;
import fr.univcotedazur.simpletcfs.cli.model.CliUpdateStatus;
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

    @ShellMethod("delete a member (delete MEMBER_MAIL)")
    public String delete(String mail) {
    	restTemplate.delete(BASE_URI + mail);
    	return "Member deleted";
    }

    @ShellMethod("update a member (update MEMBER_MAIL NEW_STATUS)")
    public CliUpdateStatus updateStatus(String mail, String status) {
         restTemplate.put(BASE_URI + "/status", new CliUpdateStatus(mail, status), CliUpdateStatus.class);
         return new CliUpdateStatus(mail, status);
    }




    @ShellMethod("use parking time (parking CAR_REGISTRATION_NUMBER MAIL PARKING_SPOT)")
    public CliParking parking(String carRegistrationNumber, String mail, int parkingSpotNumber)
    {
        CliParking res = restTemplate.postForObject(BASE_URI + "/parking",new CliParking(carRegistrationNumber,mail,parkingSpotNumber), CliParking.class);
        return res;
    }


}
