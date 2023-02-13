package fr.univcotedazur.simpletcfs.repositories;

import fr.univcotedazur.repositories.BasicRepositoryImpl;
import fr.univcotedazur.simpletcfs.entities.MemberAccount;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class MemberAccountRepository extends BasicRepositoryImpl<MemberAccount, UUID> {

}
