package fr.univcotedazur.simpletcfs.connectors.externaldto.externaldto;

import fr.univcotedazur.simpletcfs.entities.CreditCard;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

// External DTO (Data Transfert Object) to POST payment to the external Bank system
@AllArgsConstructor
public class PaymentDTO {

    @Getter
    @Setter
    private CreditCard creditCard;

    @Getter
    @Setter
    private double amount;

    public PaymentDTO() {
    }






}
