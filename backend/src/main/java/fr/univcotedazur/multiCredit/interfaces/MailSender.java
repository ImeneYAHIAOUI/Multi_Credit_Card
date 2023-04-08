package fr.univcotedazur.multiCredit.interfaces;

import fr.univcotedazur.multiCredit.entities.Mail;
import fr.univcotedazur.multiCredit.entities.MemberAccount;
import fr.univcotedazur.multiCredit.entities.Question;
import fr.univcotedazur.multiCredit.entities.Survey;
import fr.univcotedazur.multiCredit.exceptions.MailException;

import java.util.List;

public interface MailSender {
    boolean sendMail(List<String> membersMail, Mail mailToSend);
    boolean sendSurvey(List<String> membersMail, Survey survey);
}
