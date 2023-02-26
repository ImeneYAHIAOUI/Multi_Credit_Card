package fr.univcotedazur.simpletcfs.connectors;

import fr.univcotedazur.simpletcfs.connectors.externaldto.externaldto.ISWUPLSDTO;
import fr.univcotedazur.simpletcfs.interfaces.ISWUPLS;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
public class ISWUPLSProxy implements ISWUPLS {
    @Value("http://localhost:8080")
    private String iswuplsHostandPort;

    private RestTemplate restTemplate = new RestTemplate();


    @Override
    public boolean startParkingTimer(String carRegistrationNumber) {
        try {
            ResponseEntity<ISWUPLSDTO> result = restTemplate.postForEntity(
                    iswuplsHostandPort + "/cciswupls",
                    new ISWUPLSDTO(carRegistrationNumber, LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")),LocalTime.now().plusMinutes(30).format(DateTimeFormatter.ofPattern("HH:mm"))),
                    ISWUPLSDTO.class
            );
            return (result.getStatusCode().equals(HttpStatus.CREATED));
        }
        catch (HttpClientErrorException errorException)
        {
            if (errorException.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
                return false;
            }
            throw errorException;
        }
    }
}
