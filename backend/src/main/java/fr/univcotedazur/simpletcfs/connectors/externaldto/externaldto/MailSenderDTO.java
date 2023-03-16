package fr.univcotedazur.simpletcfs.connectors.externaldto.externaldto;

import fr.univcotedazur.simpletcfs.entities.MemberAccount;
import fr.univcotedazur.simpletcfs.entities.Survey;

import java.util.List;

public class MailSenderDTO {
    private String message;
    private List<MemberAccount> members;
    private Survey survey;

    public MailSenderDTO(List<MemberAccount> members, String message) {
        this.message = message;
        this.members = members;
    }

    public MailSenderDTO(List<MemberAccount> members, Survey survey) {
        this.survey = survey;
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

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }
    public List<MemberAccount> getMembers() {
        return members;
    }

    public void setMembers(List<MemberAccount> members) {
        this.members = members;
    }
}
