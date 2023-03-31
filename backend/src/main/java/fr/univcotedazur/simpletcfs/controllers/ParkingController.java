package fr.univcotedazur.simpletcfs.controllers;

import fr.univcotedazur.simpletcfs.components.MemberManager;
import fr.univcotedazur.simpletcfs.controllers.dto.ErrorDTO;
import fr.univcotedazur.simpletcfs.controllers.dto.ParkingDTO;
import fr.univcotedazur.simpletcfs.entities.MemberAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import fr.univcotedazur.simpletcfs.components.ParkingManager;
import fr.univcotedazur.simpletcfs.exceptions.NotVFPException;
import fr.univcotedazur.simpletcfs.interfaces.ISWUPLS;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;
@RestController
@RequestMapping(path = ParkingController.BASE_URI, produces = MediaType.APPLICATION_JSON_VALUE)
public class ParkingController {
    public static final String BASE_URI = "/parking";

    @Autowired
    private MemberManager memberManager;
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

    @PostMapping(path = "parkingTime", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ParkingDTO> startParkingTime(@RequestBody @Valid ParkingDTO ParkingDTO)
    {
        MemberAccount memberAccount = memberManager.findByMail(ParkingDTO.getMail()).orElse(null);
        if(memberAccount == null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(new ParkingDTO(" ","user not found",0));
        }
        try {
            parkingManager.useParkingTime(memberAccount,ParkingDTO.getCarRegistrationNumber(), ParkingDTO.getParkingSpotNumber());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ParkingDTO);
        } catch (NotVFPException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).contentType(MediaType.APPLICATION_JSON).body(new ParkingDTO(" ","user not vfp",0));
        }catch( Exception e){
            return ResponseEntity.status(HttpStatus.CREATED).body(new ParkingDTO(" ","ISWPLS not responding",0));
        }
    }

}
