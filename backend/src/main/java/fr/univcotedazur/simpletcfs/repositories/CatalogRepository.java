package fr.univcotedazur.simpletcfs.repositories;

import fr.univcotedazur.repositories.BasicRepositoryImpl;
import fr.univcotedazur.simpletcfs.entities.ShopKeeperAccount;
import fr.univcotedazur.simpletcfs.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.UUID;


@Repository
public interface CatalogRepository extends JpaRepository<Product, Long> {
    @Modifying(clearAutomatically = true)
    @Query("update Product p SET p.price = :price WHERE p.id = :id")
    public int updatePrice(@Param("price") double price, @Param("id") Long id);
    @Modifying(clearAutomatically = true)
    @Query("update Product p SET p.points = :points WHERE p.id = :id")
    public int updatePoints(@Param("points") int points, @Param("id") Long id);
    @Modifying(clearAutomatically = true)
    @Query("update Product p SET p.name = :name WHERE p.id = :id")
    public int updateName(@Param("name") String name, @Param("id") Long id);
}
