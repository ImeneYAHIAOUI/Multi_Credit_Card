package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.Mail;
import fr.univcotedazur.simpletcfs.entities.Question;
import fr.univcotedazur.simpletcfs.entities.Survey;

import java.time.LocalDate;
import java.util.List;

public interface MailHandler {
    Survey createSurvey(LocalDate endDate, List<Question> questions);
    Mail createMail(String mailContent, String subject);


}
