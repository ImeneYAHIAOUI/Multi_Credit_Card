package fr.univcotedazur.simpletcfs.controllers;

import fr.univcotedazur.simpletcfs.connectors.externaldto.externaldto.ISWUPLSDTO;
import fr.univcotedazur.simpletcfs.controllers.MemberController;
import fr.univcotedazur.simpletcfs.controllers.dto.AccountDTO;
import fr.univcotedazur.simpletcfs.controllers.dto.MemberDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping
public class ISWUPLSService {

    @PostMapping(path = "cciswupls", consumes = APPLICATION_JSON_VALUE) // path is a REST CONTROLLER NAME
    public ResponseEntity<ISWUPLSDTO> cciswupls(@RequestBody @Valid ISWUPLSDTO iswuplsdto) {

        try {
            LocalTime startTime =  LocalTime.parse(iswuplsdto.getStartingTime());
            LocalTime endTime =  LocalTime.parse(iswuplsdto.getEndingTime());
            if(startTime.until(endTime, ChronoUnit.MINUTES)== 30)
            {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(iswuplsdto);
            }
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).contentType(MediaType.APPLICATION_JSON).build();
        }
        catch (Exception e){
           return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).contentType(MediaType.APPLICATION_JSON).build();
        }

    }
}
