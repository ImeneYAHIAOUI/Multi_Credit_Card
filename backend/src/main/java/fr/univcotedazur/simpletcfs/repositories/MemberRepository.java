package fr.univcotedazur.simpletcfs.repositories;

import fr.univcotedazur.simpletcfs.entities.MemberAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;

@Repository
public interface MemberRepository extends JpaRepository<MemberAccount, Long> {

}