package fr.univcotedazur.simpletcfs.controllers;

import fr.univcotedazur.simpletcfs.components.AdminManager;
import fr.univcotedazur.simpletcfs.components.MemberManager;
import fr.univcotedazur.simpletcfs.components.ShopManager;
import fr.univcotedazur.simpletcfs.connectors.externaldto.externaldto.ISWUPLSDTO;
import fr.univcotedazur.simpletcfs.controllers.dto.*;
import fr.univcotedazur.simpletcfs.entities.AdminAccount;
import fr.univcotedazur.simpletcfs.entities.Form;
import fr.univcotedazur.simpletcfs.entities.MemberAccount;
import fr.univcotedazur.simpletcfs.entities.Shop;
import fr.univcotedazur.simpletcfs.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;

import javax.validation.Valid;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;
@RestController
@RequestMapping(path = AdminController.BASE_URI, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminController {

    public static final String BASE_URI = "/admins";
    @Autowired
    private AdminManager adminManager;

    @Autowired
    private ShopManager shopManager;


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
    public ResponseEntity<AccountDTO> register(@RequestBody @Valid AdminDTO adminDTO) {
        // Note that there is no validation at all on the CustomerDto mapped
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
            Form form = new Form(adminDTO.getName(), adminDTO.getMail(), adminDTO.getPassword(),  LocalDate.parse(adminDTO.getBirthDate(),formatter));
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(convertAdminAccountToDto(adminManager.createAdminAccount(form)));

        } catch (AlreadyExistingAdminException e) {
            // Note: Returning 409 (Conflict) can also be seen a security/privacy vulnerability, exposing a service for account enumeration
            return ResponseEntity.status(HttpStatus.OK)
                    .body(convertAdminAccountToDto(adminManager.findAdminByMail(adminDTO.getMail())));
        } catch (MissingInformationException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).contentType(MediaType.APPLICATION_JSON).build();
        }
    }


    private AdminDTO convertAdminAccountToDto(AdminAccount admin) { // In more complex cases, we could use ModelMapper
        return new AdminDTO(admin.getId(), admin.getName(), admin.getMail(), admin.getPassword(), admin.getBirthDate().toString());
    }
}


