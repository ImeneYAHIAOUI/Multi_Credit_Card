package fr.univcotedazur.multiCredit.interfaces;

import fr.univcotedazur.multiCredit.entities.Mail;
import fr.univcotedazur.multiCredit.entities.Question;
import fr.univcotedazur.multiCredit.entities.Survey;

import java.time.LocalDate;
import java.util.List;

public interface MailHandler {
    void sendSurvey(String sender, List<Question> questions);
    void sendMail(String sender, String mailContent, String subject);


}
