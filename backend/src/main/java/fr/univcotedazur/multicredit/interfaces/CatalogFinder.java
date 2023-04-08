package fr.univcotedazur.multicredit.interfaces;

import fr.univcotedazur.multicredit.entities.Gift;
import fr.univcotedazur.multicredit.entities.Product;

import java.util.Optional;

public interface CatalogFinder {
    Optional<Product> findProductById(Long id);

    Optional<Gift> findGiftById(Long id);
}
