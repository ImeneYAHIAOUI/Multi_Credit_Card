package fr.univcotedazur.multiCredit.interfaces;

<<<<<<< Updated upstream:backend/src/main/java/fr/univcotedazur/simpletcfs/interfaces/MailHandler.java
import fr.univcotedazur.simpletcfs.entities.Mail;
import fr.univcotedazur.simpletcfs.entities.MemberAccount;
import fr.univcotedazur.simpletcfs.entities.Question;
import fr.univcotedazur.simpletcfs.entities.Survey;
=======
import fr.univcotedazur.multiCredit.entities.Mail;
import fr.univcotedazur.multiCredit.entities.Question;
import fr.univcotedazur.multiCredit.entities.Survey;
>>>>>>> Stashed changes:backend/src/main/java/fr/univcotedazur/multiCredit/interfaces/MailHandler.java

import java.time.LocalDate;
import java.util.List;

public interface MailHandler {
    void sendSurvey(String sender, LocalDate endDate, List<Question> questions);
    void sendMail(String sender, String mailContent, String subject);


}
