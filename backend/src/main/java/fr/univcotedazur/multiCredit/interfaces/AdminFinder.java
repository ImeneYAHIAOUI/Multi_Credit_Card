package fr.univcotedazur.multiCredit.interfaces;

import fr.univcotedazur.multiCredit.entities.AdminAccount;

import java.util.Optional;

public interface AdminFinder {
    AdminAccount findAdminByMail(String mail);

    Optional<AdminAccount> findAdminById(Long id);
}
