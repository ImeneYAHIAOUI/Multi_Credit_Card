package fr.univcotedazur.multicredit.repositories;

import fr.univcotedazur.multicredit.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
