package fr.univcotedazur.simpletcfs.controllers;

import fr.univcotedazur.simpletcfs.components.Catalog;
import fr.univcotedazur.simpletcfs.components.ShopManager;
import fr.univcotedazur.simpletcfs.controllers.dto.ErrorDTO;
import fr.univcotedazur.simpletcfs.controllers.dto.GiftDTO;
import fr.univcotedazur.simpletcfs.controllers.dto.ProductDTO;
import fr.univcotedazur.simpletcfs.controllers.dto.ShopDTO;
import fr.univcotedazur.simpletcfs.entities.AccountStatus;
import fr.univcotedazur.simpletcfs.entities.Gift;
import fr.univcotedazur.simpletcfs.entities.Product;
import fr.univcotedazur.simpletcfs.entities.Shop;
import fr.univcotedazur.simpletcfs.exceptions.AlreadyExistingGiftException;
import fr.univcotedazur.simpletcfs.exceptions.AlreadyExistingProductException;
import fr.univcotedazur.simpletcfs.exceptions.MissingInformationException;
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
@RequestMapping(path = CatalogController.BASE_URI, produces = MediaType.APPLICATION_JSON_VALUE)
public class CatalogController {
    public static final String BASE_URI = "/catalog";
    @Autowired
    Catalog catalog;
    @Autowired
    ShopManager shopManager;
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
    @PostMapping(path = "/{shopId}/Products/add", consumes = APPLICATION_JSON_VALUE) // path is a REST CONTROLLER NAME
    public ResponseEntity<ProductDTO> addProduct(@PathVariable("shopId") Long shopId,@RequestBody @Valid ProductDTO product) {
        // Note that there is no validation at all on the shop mapped
        // from the request body. This is because the @Valid annotation
        Optional<Shop> shop=shopManager.findShopById(shopId);
        if(shop.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        else{
            Product p=new Product(product.getName(),product.getPrice(),product.getPoints(),product.getDiscountPercentage());
            try{
                catalog.addProductToCatalog(shop.get(),p);

                return ResponseEntity.status(HttpStatus.CREATED).body(convertProductToDto(p));
            }catch (AlreadyExistingProductException e){
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
            }
        }

    }
    @PostMapping(path = "/{shopId}/Gifts/add", consumes = APPLICATION_JSON_VALUE) // path is a REST CONTROLLER NAME
    public ResponseEntity<GiftDTO> addGift(@PathVariable("shopId") Long shopId,@RequestBody  GiftDTO gift) {
        // Note that there is no validation at all on the shop mapped
        // from the request body. This is because the @Valid annotation
        Optional<Shop> shop=shopManager.findShopById(shopId);
        if(shop.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        else{
            System.out.println(gift.toString());
            Gift p=new Gift(gift.getPointsNeeded(),gift.getDescription(), AccountStatus.valueOf(gift.getStatus().toUpperCase()));
            try{
                catalog.addGift(shop.get(),p);
                return ResponseEntity.status(HttpStatus.CREATED).body(convertGiftToDto( p));
            }catch (AlreadyExistingGiftException e){
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
            }
        }

    }
    @GetMapping("Gifts/{giftId}")
    public ResponseEntity<String> getGiftById(@PathVariable("giftId") Long giftId) {
        Optional<Gift> g = catalog.findGiftById(giftId);
        if(g.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Gift id " + g + " unknown");
        return ResponseEntity.ok().body(g.get().toString());
    }
    @GetMapping("Products/{ProductId}")
    public ResponseEntity<String> getProductById(@PathVariable("ProductId") Long ProductId) {
        Optional<Product> product = catalog.findProductById(ProductId);
        if(product.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product id " + ProductId + " unknown");
        return ResponseEntity.ok().body(product.get().toString());
    }
    private ProductDTO convertProductToDto(Product p) { // In more complex cases, we could use ModelMapper
        return new ProductDTO( p.getId(),
                new ShopDTO(p.getShop().getId(),p.getShop().getName(),p.getShop().getAddress()),p.getName()
                ,p.getPoints(),p.getPrice(),p.getDiscountPercentage());
    }
    private GiftDTO convertGiftToDto(Gift p) { // In more complex cases, we could use ModelMapper
        return new GiftDTO( p.getGiftId(),
               p.getPointsNeeded(),p.getDescription(),p.getRequiredStatus().getAccountStatusName());
    }
}
