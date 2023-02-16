package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.MemberAccount;

import java.util.UUID;

public interface MemberFinder {
    MemberAccount findMember(UUID id);
    MemberAccount findByMail(String mail);

}
