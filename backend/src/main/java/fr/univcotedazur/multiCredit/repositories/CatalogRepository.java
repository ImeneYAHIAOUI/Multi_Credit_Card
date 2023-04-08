package fr.univcotedazur.multiCredit.repositories;

import fr.univcotedazur.multiCredit.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface CatalogRepository extends JpaRepository<Product, Long> {
    @Modifying(clearAutomatically = true)
    @Query("update Product p SET p.price = :price WHERE p.id = :id")
    int updatePrice(@Param("price") double price, @Param("id") Long id);

    @Modifying(clearAutomatically = true)
    @Query("update Product p SET p.points = :points WHERE p.id = :id")
    int updatePoints(@Param("points") int points, @Param("id") Long id);

    @Modifying(clearAutomatically = true)
    @Query("update Product p SET p.name = :name WHERE p.id = :id")
    int updateName(@Param("name") String name, @Param("id") Long id);
}
