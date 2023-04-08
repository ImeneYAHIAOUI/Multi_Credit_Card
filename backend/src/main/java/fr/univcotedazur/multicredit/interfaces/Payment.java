package fr.univcotedazur.multicredit.interfaces;

import fr.univcotedazur.multicredit.entities.Purchase;
import fr.univcotedazur.multicredit.exceptions.PaymentException;

public interface Payment {

    void payment(Purchase purchase, String creditCard) throws PaymentException;
}
