package fr.univcotedazur.multicredit.connectors;

import fr.univcotedazur.multicredit.connectors.externaldto.externaldto.MailSenderDTO;
import fr.univcotedazur.multicredit.connectors.externaldto.externaldto.SurveySenderDTO;
import fr.univcotedazur.multicredit.entities.Mail;
import fr.univcotedazur.multicredit.entities.Survey;
import fr.univcotedazur.multicredit.interfaces.MailSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class MailProxy implements MailSender {
    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${mailproxy.host.baseurl}")
    private String mailSenderHostandPort;

    @Override
    public boolean sendMail(List<String> membersMail, Mail mailToSend) {
        try {
            ResponseEntity<MailSenderDTO> result = restTemplate.postForEntity(
                    mailSenderHostandPort + "/admin/mail",
                    new MailSenderDTO(mailToSend.getSender(), membersMail, mailToSend.getSubject(), mailToSend.getMailContent()),
                    MailSenderDTO.class
            );
            return (result.getStatusCode().equals(HttpStatus.CREATED));
        } catch (HttpClientErrorException errorException) {
            if (errorException.getStatusCode().equals(HttpStatus.BAD_REQUEST)) return false;
            throw errorException;
        }
    }

    @Override
    public boolean sendSurvey(List<String> membersMail, Survey survey) {
        try {
            ResponseEntity<MailSenderDTO> result = restTemplate.postForEntity(
                    mailSenderHostandPort + "/admin/survey",
                    new SurveySenderDTO(survey.getSender(), membersMail, survey.getQuestions()),
                    MailSenderDTO.class
            );
            return (result.getStatusCode().equals(HttpStatus.CREATED));
        } catch (HttpClientErrorException errorException) {
            if (errorException.getStatusCode().equals(HttpStatus.BAD_REQUEST)) return false;
            throw errorException;
        }
    }
}
