package fr.univcotedazur.simpletcfs.components;

import fr.univcotedazur.simpletcfs.entities.*;
import fr.univcotedazur.simpletcfs.exceptions.AlreadyExistingAdminException;
import fr.univcotedazur.simpletcfs.exceptions.AlreadyExistingMemberException;
import fr.univcotedazur.simpletcfs.exceptions.MissingInformationException;
import fr.univcotedazur.simpletcfs.exceptions.UnderAgeException;
import fr.univcotedazur.simpletcfs.interfaces.*;
import fr.univcotedazur.simpletcfs.repositories.AdminAccountRepository;
import fr.univcotedazur.simpletcfs.repositories.MemberRepository;
import fr.univcotedazur.simpletcfs.repositories.ShopKeeperAccountRepository;
import fr.univcotedazur.simpletcfs.repositories.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class AdminManager implements ShopRegistration,ShopkeeperRegistration, AdminRegistration, AdminFinder, MailHandler{

    private final MailSender mailSender;
    private final AdminAccountRepository adminAccountRepository;
    private final ShopRepository shopRepository;
    private final ShopKeeperAccountRepository shopKeeperAccountRepository;
    private final ShopManager shopManager;
    private MemberRepository memberRepository;

    @Autowired
    public AdminManager(MailSender mailSender, AdminAccountRepository adminAccountRepository, ShopRepository shopRepository, ShopKeeperAccountRepository shopKeeperAccountRepository, ShopManager shopManager, MemberRepository memberRepository) {
        this.mailSender = mailSender;
        this.adminAccountRepository = adminAccountRepository;
        this.shopRepository = shopRepository;
        this.shopKeeperAccountRepository = shopKeeperAccountRepository;
        this.shopManager = shopManager;
        this.memberRepository = memberRepository;
    }

    @Override
    public Optional<AdminAccount> findAdminById(Long id) {
        while (adminAccountRepository.findAll().iterator().hasNext()) {
            AdminAccount adminAccount = adminAccountRepository.findAll().iterator().next();
            if (adminAccount.getId().equals(id)) {
                return Optional.of(adminAccount);
            }
        }
        return Optional.empty();
    }

    @Override
    public AdminAccount findAdminByMail(String mail) {
        for (AdminAccount  adminAccount : adminAccountRepository.findAll()) {
            if (adminAccount.getMail().equals(mail)) {
                return adminAccount;
            }
        }
        return null;
    }

    @Override
    public AdminAccount createAdminAccount(Form form) throws MissingInformationException, AlreadyExistingAdminException {
        if (form.getName() == null || form.getMail() == null || form.getPassword() == null || form.getBirthDate() == null) {
            throw new MissingInformationException();
        }
        if(this.findAdminByMail(form.getMail()) != null){
            throw new AlreadyExistingAdminException();
        }
        AdminAccount adminAccount = new AdminAccount(form.getName(), form.getMail(), form.getPassword(), form.getBirthDate());
        adminAccountRepository.save(adminAccount);
        return adminAccount;
    }

    @Override
    public void deleteAdminAccount(AdminAccount account) {
        if(findAdminById(account.getId()).isPresent()){
            adminAccountRepository.deleteById(account.getId());
        }
    }

    @Override
    public Shop addShop(String name, String address) throws MissingInformationException{
        if (name == null || address == null) {
            throw new MissingInformationException();
        }
        Shop shop = new Shop(name, address);
        shopRepository.save(shop);
        return shop;
    }

    @Override
    public void removeShop(Shop shop) {
        if(shopManager.findShopById(shop.getId()).isPresent()){
            shopRepository.deleteById(shop.getId());
        }
    }

    @Override
    public ShopKeeperAccount createShopKeeperAccount(Form form, Long id) throws MissingInformationException,AlreadyExistingMemberException, UnderAgeException {
        Optional<Shop> shop = shopManager.findShopById(id);
        if (shop.isEmpty() || form.getName() == null || form.getMail() == null || form.getPassword() == null || form.getBirthDate() == null) {
            throw new MissingInformationException();
        }
        ShopKeeperAccount shopKeeperAccount = shopManager.findShopkeeperAccountByMail(form.getMail());
        if(shopKeeperAccount != null ){
            throw new AlreadyExistingMemberException();
        }
        if(form.getBirthDate().isAfter(LocalDate.now().minusYears(16))){
            throw new UnderAgeException();
        }
        shopKeeperAccount = new ShopKeeperAccount(form.getName(), form.getMail(), form.getPassword(), form.getBirthDate(), shop.get());
        shop.get().setShopKeeperAccount(shopKeeperAccount);
        shopKeeperAccountRepository.save(shopKeeperAccount);
        return shopKeeperAccount;
    }

    @Override
    public void deleteShopKeeperAccount(ShopKeeperAccount account) {
        if(account!=null && shopManager.findShopkeeperAccountById(account.getId()).isPresent()){
            shopKeeperAccountRepository.deleteById(account.getId());
        }
    }

    @Override
    public void sendSurvey(String sender, LocalDate endDate, List<Question> questions) {
        Survey surveyToSend = new Survey(sender, endDate, questions);
        if(!mailSender.sendSurvey(memberRepository.findAll(), surveyToSend)){
            System.out.println("Error while sending survey");
            throw new RuntimeException();
        }
    }
    @Override
    public void sendMail(String sender, String mailContent, String subject) {
        Mail mailToSend = new Mail(sender, mailContent, subject);
        if(!mailSender.sendMail(memberRepository.findAll(), mailToSend)){
            System.out.println("Error while sending mail");
            throw new RuntimeException();
        }
    }
}
