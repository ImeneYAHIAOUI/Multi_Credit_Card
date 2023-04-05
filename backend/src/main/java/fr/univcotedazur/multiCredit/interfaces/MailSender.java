package fr.univcotedazur.multiCredit.interfaces;

import fr.univcotedazur.multiCredit.entities.Mail;
import fr.univcotedazur.multiCredit.entities.MemberAccount;
import fr.univcotedazur.multiCredit.entities.Survey;

import java.util.List;

public interface MailSender {
    boolean sendMail(List<MemberAccount> members, Mail mailToSend);
    boolean sendSurvey(List<MemberAccount> members, Survey survey);
}
