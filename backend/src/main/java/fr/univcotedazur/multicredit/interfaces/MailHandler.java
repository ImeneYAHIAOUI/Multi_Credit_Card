package fr.univcotedazur.multicredit.interfaces;

import fr.univcotedazur.multicredit.entities.Question;
import fr.univcotedazur.multicredit.exceptions.MailException;

import java.util.List;

public interface MailHandler {
    void sendSurvey(String sender, List<Question> questions);

    void sendMail(String sender, String mailContent, String subject)throws MailException;
}
