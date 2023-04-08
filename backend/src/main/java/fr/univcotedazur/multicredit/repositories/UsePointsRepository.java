package fr.univcotedazur.multicredit.repositories;

import fr.univcotedazur.multicredit.entities.UsePoints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsePointsRepository extends JpaRepository<UsePoints, Long> {
}
