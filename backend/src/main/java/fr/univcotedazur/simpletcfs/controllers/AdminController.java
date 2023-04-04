package fr.univcotedazur.simpletcfs.controllers;

import fr.univcotedazur.simpletcfs.components.AdminManager;
import fr.univcotedazur.simpletcfs.components.MemberManager;
import fr.univcotedazur.simpletcfs.components.ShopManager;
import fr.univcotedazur.simpletcfs.connectors.externaldto.externaldto.ISWUPLSDTO;
import fr.univcotedazur.simpletcfs.controllers.dto.*;
import fr.univcotedazur.simpletcfs.entities.*;
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
import java.util.Optional;
import java.util.UUID;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;
@RestController
@RequestMapping(path = AdminController.BASE_URI, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminController {
    public static final String BASE_URI = "/admin";
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
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(convertAdminAccountToDto(adminManager.findAdminByMail(adminDTO.getMail())));
        } catch (MissingInformationException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).contentType(MediaType.APPLICATION_JSON).build();
        }
    }
    @PostMapping(path = "/shopKeepers/save", consumes = APPLICATION_JSON_VALUE) // path is a REST CONTROLLER NAME
    public ResponseEntity<ShopKeeperDTO> registerShopKeeper(@RequestBody @Valid ShopKeeperDTO shopKeeperDTO) {
        Optional<Shop> shop=shopManager.findShopById(shopKeeperDTO.getShopId());
        if(shop.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        else{
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
                adminManager.createShopKeeperAccount(new Form(shopKeeperDTO.getName(),shopKeeperDTO.getMail(),shopKeeperDTO.getPassword(),
                        LocalDate.parse(shopKeeperDTO.getBirthDate(),formatter)),shopKeeperDTO.getShopId());
                return ResponseEntity.status(HttpStatus.CREATED).body(null);
            }catch ( MissingInformationException | UnderAgeException  e){
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                        .body(null);
            }
            catch (AlreadyExistingMemberException e){
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(null);
            }
        }
    }
    // method to delete a shop by id
    @DeleteMapping("/shops/{id}")
    public ResponseEntity<String> deleteShopById(@PathVariable("id") Long shopId) {
        Optional<Shop> shop = shopManager.findShopById(shopId);
        if (shop.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body("shop not found");
        }
        adminManager.removeShop(shop.get());
        return ResponseEntity.ok("Shop deleted successfully");
    }
    @DeleteMapping("/shopKeepers/{id}")
    public ResponseEntity<String> deleteShopKeeperById(@PathVariable("id") Long shopId) {
        Optional<ShopKeeperAccount> shop = shopManager.findShopkeeperAccountById(shopId);
        if (shop.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body("shop keeper not found");
        }
        adminManager.deleteShopKeeperAccount(shop.get());
        return ResponseEntity.ok("Shop keeper deleted successfully");
    }
    @PostMapping(path = "/shops/save", consumes = APPLICATION_JSON_VALUE) // path is a REST CONTROLLER NAME
    public ResponseEntity<ShopDTO> registerShop(@RequestBody @Valid ShopDTO shopDTO) {
        try {
            if (shopManager.findShopByAddress(shopDTO.getAddress()).isEmpty()) {
                Shop shop = adminManager.addShop(shopDTO.getName(), shopDTO.getAddress());
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(convertShopToDto(shop));
            }else{
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
            }
        }catch (MissingInformationException e){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(null);
        }
    }
    @DeleteMapping("/Admin/{id}")
    public ResponseEntity<String> deleteAdminById(@PathVariable("id") Long id) {
        Optional<AdminAccount> adminAccount = adminManager.findAdminById(id);
        if (adminAccount.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body("Admin keeper not found");
        }
        adminManager.deleteAdminAccount(adminAccount.get());
        return ResponseEntity.ok("Admin account deleted successfully");
    }
    private AdminDTO convertAdminAccountToDto(AdminAccount admin) { // In more complex cases, we could use ModelMapper
        return new AdminDTO(admin.getId(), admin.getName(), admin.getMail(), admin.getPassword(), admin.getBirthDate().toString());
    }
    private ShopDTO convertShopToDto(Shop shop) { // In more complex cases, we could use ModelMapper
        return new ShopDTO( shop.getId(),shop.getName(), shop.getAddress());
    }

}


