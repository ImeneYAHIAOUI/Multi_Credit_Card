package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.MemberAccount;

import java.util.Optional;

public interface MemberFinder {

    Optional<MemberAccount> findById(Long id);

    Optional<MemberAccount> findByMail(String mail);

}
