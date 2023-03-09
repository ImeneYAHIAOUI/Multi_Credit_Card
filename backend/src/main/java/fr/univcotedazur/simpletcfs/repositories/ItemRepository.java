package fr.univcotedazur.simpletcfs.repositories;

import fr.univcotedazur.simpletcfs.entities.Item;
import fr.univcotedazur.simpletcfs.entities.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
