package fr.univcotedazur.simpletcfs.repositories;

import fr.univcotedazur.simpletcfs.entities.Planning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;

@Repository
public interface PlanningRepository extends JpaRepository<Planning, Long> {
    @Modifying(clearAutomatically = true)
    @Query("update Planning p SET p.openingHours = :openingHours WHERE p.id = :id")
    public int updateOpeningHours(@Param("openingHours") LocalTime openingHours, @Param("id") Long id);
    @Modifying(clearAutomatically = true)
    @Query("update Planning p SET p.closingHours = :closingHours WHERE p.id = :id")
    public int updateClosingHours(@Param("closingHours") LocalTime closingHours, @Param("id") Long id);
}
