package fr.univcotedazur.multicredit.cli.commands;

import fr.univcotedazur.multicredit.cli.CliContext;
import fr.univcotedazur.multicredit.cli.model.CliChargeCard;
import fr.univcotedazur.multicredit.cli.model.CliForm;
import fr.univcotedazur.multicredit.cli.model.CliMember;
import fr.univcotedazur.multicredit.cli.model.CliUpdateStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@ShellComponent
public class MemberCommands {

    public static final String BASE_URI = "/members";
    private final CliContext cliContext;
    @Autowired
    RestTemplate restTemplate;

    public MemberCommands(CliContext cliContext) {
        this.cliContext = cliContext;
    }

    @ShellMethod("Register a member in the multi-credit backend (register-member MEMBER_NAME MEMBER_MAIL MEMBER_PASSWORD MEMBER_BIRTHDATE)")
    public CliMember registerMember(String name, String mail, String password, String birthDate) {
        CliMember res = restTemplate.postForObject(BASE_URI + "/register", new CliMember(name, mail, password, birthDate), CliMember.class);
        assert res != null;
        cliContext.getMemberAccounts().put(res.getName(), res);
        return res;
    }

    @ShellMethod("delete a member (delete-member MEMBER_MAIL)")
    public String deleteMember(String mail) {
        restTemplate.exchange(BASE_URI + "/delete", HttpMethod.DELETE, new HttpEntity<>(mail), String.class);
        return "Member deleted";
    }

    @ShellMethod("delete a member (delete-member id)")
    public String deleteMemberWithId(Long id) {
        restTemplate.delete(BASE_URI + "/" + id);
        return "Member deleted";
    }

    @ShellMethod("archive a member(archive-member MEMBER_MAIL)")
    public String archiveMember(String mail) {
        restTemplate.put(BASE_URI + "/archive", mail);
        return "Member archived";
    }

    @ShellMethod("restore a member(archive-member MEMBER_MAIL)")
    public String restoreMember(String mail) {
        restTemplate.put(BASE_URI + "/restore", mail);
        return "Member restored";
    }

    @ShellMethod("update a member (update-status MEMBER_MAIL NEW_STATUS)")
    public CliUpdateStatus updateStatus(String mail, String status) {
        restTemplate.put(BASE_URI + "/status", new CliUpdateStatus(mail, status), CliUpdateStatus.class);
        return new CliUpdateStatus(mail, status);
    }

    @ShellMethod("Get member information (get-member-info id)")
    public CliMember getMemberInfo(Long id) {
        return restTemplate.getForObject(BASE_URI + "/" + id, CliMember.class);
    }

    @ShellMethod("Get members (get-members)")
    public List<CliMember> getMembers() {
        return List.of(Objects.requireNonNull(restTemplate.getForObject(BASE_URI, CliMember[].class)));
    }

    @ShellMethod("update a member (update-member MEMBER_MAIL NEW_NAME NEW_PASSWORD NEW_BIRTHDATE)")
    public CliMember updateMember(long id, @ShellOption(defaultValue = "") String name, @ShellOption(defaultValue = "") String mail, @ShellOption(defaultValue = "") String password, @ShellOption(defaultValue = "") String birthDate) {
        return restTemplate.postForObject(BASE_URI + "/" + id, new CliForm(name, mail, password, birthDate), CliMember.class);
    }

    @ShellMethod("renew membership (renew-membership id)")
    public CliMember renewMembership(long id) {
        return restTemplate.postForObject(BASE_URI + "/renew/" + id, null, CliMember.class);
    }

    @ShellMethod("update all merbers status")
    public String updateAllMembersStatus() {
        restTemplate.put(BASE_URI + "/status", null);
        return "All members status updated";
    }

    @ShellMethod("charge membership card: (charge-membership-card MEMBER_ID AMOUNT CREDIT_CARD_NUMBER)")
    public String chargeMembershipCard(long member, double amount, String creditCardNumber) {
        restTemplate.put(BASE_URI + "/charge", new CliChargeCard(member, amount, creditCardNumber));
        return "Membership card charged";
    }
}
