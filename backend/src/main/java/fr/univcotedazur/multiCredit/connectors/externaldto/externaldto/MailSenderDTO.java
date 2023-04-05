package fr.univcotedazur.multiCredit.connectors.externaldto.externaldto;

import fr.univcotedazur.multiCredit.entities.Mail;
import fr.univcotedazur.multiCredit.entities.MemberAccount;
import fr.univcotedazur.multiCredit.entities.Survey;

import java.util.List;

public class MailSenderDTO {
    private Mail mail;
    private List<MemberAccount> members;
    private Survey survey;

    public MailSenderDTO(List<MemberAccount> members, Mail mail) {
        this.mail = mail;
        this.members = members;
    }

    public MailSenderDTO(List<MemberAccount> members, Survey survey) {
        this.survey = survey;
        this.members = members;
    }

    public MailSenderDTO() {
    }

    public Mail getMail() {
        return mail;
    }

    public void setMail(Mail mail) {
        this.mail = mail;
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
