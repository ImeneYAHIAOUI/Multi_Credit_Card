package fr.univcotedazur.multiCredit.components;
import fr.univcotedazur.multiCredit.entities.*;
import fr.univcotedazur.multiCredit.exceptions.*;
import fr.univcotedazur.multiCredit.interfaces.*;
import fr.univcotedazur.multiCredit.repositories.AdminAccountRepository;
import fr.univcotedazur.multiCredit.repositories.MemberRepository;
import fr.univcotedazur.multiCredit.repositories.ShopKeeperAccountRepository;
import fr.univcotedazur.multiCredit.repositories.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final ShopFinder shopFinder;
    private final ShopkeeperFinder shopKeeperFinder;

    private MemberRepository memberRepository;

    @Autowired
    public AdminManager(MailSender mailSender, ShopFinder shopFinder,ShopkeeperFinder shopkeeperFinder,AdminAccountRepository adminAccountRepository, ShopRepository shopRepository, ShopKeeperAccountRepository shopKeeperAccountRepository, ShopManager shopManager, MemberRepository memberRepository) {
        this.mailSender = mailSender;
        this.adminAccountRepository = adminAccountRepository;
        this.shopRepository = shopRepository;
        this.shopKeeperAccountRepository = shopKeeperAccountRepository;
        this.shopFinder=shopFinder;
        this.shopKeeperFinder=shopkeeperFinder;
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
    public Shop addShop(String name, String address) throws MissingInformationException,AlreadyExistingShopException{
        if (name == null || address == null) {
            throw new MissingInformationException();
        }
        else if(shopFinder.findShopByAddress(address).isEmpty()){
            Shop shop = new Shop(name, address);
            shopRepository.save(shop);
            return shop;
        }else
            throw new AlreadyExistingShopException();

    }

    @Override
    public void removeShop(Long id ) throws  ShopNotFoundException{
        if(shopFinder.findShopById(id).isPresent()){
            shopRepository.deleteById(id);
        }else throw  new ShopNotFoundException();
    }

    @Override
    public ShopKeeperAccount createShopKeeperAccount(Form form, long id) throws ShopNotFoundException , MissingInformationException,AlreadyExistingMemberException, UnderAgeException {
        Optional<Shop> shop=shopFinder.findShopById(id);
             if(shop.isEmpty())
                throw new ShopNotFoundException();
            if (form.getName() == null || form.getMail() == null || form.getPassword() == null || form.getBirthDate() == null) {
                throw new MissingInformationException();
            }
            ShopKeeperAccount shopKeeperAccount = shopKeeperFinder.findShopkeeperAccountByMail(form.getMail());
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
        public void deleteShopKeeperAccount(Long id)throws ShopKeeperNotFoundException {
                if(shopKeeperFinder.findShopkeeperAccountById(id).isPresent())
                     shopKeeperAccountRepository.deleteById(id);
                else
                    throw new ShopKeeperNotFoundException();

        }

        @Override
        public void sendSurvey(String sender, List<Question> questions) {
            Survey surveyToSend = new Survey(sender, questions);
            List<String> mails = memberRepository.findAll().stream().map(MemberAccount::getMail).toList();
            if(!mailSender.sendSurvey(mails, surveyToSend)){
                System.out.println("Error while sending survey");
                throw new RuntimeException();
            }
        }
        @Override
        public void sendMail(String sender, String mailContent, String subject)throws MailException {
            Mail mailToSend = new Mail(sender, mailContent, subject);
            List<String> mails = memberRepository.findAll().stream().map(MemberAccount::getMail).toList();
            if(!mailSender.sendMail(mails, mailToSend)){
                System.out.println("Error while sending mail");
                throw new MailException();
            }
        }
    }