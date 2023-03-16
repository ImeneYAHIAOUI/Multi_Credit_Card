package fr.univcotedazur.simpletcfs.repositories;

import fr.univcotedazur.repositories.BasicRepositoryImpl;
import fr.univcotedazur.simpletcfs.entities.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.UUID;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    @Modifying(clearAutomatically = true)
    @Query("update Shop p SET p.address = :address WHERE p.id = :id")
    int updateAddress(@Param("address") String address, @Param("id") Long id);
    @Modifying(clearAutomatically = true)
    @Query("update Shop p SET p.name = :name WHERE p.id = :id")
    int updateName(@Param("name") String name, @Param("id") Long id);
}
