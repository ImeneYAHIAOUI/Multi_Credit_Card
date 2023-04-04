package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.Mail;
import fr.univcotedazur.simpletcfs.entities.MemberAccount;
import fr.univcotedazur.simpletcfs.entities.Question;
import fr.univcotedazur.simpletcfs.entities.Survey;

import java.time.LocalDate;
import java.util.List;

public interface MailHandler {
    void sendSurvey(List<MemberAccount> receivers, String sender, LocalDate endDate, List<Question> questions);
    void sendMail(List<MemberAccount> receivers, String sender, String mailContent, String subject);


}
