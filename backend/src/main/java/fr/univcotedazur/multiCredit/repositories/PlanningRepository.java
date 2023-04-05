package fr.univcotedazur.multiCredit.repositories;

import fr.univcotedazur.multiCredit.entities.Planning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanningRepository extends JpaRepository<Planning, Long> {

}
