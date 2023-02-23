package fr.univcotedazur.simpletcfs.controllers;

import fr.univcotedazur.simpletcfs.components.MemberManager;
import fr.univcotedazur.simpletcfs.controllers.dto.ErrorDTO;
import fr.univcotedazur.simpletcfs.controllers.dto.AccountDTO;
import fr.univcotedazur.simpletcfs.controllers.dto.MemberDTO;
import fr.univcotedazur.simpletcfs.entities.MemberAccount;
import fr.univcotedazur.simpletcfs.exceptions.AlreadyExistingMemberException;
import fr.univcotedazur.simpletcfs.exceptions.MissingInformationException;
import fr.univcotedazur.simpletcfs.exceptions.UnderAgeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
            MemberAccount a = memberManager.findByMail(memberDTO.getMail());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(convertMemberAccountToDto(memberManager.findByMail(memberDTO.getMail())));
        } catch (MissingInformationException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).contentType(MediaType.APPLICATION_JSON).build();
        } catch (UnderAgeException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).contentType(MediaType.APPLICATION_JSON).build();
        }
    }

    private MemberDTO convertMemberAccountToDto(MemberAccount member) { // In more complex cases, we could use ModelMapper
        return new MemberDTO( member.getName(), member.getMail(), member.getPassword(), member.getBirthDate().toString());
    }

}


