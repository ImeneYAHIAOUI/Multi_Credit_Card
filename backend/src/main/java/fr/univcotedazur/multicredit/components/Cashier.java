package fr.univcotedazur.multicredit.components;

import fr.univcotedazur.multicredit.entities.Purchase;
import fr.univcotedazur.multicredit.exceptions.PaymentException;
import fr.univcotedazur.multicredit.interfaces.Bank;
import fr.univcotedazur.multicredit.interfaces.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class Cashier implements Payment {
    private final Bank bank;

    @Autowired
    public Cashier(Bank bank) {
        this.bank = bank;
    }

    public void payment(Purchase purchase, String creditCard) throws PaymentException {
        if (!bank.pay(creditCard, purchase.getTotalPrice())) throw new PaymentException();
        else {
            purchase.setCreditCardNumber(creditCard);
            purchase.setDate(LocalDate.now());
        }
    }
}
