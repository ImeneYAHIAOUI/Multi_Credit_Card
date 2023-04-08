package fr.univcotedazur.multicredit.interfaces;

import fr.univcotedazur.multicredit.entities.AdminAccount;

import java.util.Optional;

public interface AdminFinder {
    AdminAccount findAdminByMail(String mail);

    Optional<AdminAccount> findAdminById(Long id);
}
