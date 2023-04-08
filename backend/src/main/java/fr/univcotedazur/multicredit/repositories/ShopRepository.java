package fr.univcotedazur.multicredit.repositories;

import fr.univcotedazur.multicredit.entities.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
}
