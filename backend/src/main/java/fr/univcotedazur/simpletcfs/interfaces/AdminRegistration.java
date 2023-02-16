package fr.univcotedazur.simpletcfs.interfaces;

import fr.univcotedazur.simpletcfs.entities.AdminAccount;
import fr.univcotedazur.simpletcfs.entities.Form;
import fr.univcotedazur.simpletcfs.exceptions.MissingInformationException;

public interface AdminRegistration {
    public AdminAccount createAdminAccount(Form form) throws MissingInformationException;
    public void deleteAdminAccount(AdminAccount account);
}
