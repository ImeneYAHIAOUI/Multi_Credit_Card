package fr.univcotedazur.multiCredit.connectors;

import fr.univcotedazur.multiCredit.connectors.externaldto.externaldto.MailSenderDTO;
import fr.univcotedazur.multiCredit.entities.Mail;
import fr.univcotedazur.multiCredit.entities.MemberAccount;
import fr.univcotedazur.multiCredit.entities.Survey;
import fr.univcotedazur.multiCredit.interfaces.MailSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class MailProxy implements MailSender {
    @Value("${mailproxy.host.baseurl}")
    private String mailSenderHostandPort;
    private RestTemplate restTemplate = new RestTemplate();
    @Override
    public boolean sendMail(List<MemberAccount> members, Mail mailToSend) {
        try {
            ResponseEntity<MailSenderDTO> result = restTemplate.postForEntity(
                    mailSenderHostandPort + "",
                    new MailSenderDTO(members, mailToSend),
                    MailSenderDTO.class
            );
            return (result.getStatusCode().equals(HttpStatus.CREATED));
        }
        catch (HttpClientErrorException errorException) {
            if (errorException.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
                return false;
            }
            throw errorException;
        }
    }

    @Override
    public boolean sendSurvey(List<MemberAccount> members, Survey survey) {
        try {
            ResponseEntity<MailSenderDTO> result = restTemplate.postForEntity(
                    mailSenderHostandPort + "",
                    new MailSenderDTO(members, survey),
                    MailSenderDTO.class
            );
            return (result.getStatusCode().equals(HttpStatus.CREATED));
        }
        catch (HttpClientErrorException errorException) {
            if (errorException.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
                return false;
            }
            throw errorException;
        }
    }
}
