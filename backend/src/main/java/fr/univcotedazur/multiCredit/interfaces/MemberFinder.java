package fr.univcotedazur.multiCredit.interfaces;

import fr.univcotedazur.multiCredit.entities.MemberAccount;

import java.util.List;
import java.util.Optional;

public interface MemberFinder {

    Optional<MemberAccount> findById(Long id);

    Optional<MemberAccount> findByMail(String mail);

    List<MemberAccount> findAll();
}
