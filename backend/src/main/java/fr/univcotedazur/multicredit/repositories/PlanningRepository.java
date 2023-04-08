package fr.univcotedazur.multicredit.repositories;

import fr.univcotedazur.multicredit.entities.Planning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanningRepository extends JpaRepository<Planning, Long> {
}
