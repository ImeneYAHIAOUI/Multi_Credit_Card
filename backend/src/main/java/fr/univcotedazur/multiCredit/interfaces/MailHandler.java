package fr.univcotedazur.multiCredit.interfaces;

import fr.univcotedazur.multiCredit.entities.Question;
import fr.univcotedazur.multiCredit.exceptions.MailException;

import java.util.List;

public interface MailHandler {
    void sendSurvey(String sender, List<Question> questions);

    void sendMail(String sender, String mailContent, String subject)throws MailException;
}
