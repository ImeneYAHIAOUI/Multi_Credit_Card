package fr.univcotedazur.simpletcfs.controllers;

import fr.univcotedazur.simpletcfs.components.ShopManager;
import fr.univcotedazur.simpletcfs.controllers.dto.ErrorDTO;
import fr.univcotedazur.simpletcfs.controllers.dto.PlanningDTO;
import fr.univcotedazur.simpletcfs.controllers.dto.ShopDTO;
import fr.univcotedazur.simpletcfs.entities.Planning;
import fr.univcotedazur.simpletcfs.entities.Shop;
import fr.univcotedazur.simpletcfs.entities.WeekDay;
import fr.univcotedazur.simpletcfs.exceptions.MissingInformationException;
import fr.univcotedazur.simpletcfs.interfaces.ShopRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalTime;
import java.util.Optional;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = ShopController.BASE_URI, produces = MediaType.APPLICATION_JSON_VALUE)
public class ShopController {
    public static final String BASE_URI = "/shops";
    @Autowired
    private ShopManager shopManager;
    @Autowired
    private ShopRegistration shopRegistration;
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    // The 422 (Unprocessable Entity) status code means the server understands the content type of the request entity
    // (hence a 415(Unsupported Media Type) status code is inappropriate), and the syntax of the request entity is
    // correct (thus a 400 (Bad Request) status code is inappropriate) but was unable to process the contained
    // instructions.
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ErrorDTO handleExceptions(MethodArgumentNotValidException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Cannot process shop information");
        errorDTO.setDetails(e.getMessage());
        return errorDTO;
    }
    @PostMapping(path = "/save", consumes = APPLICATION_JSON_VALUE) // path is a REST CONTROLLER NAME
    public ResponseEntity<ShopDTO> registerShop(@RequestBody @Valid ShopDTO shopDTO) {
        try {
            if (shopManager.findShopByAddress(shopDTO.getAddress()).isEmpty()) {
                Shop shop = shopRegistration.addShop(shopDTO.getName(), shopDTO.getAddress());
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

    @GetMapping("/{shopId}")
    public ResponseEntity<ShopDTO> getShopById(@PathVariable("shopId") Long shopId) {
        Optional<Shop> shop = shopManager.findShopById(shopId);
        if(shop.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        return ResponseEntity.ok()
                .body(convertShopToDto(shop.get()));
    }
    // method to update the address of the shop
    @PutMapping("/{id}/address")
    public ResponseEntity<String> updateShopAddress(@PathVariable("id") Long shopId, @RequestBody String newAddress) {
        Optional<Shop> shop = shopManager.findShopById(shopId);
        if (shop.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        shopManager.modifyAddress(shop.get(), newAddress);
        return ResponseEntity.ok("Shop address updated successfully");
    }
    // method to modify a day's planning of a shop
    @PutMapping(path="/{id}/planning")
    public ResponseEntity<String> modifyPlanning(
            @PathVariable("id") Long id, @RequestBody PlanningDTO planning
    ){
        Optional<Shop> shop = shopManager.findShopById(id);
        if (shop.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        if (planning.getDayWorking() == null) {
            return ResponseEntity.badRequest().body("Invalid day parameter");
        }
        LocalTime closingHours = LocalTime.parse(planning.getClosingHours());
        LocalTime openingHours = LocalTime.parse(planning.getOpeningHours());
        if (openingHours != null && closingHours!= null && openingHours.isAfter(closingHours)) {
            return ResponseEntity.badRequest().body("Invalid opening/closing hours parameters");
        }
        shopManager.modifyPlanning(shop.get(), WeekDay.valueOf(planning.getDayWorking()), openingHours, closingHours);
        return ResponseEntity.ok("Shop planning for " + planning.getDayWorking() + " updated successfully");
    }

    // method to delete a shop by id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteShopById(@PathVariable("id") Long shopId) {
        Optional<Shop> shop = shopManager.findShopById(shopId);
        if (shop.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        shopRegistration.removeShop(shop.get());
        return ResponseEntity.ok("Shop deleted successfully");
    }
    private ShopDTO convertShopToDto(Shop shop) { // In more complex cases, we could use ModelMapper
        return new ShopDTO( shop.getId(),shop.getName(), shop.getAddress());
    }
}
