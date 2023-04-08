package fr.univcotedazur.multiCredit.connectors;

import fr.univcotedazur.multiCredit.connectors.externaldto.externaldto.ISWUPLSDTO;
import fr.univcotedazur.multiCredit.interfaces.ISWUPLS;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class ISWUPLSProxy implements ISWUPLS {
    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${iswupls.host.baseurl}")
    private String iswuplsHostandPort;

    @Override
    public boolean startParkingTimer(String carRegistrationNumber, int parkingSpotNumber) {

        try {
            ResponseEntity<ISWUPLSDTO> result = restTemplate.postForEntity(iswuplsHostandPort + "/parking",
                    new ISWUPLSDTO(carRegistrationNumber, parkingSpotNumber, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
                            1800), ISWUPLSDTO.class);
            return (result.getStatusCode().equals(HttpStatus.CREATED));
        } catch (HttpClientErrorException errorException) {
            if (errorException.getStatusCode().equals(HttpStatus.BAD_REQUEST)) return false;
            throw errorException;
        }
    }

    @Override
    public ISWUPLSDTO[] getParkingInformation(String carRegistrationNumber) {
        return restTemplate.exchange(iswuplsHostandPort + "/parking", HttpMethod.GET, new HttpEntity<>(carRegistrationNumber), ISWUPLSDTO[].class).getBody();
    }
}