package fr.univcotedazur.simpletcfs.connectors;

import fr.univcotedazur.simpletcfs.entities.MemberAccount;
import fr.univcotedazur.simpletcfs.entities.Survey;
import fr.univcotedazur.simpletcfs.interfaces.MailSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class MailProxy implements MailSender {
    @Value("http://localhost:8080")
    private String mailSenderHostandPort;
    private RestTemplate restTemplate = new RestTemplate();
    @Override
    public void sendPromotions(List<MemberAccount> members, String mailToSend) {

    }

    @Override
    public void sendSurvey(List<MemberAccount> members, Survey survey) {

    }

    @Override
    public void sendVFPReminders(List<MemberAccount> members) {

    }
}
