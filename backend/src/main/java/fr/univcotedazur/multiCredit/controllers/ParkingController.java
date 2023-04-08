package fr.univcotedazur.multiCredit.controllers;

import fr.univcotedazur.multiCredit.components.MemberManager;
import fr.univcotedazur.multiCredit.connectors.externaldto.externaldto.ISWUPLSDTO;
import fr.univcotedazur.multiCredit.controllers.dto.ErrorDTO;
import fr.univcotedazur.multiCredit.controllers.dto.ParkingDTO;
import fr.univcotedazur.multiCredit.entities.MemberAccount;
import fr.univcotedazur.multiCredit.interfaces.MemberFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import fr.univcotedazur.multiCredit.components.ParkingManager;
import fr.univcotedazur.multiCredit.exceptions.NotVFPException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.validation.Valid;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;
@RestController
@RequestMapping(path = ParkingController.BASE_URI, produces = MediaType.APPLICATION_JSON_VALUE)
public class ParkingController {
    public static final String BASE_URI = "/parking";
    @Autowired
    private MemberFinder memberManager;
    @Autowired
    private ParkingManager parkingManager;
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    // The 422 (Unprocessable Entity) status code means the server understands the content type of the request entity
    // (hence a 415(Unsupported Media Type) status code is inappropriate), and the syntax of the request entity is
    // correct (thus a 400 (Bad Request) status code is inappropriate) but was unable to process the contained
    // instructions.
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ErrorDTO handleExceptions(MethodArgumentNotValidException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Cannot process Member information");
        errorDTO.setDetails(e.getMessage());
        return errorDTO;
    }

    @PostMapping( consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ParkingDTO> startParkingTime(@RequestBody @Valid ParkingDTO parkingDTO)
    {
        MemberAccount memberAccount = memberManager.findByMail(parkingDTO.getMail()).orElse(null);
        if(memberAccount == null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(new ParkingDTO(" ","user not found",0));
        }
        try {
            parkingManager.useParkingTime(memberAccount,parkingDTO.getCarRegistrationNumber(), parkingDTO.getParkingSpotNumber());

            ISWUPLSDTO iswuplsdto = parkingManager.getParkingInformation(parkingDTO.getCarRegistrationNumber())[0];
            LocalDateTime start = LocalDateTime.ofInstant(Instant.ofEpochSecond(iswuplsdto.getParking_date_time()), TimeZone.getTimeZone("Europe/Paris").toZoneId());
            LocalDateTime end = LocalDateTime.ofInstant(Instant.ofEpochSecond(iswuplsdto.getParking_end_date_time()), TimeZone.getTimeZone("Europe/Paris").toZoneId());
            parkingDTO.setStartTime(start.toString());
            parkingDTO.setEndTime(end.toString());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(parkingDTO);
        } catch (NotVFPException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).contentType(MediaType.APPLICATION_JSON).body(new ParkingDTO(" ","user not vfp",0));
        }catch( Exception e){
            return ResponseEntity.status(HttpStatus.CREATED).body(new ParkingDTO(" ","ISWPLS not responding",0));
        }
    }

}
