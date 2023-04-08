package fr.univcotedazur.multiCredit.interfaces;

import fr.univcotedazur.multiCredit.entities.AdminAccount;
import fr.univcotedazur.multiCredit.entities.Form;
import fr.univcotedazur.multiCredit.exceptions.AlreadyExistingAdminException;
import fr.univcotedazur.multiCredit.exceptions.MissingInformationException;

public interface AdminRegistration {
    AdminAccount createAdminAccount(Form form) throws MissingInformationException, AlreadyExistingAdminException;

    void deleteAdminAccount(AdminAccount account);
}
