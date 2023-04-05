package fr.univcotedazur.multiCredit.interfaces;

import fr.univcotedazur.multiCredit.entities.Gift;
import fr.univcotedazur.multiCredit.entities.Product;

import java.util.Optional;

public interface CatalogFinder {
     Optional<Product> findProductById(Long id);
     Optional<Gift> findGiftById(Long id);

}
