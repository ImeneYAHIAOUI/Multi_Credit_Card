package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.Gift;
import fr.univcotedazur.simpletcfs.entities.Product;
import fr.univcotedazur.simpletcfs.entities.Shop;

import java.util.Optional;
import java.util.UUID;

public interface CatalogFinder {
     Optional<Product> findProductById(Long id);
     Optional<Gift> findGiftById(Long id);

}
