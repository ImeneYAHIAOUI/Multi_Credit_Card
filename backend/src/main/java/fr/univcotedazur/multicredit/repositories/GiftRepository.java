package fr.univcotedazur.multicredit.repositories;

import fr.univcotedazur.multicredit.entities.AccountStatus;
import fr.univcotedazur.multicredit.entities.Gift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GiftRepository extends JpaRepository<Gift, Long> {
    @Modifying(clearAutomatically = true)
    @Query("update Gift g SET g.pointsNeeded = :pointsNeeded WHERE g.id = :id")
    int updatePointsNeeded(@Param("pointsNeeded") int pointsNeeded, @Param("id") Long id);

    @Modifying(clearAutomatically = true)
    @Query("update Gift g SET g.description = :description WHERE g.id = :id")
    int updateDescription(@Param("description") String description, @Param("id") Long id);

    @Modifying(clearAutomatically = true)
    @Query("update Gift g SET g.requiredStatus = :requiredStatus WHERE g.id = :id")
    int updateStatus(@Param("requiredStatus") AccountStatus requiredStatus, @Param("id") Long id);
}
