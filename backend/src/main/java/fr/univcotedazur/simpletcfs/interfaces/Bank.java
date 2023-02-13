package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.CreditCard;
import fr.univcotedazur.simpletcfs.exceptions.PaymentException;

public interface Bank {

    boolean pay(CreditCard creditCard, double value) throws PaymentException;
}
