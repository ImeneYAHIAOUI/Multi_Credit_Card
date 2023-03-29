package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.Mail;
import fr.univcotedazur.simpletcfs.entities.MemberAccount;
import fr.univcotedazur.simpletcfs.entities.Survey;

import java.lang.reflect.Member;
import java.util.List;

public interface MailSender {
    boolean sendMail(List<MemberAccount> members, Mail mailToSend);
    boolean sendSurvey(List<MemberAccount> members, Survey survey);
}
