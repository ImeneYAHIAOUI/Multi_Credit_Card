package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.Product;
import java.util.List;

public interface CatalogEditor {
    void editCatalog(List<Product> addedProducts, List<Product> removedProducts);
}
