package fr.univcotedazur.multicredit.interfaces;

import fr.univcotedazur.multicredit.entities.MemberAccount;

import java.util.List;
import java.util.Optional;

public interface MemberFinder {

    Optional<MemberAccount> findById(Long id);

    Optional<MemberAccount> findByMail(String mail);

    List<MemberAccount> findAll();
}
