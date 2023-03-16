package fr.univcotedazur.simpletcfs.connectors.externaldto.externaldto;

import fr.univcotedazur.simpletcfs.entities.MemberAccount;

import java.util.List;

public class MailSenderDTO {
    private String message;
    private List<MemberAccount> members;

    public MailSenderDTO(String message, List<MemberAccount> members) {
        this.message = message;
        this.members = members;
    }

    public MailSenderDTO() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<MemberAccount> getMembers() {
        return members;
    }

    public void setMembers(List<MemberAccount> members) {
        this.members = members;
    }
}
