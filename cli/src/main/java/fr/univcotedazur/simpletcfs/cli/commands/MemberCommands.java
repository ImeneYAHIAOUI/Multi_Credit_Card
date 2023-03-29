package fr.univcotedazur.simpletcfs.cli.commands;

import fr.univcotedazur.simpletcfs.cli.CliContext;
import fr.univcotedazur.simpletcfs.cli.model.CliForm;
import fr.univcotedazur.simpletcfs.cli.model.CliMember;
import fr.univcotedazur.simpletcfs.cli.model.CliParking;
import fr.univcotedazur.simpletcfs.cli.model.CliUpdateStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@ShellComponent
public class MemberCommands {

    public static final String BASE_URI = "/members";

    @Autowired
    RestTemplate restTemplate;

    private final CliContext cliContext;

    public MemberCommands(CliContext cliContext) {
        this.cliContext = cliContext;
    }

    @ShellMethod("Register a member in the multi-credit backend (register-member MEMBER_NAME MEMBER_MAIL MEMBER_PASSWORD MEMBER_BIRTHDATE)")
    public CliMember registerMember( String name, String mail, String password, String birthDate) {
        CliMember res = restTemplate.postForObject(BASE_URI + "/register", new CliMember(0,name,mail,password,birthDate), CliMember.class);
        assert res != null;
        cliContext.getMemberAccounts().put(res.getName(), res);
        return res;
    }

    @ShellMethod("List all members")
    public String members() {
        return cliContext.getMemberAccounts().toString();
    }

    @ShellMethod("delete a member (delete-member MEMBER_MAIL)")
    public String deleteMember(String mail) {
    	restTemplate.exchange(BASE_URI + "/delete", HttpMethod.DELETE, new HttpEntity<>(mail), String.class);
    	return "Member deleted";
    }

    @ShellMethod("delete a member (delete-member id)")
    public String deleteMemberWithId(Long id) {
    	restTemplate.delete(BASE_URI +"/"+id);
    	return "Member deleted";
    }

    @ShellMethod("archive a member(archive-member MEMBER_MAIL)")
    public String archiveMember(String mail){
        restTemplate.put(BASE_URI + "/archive",mail);
        return "Member archived";
    }

    @ShellMethod("archive a member(archive-member MEMBER_MAIL)")
    public String restoreMember(String mail){
        restTemplate.put(BASE_URI + "/restore",mail);
        return "Member restored";
    }

    @ShellMethod("update a member (update-status MEMBER_MAIL NEW_STATUS)")
    public CliUpdateStatus updateStatus(String mail, String status) {
         restTemplate.put(BASE_URI + "/status", new CliUpdateStatus(mail, status), CliUpdateStatus.class);
         return new CliUpdateStatus(mail, status);
    }


    @ShellMethod("use parking time (parking CAR_REGISTRATION_NUMBER MAIL PARKING_SPOT)")
    public CliParking startParking(String carRegistrationNumber, String mail, int parkingSpotNumber)
    {
        CliParking res = restTemplate.postForObject(BASE_URI + "/parking",new CliParking(carRegistrationNumber,mail,parkingSpotNumber), CliParking.class);
        return res;
    }

    @ShellMethod("Get member information (get-member-info id)")
    public CliMember getMemberInfo(Long id) {
        CliMember res = restTemplate.getForObject(BASE_URI+"/"+id, CliMember.class );
        return res;
    }

    @ShellMethod("Get members (get-members)")
    public List<CliMember> getMembers() {
        CliMember[] res = restTemplate.getForObject(BASE_URI, CliMember[].class );
        return List.of(res);
    }

    @ShellMethod("update a member (update-member MEMBER_MAIL NEW_NAME NEW_PASSWORD NEW_BIRTHDATE)")
    public CliMember updateMember(Long id, @ShellOption(defaultValue = "") String name, @ShellOption( defaultValue = "")  String mail, @ShellOption(defaultValue = "") String password, @ShellOption(defaultValue = "") String birthDate) {
        CliMember res = restTemplate.postForObject(BASE_URI + "/"+id, new CliForm(name,mail,password,birthDate), CliMember.class);
        return res;
    }

    @ShellMethod("update all merbers status")
    public String updateAllMembersStatus() {
        restTemplate.put(BASE_URI + "/status", null);
        return "All members status updated";
    }


}
