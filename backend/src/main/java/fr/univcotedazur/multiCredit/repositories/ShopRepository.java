package fr.univcotedazur.multiCredit.repositories;

import fr.univcotedazur.multiCredit.entities.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
}
