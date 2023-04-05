package fr.univcotedazur.multiCredit.repositories;

import fr.univcotedazur.multiCredit.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
