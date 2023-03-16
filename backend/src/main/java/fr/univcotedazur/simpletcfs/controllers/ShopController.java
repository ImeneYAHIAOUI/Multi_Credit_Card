package fr.univcotedazur.simpletcfs.controllers;

import fr.univcotedazur.simpletcfs.components.ShopManager;
import fr.univcotedazur.simpletcfs.controllers.dto.ErrorDTO;
import fr.univcotedazur.simpletcfs.controllers.dto.ShopDTO;
import fr.univcotedazur.simpletcfs.entities.Shop;
import fr.univcotedazur.simpletcfs.exceptions.MissingInformationException;
import fr.univcotedazur.simpletcfs.interfaces.ShopRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<ShopDTO> save(@RequestBody @Valid ShopDTO shopDTO) {
        // Note that there is no validation at all on the shop mapped
        // from the request body. This is because the @Valid annotation
        try {
            Shop shop = shopRegistration.addShop(shopDTO.getName(), shopDTO.getAddress());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(convertShopToDto(shop));
        }catch (MissingInformationException e){

            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(null);
        }

    }

    @GetMapping("/{shopId}")
    public ResponseEntity<String> getShopById(@PathVariable("shopId") Long shopId) {
        Optional<Shop> shop = shopManager.findShopById(shopId);
        if(shop.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ShopId " + shopId + " unknown");
        return ResponseEntity.ok().body(shop.toString());
    }
    @PostMapping(path = "/{shopId}/address", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateAddress(@PathVariable("shopId") Long shopId, @RequestBody String address) {
        Optional<Shop> shop = shopManager.findShopById(shopId);
        if(shop.isEmpty())
            return ResponseEntity.status(
                    HttpStatus.NOT_FOUND).body("ShopId " + shopId + " unknown");
        else
            shopManager.modifyAddress(shop.get(),address);
        return ResponseEntity.ok("address of shop with id "+shopId+" is successfully updated");
    }
    private ShopDTO convertShopToDto(Shop shop) { // In more complex cases, we could use ModelMapper
        return new ShopDTO( shop.getId(),shop.getName(), shop.getAddress());
    }
}
