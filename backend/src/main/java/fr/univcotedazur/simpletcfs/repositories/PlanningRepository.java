package fr.univcotedazur.simpletcfs.repositories;

import fr.univcotedazur.simpletcfs.entities.Planning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanningRepository extends JpaRepository<Planning, Long> {
    /*@Modifying(clearAutomatically = true)
    @Query("update Planning p SET p.openingHours = :openingHours WHERE p.planning_id = :id")
    public void updateOpeningHours(@Param("openingHours") LocalTime openingHours, @Param("id") Long id);
    @Modifying(clearAutomatically = true)
    @Query("update Planning p SET p.closingHours = :closingHours WHERE p.planning_id = :id")
    public void updateClosingHours(@Param("closingHours") LocalTime closingHours, @Param("id") Long id);*/
}
