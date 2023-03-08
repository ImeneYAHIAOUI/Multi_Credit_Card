package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.Product;
import fr.univcotedazur.simpletcfs.entities.Shop;

import java.util.Optional;
import java.util.UUID;

public interface CatalogFinder {
    public Optional<Product> findProductById(Long id);

}
