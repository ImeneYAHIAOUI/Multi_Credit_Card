package fr.univcotedazur.multiCredit.controllers;

import fr.univcotedazur.multiCredit.components.MemberManager;
import fr.univcotedazur.multiCredit.components.TransactionHandler;
import fr.univcotedazur.multiCredit.controllers.dto.ErrorDTO;
import fr.univcotedazur.multiCredit.controllers.dto.PurchaseDTO;
import fr.univcotedazur.multiCredit.controllers.dto.TransactionDTO;
import fr.univcotedazur.multiCredit.controllers.dto.UsePointDTO;
import fr.univcotedazur.multiCredit.entities.*;
import fr.univcotedazur.multiCredit.exceptions.AccountNotFoundException;
import fr.univcotedazur.multiCredit.exceptions.DeclinedTransactionException;
import fr.univcotedazur.multiCredit.exceptions.InsufficientPointsException;
import fr.univcotedazur.multiCredit.exceptions.PaymentException;
import fr.univcotedazur.multiCredit.interfaces.CatalogFinder;
import fr.univcotedazur.multiCredit.interfaces.ShopFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = TransactionController.BASE_URI, produces = MediaType.APPLICATION_JSON_VALUE)
public class TransactionController {
    public static final String BASE_URI = "/transactions";
    public static final String MEMBER_NOT_FOUND = "Member Not Found";
    public static final String SHOP_NOT_FOUND = "Shop Not Found";
    public static final String GIFT_NOT_FOUND = "Gift Not Found";
    public static final String PRODUCT_NOT_FOUND = "Product Not Found";
    private final TransactionHandler transactionHandler;

    private final MemberManager memberManager;

    private final CatalogFinder catalogFinder;

    private final ShopFinder shopFinder;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");


    @Autowired
    public TransactionController(MemberManager memberManager, TransactionHandler transactionHandler, CatalogFinder catalogFinder, ShopFinder shopFinder) {
        this.memberManager = memberManager;
        this.transactionHandler = transactionHandler;
        this.catalogFinder = catalogFinder;
        this.shopFinder = shopFinder;
    }


    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    // The 422 (Unprocessable Entity) status code means the server understands the content type of the request entity
    // (hence a 415(Unsupported Media Type) status code is inappropriate), and the syntax of the request entity is
    // correct (thus a 400 (Bad Request) status code is inappropriate) but was unable to process the contained
    // instructions.
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ErrorDTO handleExceptions(MethodArgumentNotValidException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Cannot process transaction information");
        errorDTO.setDetails(e.getMessage());
        return errorDTO;
    }

    @GetMapping("")
    public ResponseEntity<List<TransactionDTO>> getTransactions() {
        List<Transaction> transactions = transactionHandler.findAllTransactions();
        List<TransactionDTO> transactionDTOS = new ArrayList<>();
        for (Transaction transaction : transactions) {
            transactionDTOS.add(convertToDTO(transaction));
        }
        return ResponseEntity.ok(transactionDTOS);
    }

    @PostMapping(path = "/UsePoints", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionDTO> usePoints(@RequestBody UsePointDTO usePointDTO) {
        Optional<MemberAccount> memberAccount = memberManager.findById(usePointDTO.getMemberAccount());
        if (memberAccount.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new UsePointDTO(0, MEMBER_NOT_FOUND, 0, 0, 0, 0));
        }
        Optional<Shop> shop = shopFinder.findShopById(usePointDTO.getShop());
        if (shop.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new UsePointDTO(0, SHOP_NOT_FOUND, 0, 0, 0, 0));
        }
        Optional<Gift> gift = catalogFinder.findGiftById(usePointDTO.getGift());
        if (gift.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new UsePointDTO(0, GIFT_NOT_FOUND, 0, 0, 0, 0));
        }
        try {
            UsePoints usePoints = new UsePoints(LocalDate.parse(usePointDTO.getDate(), formatter), memberAccount.get(), gift.get().getPointsNeeded(), gift.get());
            usePoints.setShop(shop.get());
            return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON)
                    .body(convertToDTO(transactionHandler.processPointsUsage(memberAccount.get(), usePoints)));
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                    .body(new UsePointDTO(0, MEMBER_NOT_FOUND, 0, 0, 0, 0));
        } catch (InsufficientPointsException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).contentType(MediaType.APPLICATION_JSON)
                    .body(new UsePointDTO(0, "insuffisante points", 0, 0, 0, 0));
        } catch (DeclinedTransactionException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).contentType(MediaType.APPLICATION_JSON)
                    .body(new UsePointDTO(0, "transaction declined", 0, 0, 0, 0));
        }
    }

    @PostMapping("/purchase/creditCard")
    public ResponseEntity<TransactionDTO> purchaseWithCreditCard(@RequestBody PurchaseDTO purchaseDTO) {
        Optional<MemberAccount> memberAccount = memberManager.findById(purchaseDTO.getMemberAccount());
        if (memberAccount.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(new PurchaseDTO(0, MEMBER_NOT_FOUND, 0, 0, 0, 0, "", null, null, null));
        }
        Optional<Shop> shop = shopFinder.findShopById(purchaseDTO.getShop());
        if (shop.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(new PurchaseDTO(0, SHOP_NOT_FOUND, 0, 0, 0, 0, "", null, null, null));
        }
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < purchaseDTO.getItems().length; i++) {
            Optional<Product> product = catalogFinder.findProductById(purchaseDTO.getItems()[i]);
            if (product.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(new PurchaseDTO(0, PRODUCT_NOT_FOUND, 0, 0, 0, 0, "", null, null, null));
            }
            items.add(new Item(product.get(), purchaseDTO.getQuantities()[i]));
        }
        try {
            Purchase purchase = new Purchase(LocalDate.parse(purchaseDTO.getDate(), formatter), memberAccount.get(), items);
            purchase.setShop(shop.get());
            return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(convertToDTO(transactionHandler.processPurchaseWithCreditCard(memberAccount.get(), purchase, purchaseDTO.getCreditCardNumber())));
        } catch (PaymentException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).contentType(MediaType.APPLICATION_JSON).body(new PurchaseDTO(0, "Payment Error", 0, 0, 0, 0, "", null, null, null));
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(new PurchaseDTO(0, MEMBER_NOT_FOUND, 0, 0, 0, 0, "", null, null, null));
        }
    }

    @PostMapping("/purchase/cash")
    public ResponseEntity<TransactionDTO> purchaseWithCash(@RequestBody PurchaseDTO purchaseDTO) {
        Optional<MemberAccount> memberAccount = memberManager.findById(purchaseDTO.getMemberAccount());
        if (memberAccount.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(new PurchaseDTO(0, MEMBER_NOT_FOUND, 0, 0, 0, 0, "", null, null, null));
        }
        Optional<Shop> shop = shopFinder.findShopById(purchaseDTO.getShop());
        if (shop.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(new PurchaseDTO(0, SHOP_NOT_FOUND, 0, 0, 0, 0, "", null, null, null));
        }
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < purchaseDTO.getItems().length; i++) {
            Optional<Product> product = catalogFinder.findProductById(purchaseDTO.getItems()[i]);
            if (product.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(new PurchaseDTO(0, PRODUCT_NOT_FOUND, 0, 0, 0, 0, "", null, null, null));
            }
            items.add(new Item(product.get(), purchaseDTO.getQuantities()[i]));
        }
        try {
            Purchase purchase = new Purchase(LocalDate.parse(purchaseDTO.getDate(), formatter), memberAccount.get(), items);
            purchase.setShop(shop.get());
            return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(convertToDTO(transactionHandler.processPurchaseWithCash(memberAccount.get(), purchase)));
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(new PurchaseDTO(0, MEMBER_NOT_FOUND, 0, 0, 0, 0, "", null, null, null));
        }
    }

    @PostMapping("/purchase/membershipCard")
    public ResponseEntity<TransactionDTO> purchaseWithMembershipCard(@RequestBody PurchaseDTO purchaseDTO) {
        Optional<MemberAccount> memberAccount = memberManager.findById(purchaseDTO.getMemberAccount());
        if (memberAccount.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(new PurchaseDTO(0, MEMBER_NOT_FOUND, 0, 0, 0, 0, "", null, null, null));
        }
        Optional<Shop> shop = shopFinder.findShopById(purchaseDTO.getShop());
        if (shop.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(new PurchaseDTO(0, SHOP_NOT_FOUND, 0, 0, 0, 0, "", null, null, null));
        }
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < purchaseDTO.getItems().length; i++) {
            Optional<Product> product = catalogFinder.findProductById(purchaseDTO.getItems()[i]);
            if (product.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(new PurchaseDTO(0, PRODUCT_NOT_FOUND, 0, 0, 0, 0, "", null, null, null));
            }
            items.add(new Item(product.get(), purchaseDTO.getQuantities()[i]));
        }
        try {
            Purchase purchase = new Purchase(LocalDate.parse(purchaseDTO.getDate(), formatter), memberAccount.get(), items);
            purchase.setShop(shop.get());
            return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(convertToDTO(transactionHandler.processPurchaseWithMemberCard(memberAccount.get(), purchase)));
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(new PurchaseDTO(0, MEMBER_NOT_FOUND, 0, 0, 0, 0, "", null, null, null));
        } catch (PaymentException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).contentType(MediaType.APPLICATION_JSON).body(new PurchaseDTO(0, "insuffisante balance", 0, 0, 0, 0, "", null, null, null));
        }
    }


    private TransactionDTO convertToDTO(Transaction transaction) {
        if (transaction instanceof Purchase t) {
            long[] items = new long[t.getItem().size()];
            int[] quantities = new int[t.getItem().size()];
            int i = 0;
            for (Item item : t.getItem()) {
                items[i] = item.getProduct().getId();
                quantities[i] = item.getAmount();
                i++;
            }
            return new PurchaseDTO(transaction.getId(), transaction.getDate().toString(),
                    transaction.getMemberAccount().getId(), transaction.getShop().getId(),
                    t.getEarnedPoints(), t.getTotalPrice(), t.getCreditCardNumber(), items, quantities,
                    t.getPaymentMethod().toString());
        }

        return new UsePointDTO(transaction.getId(), transaction.getDate().toString(), transaction.getMemberAccount().getId(),
                transaction.getShop().getId(), ((UsePoints) transaction).getUsedPoints(), ((UsePoints) transaction).getGift().getGiftId());
    }
}