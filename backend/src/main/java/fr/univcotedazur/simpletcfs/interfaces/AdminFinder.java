package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.AdminAccount;
import fr.univcotedazur.simpletcfs.entities.MemberAccount;

import java.util.UUID;

public interface AdminFinder {
    AdminAccount findById(UUID id);

}
