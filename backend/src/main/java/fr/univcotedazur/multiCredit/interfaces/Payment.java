package fr.univcotedazur.multiCredit.interfaces;

import fr.univcotedazur.multiCredit.entities.Purchase;
import fr.univcotedazur.multiCredit.exceptions.PaymentException;

public interface Payment {
    void payment(Purchase purchase, String creditCard) throws PaymentException;
}
