package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.CreditCard;
import fr.univcotedazur.simpletcfs.entities.Purchase;
import fr.univcotedazur.simpletcfs.exceptions.PaymentException;

public interface Payment {
    void payment(Purchase purchase, String creditCard) throws PaymentException;
}
