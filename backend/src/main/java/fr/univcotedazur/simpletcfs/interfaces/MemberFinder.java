package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.MemberAccount;

import java.util.Optional;
import java.util.UUID;

public interface MemberFinder {

    Optional<MemberAccount> findMember(Long id);

    Optional<MemberAccount> findByMail(String mail);

}
