package fr.univcotedazur.simpletcfs.controllers;

import fr.univcotedazur.simpletcfs.components.MemberManager;
import fr.univcotedazur.simpletcfs.connectors.externaldto.externaldto.ISWUPLSDTO;
import fr.univcotedazur.simpletcfs.controllers.dto.*;
import fr.univcotedazur.simpletcfs.entities.AccountStatus;
import fr.univcotedazur.simpletcfs.entities.MemberAccount;
import fr.univcotedazur.simpletcfs.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;
@RestController
@RequestMapping(path = MemberController.BASE_URI, produces = MediaType.APPLICATION_JSON_VALUE)
public class MemberController {

    public static final String BASE_URI = "/members";
    @Autowired
    private MemberManager memberManager;



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

    @PostMapping(path = "register", consumes = APPLICATION_JSON_VALUE) // path is a REST CONTROLLER NAME
    public ResponseEntity<AccountDTO> register(@RequestBody @Valid MemberDTO memberDTO) {
        // Note that there is no validation at all on the CustomerDto mapped
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(convertMemberAccountToDto(memberManager.createAccount(memberDTO.getName(), memberDTO.getMail(), memberDTO.getPassword(),  LocalDate.parse(memberDTO.getBirthDate(),formatter))));

        } catch (AlreadyExistingMemberException e) {
            // Note: Returning 409 (Conflict) can also be seen a security/privacy vulnerability, exposing a service for account enumeration
            return ResponseEntity.status(HttpStatus.OK)
                    .body(convertMemberAccountToDto(memberManager.findByMail(memberDTO.getMail()).orElse(null)));
        } catch (MissingInformationException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).contentType(MediaType.APPLICATION_JSON).build();
        } catch (UnderAgeException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).contentType(MediaType.APPLICATION_JSON).build();
        }
    }

    @PostMapping(path = "parking", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ParkingDTO> startParkingTime(@RequestBody @Valid ParkingDTO ParkingDTO)
    {
        MemberAccount memberAccount = memberManager.findByMail(ParkingDTO.getMail()).orElse(null);
        if(memberAccount == null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(new ParkingDTO(" ","user not found",0));
        }
        try {
            memberManager.useParkingTime(memberAccount,ParkingDTO.getCarRegistrationNumber(), ParkingDTO.getParkingSpotNumber());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ParkingDTO);
        } catch (NotVFPException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).contentType(MediaType.APPLICATION_JSON).body(new ParkingDTO(" ","user not vfp",0));
        }catch( Exception e){
            return ResponseEntity.status(HttpStatus.CREATED).body(new ParkingDTO(" ","ISWPLS not responding",0));
        }
    }

    @DeleteMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteAccount(@RequestBody @Valid  @Pattern(regexp = "^(.+)@(.+)$", message = "email should be valid") String mail) {
        MemberAccount memberAccount = memberManager.findByMail(mail).orElse(null);
        if(memberAccount == null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body("member not found");
        }
        try {
            memberManager.deleteAccount(memberAccount);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body("member deleted");
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body("member not found");
        }
    }

    @PutMapping(path="status",consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdateStatusDTO> updateStatus(@RequestBody @Valid  UpdateStatusDTO updateStatusDTO) {
        MemberAccount memberAccount = memberManager.findByMail(updateStatusDTO.getMail()).orElse(null);
        if(memberAccount == null)
        {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(new UpdateStatusDTO(" ","user not found"));
        }
        try {
            AccountStatus newStatus = switch (updateStatusDTO.getStatus()) {
                case "VFP" -> AccountStatus.VFP;
                case "REGULAR" -> AccountStatus.REGULAR;
                case "EXPIRED" -> AccountStatus.EXPIRED;
                default -> throw new IllegalStateException("Unexpected value: " + updateStatusDTO.getStatus());
            };
            memberManager.updateAccountStatus(memberAccount,newStatus);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(updateStatusDTO);
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(new UpdateStatusDTO(" ","user not found"));
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).contentType(MediaType.APPLICATION_JSON).body(new UpdateStatusDTO(" ","status not valid"));
        }
    }


    private MemberDTO convertMemberAccountToDto(MemberAccount member) { // In more complex cases, we could use ModelMapper
        return new MemberDTO( member.getName(), member.getMail(), member.getPassword(), member.getBirthDate().toString());
    }

}


