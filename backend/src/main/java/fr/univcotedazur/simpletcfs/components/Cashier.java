package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.entities.CreditCard;
import fr.univcotedazur.simpletcfs.entities.Product;
import fr.univcotedazur.simpletcfs.entities.Purchase;
import fr.univcotedazur.simpletcfs.exceptions.PaymentException;
import fr.univcotedazur.simpletcfs.interfaces.Bank;
import fr.univcotedazur.simpletcfs.interfaces.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Cashier implements Payment {
    private Bank bank;

    @Autowired
    public Cashier(Bank bank) {
        this.bank = bank;
    }


    public void payment(Purchase purchase, CreditCard creditCard) throws PaymentException {
        bank.pay(creditCard, purchase.getTotalPrice());
        purchase.setCreditCard(creditCard);
        purchase.setDate(new Date(System.currentTimeMillis()));
    }
}
