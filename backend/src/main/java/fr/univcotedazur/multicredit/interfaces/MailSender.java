package fr.univcotedazur.multicredit.interfaces;

import fr.univcotedazur.multicredit.entities.Mail;
import fr.univcotedazur.multicredit.entities.Survey;

import java.util.List;

public interface MailSender {
    boolean sendMail(List<String> membersMail, Mail mailToSend);
    boolean sendSurvey(List<String> membersMail, Survey survey);
}
