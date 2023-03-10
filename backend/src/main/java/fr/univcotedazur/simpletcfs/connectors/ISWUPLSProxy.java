package fr.univcotedazur.simpletcfs.connectors;

import fr.univcotedazur.simpletcfs.connectors.externaldto.externaldto.ISWUPLSDTO;
import fr.univcotedazur.simpletcfs.interfaces.ISWUPLS;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class ISWUPLSProxy implements ISWUPLS {
    @Value("${iswupls.host.baseurl}")
    private String iswuplsHostandPort;

    private RestTemplate restTemplate = new RestTemplate();


    @Override
    public boolean startParkingTimer(String carRegistrationNumber,int parkingSpotNumber) {
        try {
            ResponseEntity<ISWUPLSDTO> result = restTemplate.postForEntity(
                    iswuplsHostandPort + "/parking",
                    new ISWUPLSDTO(carRegistrationNumber,parkingSpotNumber , LocalDateTime.now().toEpochSecond(ZoneOffset.UTC), 1800),
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
