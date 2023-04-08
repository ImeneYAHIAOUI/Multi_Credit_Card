package fr.univcotedazur.multicredit.interfaces;

import fr.univcotedazur.multicredit.entities.AdminAccount;
import fr.univcotedazur.multicredit.entities.Form;
import fr.univcotedazur.multicredit.exceptions.AlreadyExistingAdminException;
import fr.univcotedazur.multicredit.exceptions.MissingInformationException;

public interface AdminRegistration {
    AdminAccount createAdminAccount(Form form) throws MissingInformationException, AlreadyExistingAdminException;

    void deleteAdminAccount(AdminAccount account);
}
