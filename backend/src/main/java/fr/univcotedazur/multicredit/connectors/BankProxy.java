package fr.univcotedazur.multicredit.connectors;

import fr.univcotedazur.multicredit.connectors.externaldto.externaldto.PaymentDTO;
import fr.univcotedazur.multicredit.interfaces.Bank;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class BankProxy implements Bank {

    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${bank.host.baseurl}")
    private String bankHostandPort;

    @Override
    public boolean pay(String creditCard, double value) {
        System.out.println(creditCard + " " + value);
        try {
            ResponseEntity<PaymentDTO> result = restTemplate.postForEntity(bankHostandPort + "/cctransactions", new PaymentDTO(creditCard, value), PaymentDTO.class);
            return (result.getStatusCode().equals(HttpStatus.CREATED));
        } catch (HttpClientErrorException errorException) {
            if (errorException.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
                System.out.println(errorException.getResponseBodyAsString());
                return false;
            }
            throw errorException;
        }
    }
}