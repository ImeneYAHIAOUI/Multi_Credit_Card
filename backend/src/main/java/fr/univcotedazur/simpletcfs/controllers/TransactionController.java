package fr.univcotedazur.simpletcfs.controllers;

import fr.univcotedazur.simpletcfs.components.MemberManager;
import fr.univcotedazur.simpletcfs.components.TransactionManager;
import fr.univcotedazur.simpletcfs.controllers.dto.ErrorDTO;
import fr.univcotedazur.simpletcfs.entities.Shop;
import fr.univcotedazur.simpletcfs.entities.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = TransactionController.BASE_URI, produces = MediaType.APPLICATION_JSON_VALUE)
public class TransactionController {
    public static final String BASE_URI = "/transactions";
    @Autowired
    private TransactionManager transactionManager;
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
    @GetMapping("/{TransactionId}")
    public ResponseEntity<String> getTransactionById(@PathVariable("transactionId") UUID transactionId) {
        Optional<Transaction> transaction = transactionManager.findTransactionById(transactionId);
        if(transaction.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("transactionId " + transactionId + " unknown");

        return ResponseEntity.ok().body(transaction.toString());
    }

}
