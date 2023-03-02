package fr.univcotedazur.simpletcfs.repositories;

import fr.univcotedazur.repositories.BasicRepositoryImpl;
import fr.univcotedazur.simpletcfs.entities.AdminAccount;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class AdminAccountRepository extends BasicRepositoryImpl<AdminAccount, UUID> {

}
