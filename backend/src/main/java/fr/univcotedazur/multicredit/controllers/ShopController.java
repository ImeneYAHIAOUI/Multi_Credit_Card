package fr.univcotedazur.multicredit.controllers;

import fr.univcotedazur.multicredit.components.ShopManager;
import fr.univcotedazur.multicredit.controllers.dto.ErrorDTO;
import fr.univcotedazur.multicredit.controllers.dto.PlanningDTO;
import fr.univcotedazur.multicredit.entities.Shop;
import fr.univcotedazur.multicredit.entities.ShopKeeperAccount;
import fr.univcotedazur.multicredit.entities.WeekDay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.Optional;

@RestController
@RequestMapping(path = ShopController.BASE_URI, produces = MediaType.APPLICATION_JSON_VALUE)
public class ShopController {
    public static final String BASE_URI = "/shops";
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
        errorDTO.setError("Cannot process shop information");
        errorDTO.setDetails(e.getMessage());
        return errorDTO;
    }

    @GetMapping("/{shopId}")
    public ResponseEntity<String> getShopById(@PathVariable("shopId") Long shopId) {
        Optional<Shop> shop = shopManager.findShopById(shopId);
        return shop.map(value -> ResponseEntity.ok().body(value.toString()))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Shop not found"));
    }

    // method to update the address of the shop
    @PutMapping("/{id}/address")
    public ResponseEntity<String> updateShopAddress(@PathVariable("id") Long shopId, @RequestBody String newAddress) {
        Optional<Shop> shop = shopManager.findShopById(shopId);
        if (shop.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body("shop not found");
        }
        shopManager.modifyAddress(shop.get(), newAddress);
        return ResponseEntity.ok("Shop address updated successfully");
    }

    // method to modify a day's planning of a shop
    @PutMapping(path = "/{id}/planning")
    public ResponseEntity<String> modifyPlanning(@PathVariable("id") Long id, @RequestBody PlanningDTO planning) {
        Optional<Shop> shop = shopManager.findShopById(id);
        if (shop.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body("shop not found");
        }
        if (planning.getDayWorking() == null) {
            return ResponseEntity.badRequest().body("Invalid day parameter");
        }

        LocalTime closingHours = LocalTime.parse(planning.getClosingHours());
        LocalTime openingHours = LocalTime.parse(planning.getOpeningHours());
        if (openingHours.isAfter(closingHours)) {
            return ResponseEntity.badRequest().body("Invalid opening/closing hours parameters");
        }
        shopManager.modifyPlanning(shop.get(), WeekDay.valueOf(planning.getDayWorking().toUpperCase()), openingHours, closingHours);
        return ResponseEntity.ok("Shop planning for " + planning.getDayWorking() + " updated successfully");
    }

    @GetMapping("/shopKeepers/{shopKeeperId}")
    public ResponseEntity<String> getShopKeeperById(@PathVariable("shopKeeperId") Long shopKeepersId) {
        Optional<ShopKeeperAccount> shop = shopManager.findShopkeeperAccountById(shopKeepersId);
        return shop.map(shopKeeperAccount -> ResponseEntity.ok().body(shopKeeperAccount.toString()))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Shop keeper not found"));
    }
}
