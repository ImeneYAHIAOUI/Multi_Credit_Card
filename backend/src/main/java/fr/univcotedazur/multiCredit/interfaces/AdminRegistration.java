package fr.univcotedazur.multiCredit.interfaces;

import fr.univcotedazur.multiCredit.entities.AdminAccount;
import fr.univcotedazur.multiCredit.entities.Form;
import fr.univcotedazur.multiCredit.exceptions.AlreadyExistingAdminException;
import fr.univcotedazur.multiCredit.exceptions.MissingInformationException;

public interface AdminRegistration {
    public AdminAccount createAdminAccount(Form form) throws MissingInformationException, AlreadyExistingAdminException;
    public void deleteAdminAccount(AdminAccount account);
}
