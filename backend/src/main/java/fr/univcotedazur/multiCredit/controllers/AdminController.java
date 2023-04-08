package fr.univcotedazur.multiCredit.controllers;

import fr.univcotedazur.multiCredit.components.AdminManager;
import fr.univcotedazur.multiCredit.controllers.dto.*;
import fr.univcotedazur.multiCredit.entities.AdminAccount;
import fr.univcotedazur.multiCredit.entities.Form;
import fr.univcotedazur.multiCredit.entities.Shop;
import fr.univcotedazur.multiCredit.entities.ShopKeeperAccount;
import fr.univcotedazur.multiCredit.exceptions.*;
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
import java.util.Optional;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = AdminController.BASE_URI, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminController {
    public static final String BASE_URI = "/admin";
    @Autowired
    private AdminManager adminManager;

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

    @PostMapping(path = "/register", consumes = APPLICATION_JSON_VALUE) // path is a REST CONTROLLER NAME
    public ResponseEntity<AccountDTO> register(@RequestBody @Valid AdminDTO adminDTO) {
        // Note that there is no validation at all on the CustomerDto mapped
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
            Form form = new Form(adminDTO.getName(), adminDTO.getMail(), adminDTO.getPassword(),
                    LocalDate.parse(adminDTO.getBirthDate(), formatter));
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(convertAdminAccountToDto(adminManager.createAdminAccount(form)));
        } catch (AlreadyExistingAdminException e) {
            // Note: Returning 409 (Conflict) can also be seen a security/privacy vulnerability, exposing a service for account enumeration
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(convertAdminAccountToDto(adminManager.findAdminByMail(adminDTO.getMail())));
        } catch (MissingInformationException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).contentType(MediaType.APPLICATION_JSON).build();
        }
    }

    @PostMapping(path = "/shopKeepers/save", consumes = APPLICATION_JSON_VALUE) // path is a REST CONTROLLER NAME
    public ResponseEntity<ShopKeeperDTO> registerShopKeeper(@RequestBody @Valid ShopKeeperDTO shopKeeperDTO) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
            ShopKeeperAccount shopKeeperAccount = adminManager.createShopKeeperAccount(new Form(shopKeeperDTO.getName(), shopKeeperDTO.getMail(), shopKeeperDTO.getPassword(),
                            LocalDate.parse(shopKeeperDTO.getBirthDate(), formatter)),
                    shopKeeperDTO.getShopId());
            return ResponseEntity.status(HttpStatus.CREATED).body(convertShopKeeperToDto(shopKeeperAccount));
        } catch (MissingInformationException | UnderAgeException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(null);
        } catch (AlreadyExistingMemberException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(null);
        } catch (ShopNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping(path = "/mail", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> sendMail(@RequestBody @Valid MailDTO mailDTO) {
        adminManager.sendMail(mailDTO.getSender(), mailDTO.getSubject(), mailDTO.getMailContent());
        return ResponseEntity.status(HttpStatus.OK).body("Mail sent successfully");
    }

    @PostMapping(path = "/survey", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> sendSurvey(@RequestBody @Valid SurveyDTO surveyDTO) {
        try {
            adminManager.sendSurvey(surveyDTO.getSender(), surveyDTO.getQuestions());
            return ResponseEntity.status(HttpStatus.OK).body("Survey sent successfully");
        } catch (ResourceAccessException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Survey service unavailable");
        }
    }

    // method to delete a shop by id
    @DeleteMapping("/shops/{id}")
    public ResponseEntity<String> deleteShopById(@PathVariable("id") Long shopId) {
        try {
            adminManager.removeShop(shopId);
            return ResponseEntity.ok("Shop deleted successfully");
        } catch (ShopNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body("shop not found");
        }
    }

    @DeleteMapping("/shopKeepers/{id}")
    public ResponseEntity<String> deleteShopKeeperById(@PathVariable("id") Long shopId) {
        try {
            adminManager.deleteShopKeeperAccount(shopId);
            return ResponseEntity.ok("Shop keeper deleted successfully");
        } catch (ShopKeeperNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body("shop keeper not found");
        }
    }

    @PostMapping(path = "/shops/save", consumes = APPLICATION_JSON_VALUE) // path is a REST CONTROLLER NAME
    public ResponseEntity<ShopDTO> registerShop(@RequestBody @Valid ShopDTO shopDTO) {
        try {
            Shop shop = adminManager.addShop(shopDTO.getName(), shopDTO.getAddress());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(convertShopToDto(shop));

        } catch (MissingInformationException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(null);
        } catch (AlreadyExistingShopException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAdminById(@PathVariable("id") Long id) {
        Optional<AdminAccount> adminAccount = adminManager.findAdminById(id);
        if (adminAccount.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("Admin keeper not found");
        }
        adminManager.deleteAdminAccount(adminAccount.get());
        return ResponseEntity.ok("Admin account deleted successfully");
    }

    private AdminDTO convertAdminAccountToDto(AdminAccount admin) { // In more complex cases, we could use ModelMapper
        return new AdminDTO(admin.getId(), admin.getName(), admin.getMail(), admin.getPassword(), admin.getBirthDate().toString());
    }

    private ShopDTO convertShopToDto(Shop shop) { // In more complex cases, we could use ModelMapper
        return new ShopDTO(shop.getId(), shop.getName(), shop.getAddress());
    }

    private ShopKeeperDTO convertShopKeeperToDto(ShopKeeperAccount shop) { // In more complex cases, we could use ModelMapper
        ShopKeeperDTO s = new ShopKeeperDTO(shop.getId(), shop.getName(), shop.getMail(), shop.getPassword(), shop.getBirthDate().toString());
        s.setShopId(shop.getShop().getId());
        return s;
    }
}


