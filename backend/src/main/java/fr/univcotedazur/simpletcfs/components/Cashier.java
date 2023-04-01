package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.entities.Purchase;
import fr.univcotedazur.simpletcfs.exceptions.PaymentException;
import fr.univcotedazur.simpletcfs.interfaces.Bank;
import fr.univcotedazur.simpletcfs.interfaces.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class Cashier implements Payment {
    private Bank bank;
    @Autowired
    public Cashier(Bank bank) {
        this.bank = bank;
    }

    public void payment(Purchase purchase, String creditCard) throws PaymentException {
        if(! bank.pay(creditCard, purchase.getTotalPrice())) throw new PaymentException();
        //if(creditCard.getExpirationDate().isBefore(LocalDate.now())) throw new PaymentException();
        else{
            purchase.setCreditCardNumber(creditCard);
            purchase.setDate(LocalDate.now());
        }

    }
}
