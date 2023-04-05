package fr.univcotedazur.multiCredit.cli.commands;

<<<<<<< Updated upstream:cli/src/main/java/fr/univcotedazur/simpletcfs/cli/commands/AdminCommands.java
import fr.univcotedazur.simpletcfs.cli.CliContext;
import fr.univcotedazur.simpletcfs.cli.model.*;
=======
import fr.univcotedazur.multiCredit.cli.CliContext;
import fr.univcotedazur.multiCredit.cli.model.CliAdmin;
import fr.univcotedazur.multiCredit.cli.model.CliShop;
import fr.univcotedazur.multiCredit.cli.model.CliShopKeeper;
>>>>>>> Stashed changes:cli/src/main/java/fr/univcotedazur/multiCredit/cli/commands/AdminCommands.java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

<<<<<<< Updated upstream:cli/src/main/java/fr/univcotedazur/simpletcfs/cli/commands/AdminCommands.java
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

=======
>>>>>>> Stashed changes:cli/src/main/java/fr/univcotedazur/multiCredit/cli/commands/AdminCommands.java
@ShellComponent
public class AdminCommands {

    public static final String BASE_URI = "/admin";

    @Autowired
    RestTemplate restTemplate;
    private final CliContext cliContext;
    public AdminCommands(CliContext cliContext) {
        this.cliContext = cliContext;
    }
    @ShellMethod("delete an admin (delete ADMIN_ID)")
    public String deleteAdmin(@ShellOption(value = {"-i", "--id"}) Long id) {
        if (id < 0) {
            return "Invalid admin ID";
        }
        try {
            ResponseEntity<String> response = restTemplate.exchange(BASE_URI + "/admin/" + id, HttpMethod.DELETE, null, String.class);
            cliContext.getAdminAccounts().remove(id);
            return "Admin deleted successfully";
        }catch (HttpClientErrorException ex) {
            if(HttpStatus.NOT_FOUND.equals(ex.getStatusCode())){
                return "404 Failed to delete admin : admin not found";
            }
            return "Error while deleting admin";
        }
    }
    @ShellMethod("Register an admin in the multi-credit backend (register ADMIN_NAME ADMIN_MAIL ADMIN_PASSWORD ADMIN_BIRTHDATE)")
    public String registerAdmin( String name, String mail, String password, String birthDate) {
        try {
            CliAdmin res = restTemplate.postForObject(BASE_URI + "/register", new CliAdmin( name,mail,password,birthDate), CliAdmin.class);
            cliContext.getAdminAccounts().put(res.getId(), res);
            return res.toString();
        }catch (HttpClientErrorException ex) {
            if(HttpStatus.CONFLICT.equals(ex.getStatusCode())){
                return "409 Failed to add admin account : account already exists";
            }
            else if(HttpStatus.UNPROCESSABLE_ENTITY.equals(ex.getStatusCode())){
                return "422 Failed tto add admin account : invalid parameters";
            }
            return "Error while adding admin account ";
        }
    }
    @ShellMethod("List all admins")
    public String admins() {
        return cliContext.printAdminAccounts();
    }
    @ShellMethod("Send a mail")
    public String sendMail(String adminMail, String mailContent, String subject) {
        try {
            CliMail res = restTemplate.postForObject(BASE_URI + "/mails/send", new CliMail(adminMail, mailContent, subject), CliMail.class);
            cliContext.getMails().put(res.getSender(), res);
            return res.toString();
        }catch (HttpClientErrorException ex) {
            if(HttpStatus.CONFLICT.equals(ex.getStatusCode())){
                return "409 Failed to send mail : mail already exists";
            }
            else if(HttpStatus.UNPROCESSABLE_ENTITY.equals(ex.getStatusCode())){
                return "422 Failed to send mail : invalid parameters";
            }
            return "Error while sending mail";
        }
    }

    @ShellMethod("Send a survey (sendSurvey ADMIN_MAIL END_DATE --questions QUESTION1:ANSWER1,ANSWER2,ANSWER3;QUESTION2:ANSWER1,ANSWER2)")
    public String sendSurvey(String adminMail, String endDate, @ShellOption(value = {"--questions"}, defaultValue = "") String questionList){
        List<String> questions = List.of(questionList.split(";"));
        List<CliQuestion> cliQuestions = new ArrayList<>();
        for (String question : questions) {
            List<String> val = List.of(question.split(":"));
            List<String> answers = List.of(val.get(1).split(","));
            cliQuestions.add(new CliQuestion(val.get(0), answers));
        }
        try {
            CliSurvey res = restTemplate.postForObject(BASE_URI + "/surveys/send", new CliSurvey(adminMail, endDate, cliQuestions), CliSurvey.class);
            cliContext.getSurveys().put(res.getSender(), res);
            return res.toString();
        }catch (HttpClientErrorException ex) {
            if(HttpStatus.CONFLICT.equals(ex.getStatusCode())){
                return "409 Failed to send survey : survey already exists";
            }
            else if(HttpStatus.UNPROCESSABLE_ENTITY.equals(ex.getStatusCode())){
                return "422 Failed to send survey : invalid parameters";
            }
            return "Error while sending survey";
        }
    }

    ////////////////////////////////////////
    //SHOP COMMANDS & SHOPKEEPER COMMANDS //
    ////////////////////////////////////////
    @ShellMethod("List all shopkeepers")
    public String shopKeepers() {
        return cliContext.printAdminAccounts();
    }
    @ShellMethod("Register a shop in the multi-credit backend (register SHOP_NAME SHOP_ADDRESS )")
    public String addShop(String name, String address) {
        try {
            CliShop res = restTemplate.postForObject(BASE_URI + "/shops/save", new CliShop(name, address), CliShop.class);
            cliContext.getShops().put(res.getId(), res);
            return res.toString();
        }catch (HttpClientErrorException ex) {
            if(HttpStatus.CONFLICT.equals(ex.getStatusCode())){
                return "409 Failed to add shop : shop already exists";
            }
            else if(HttpStatus.UNPROCESSABLE_ENTITY.equals(ex.getStatusCode())){
                return "422 Failed to add shop  :  invalid parameters";
            }
            return "Error while adding shop ";
        }
    }
    @ShellMethod("Register a shop keeper account in the multi-credit backend (register SHOP_ID  SHOPKEEPER_NAME SHOPKEEPER_MAIL SHOPKEEPER_PASSWORD SHOPKEEPER_BIRTHDATE)")
    public String addShopKeeper(Long Shopid, String name, String mail, String password, String birthDate) {
        try {
            CliShopKeeper s=new CliShopKeeper(name,mail ,password,birthDate);
            s.setShopId(Shopid);
            CliShopKeeper res = restTemplate.postForObject(BASE_URI + "/shopKeepers/save", s, CliShopKeeper.class);
            cliContext.getShopKeepers().put(res.getId(), res);
            return res.toString();
        }catch (HttpClientErrorException ex) {
            if(HttpStatus.NOT_FOUND.equals(ex.getStatusCode())) {
                return "404 Failed to add shop keeper : shop not found";
            }
            else if(HttpStatus.CONFLICT.equals(ex.getStatusCode())){
                return "409 Failed to add shop keeper : shop keeper account already exists";
            }
            if(HttpStatus.UNPROCESSABLE_ENTITY.equals(ex.getStatusCode())){
                return "422 Failed to add shop keeper :  invalid parameters";
            }
            return "Error while adding shop keeper";
        }
    }
    @ShellMethod("delete a shop (delete SHOP_ID)")
    public String deleteShop(@ShellOption(value = {"-i", "--id"}) Long id) {
        if (id < 0) {
            return "Invalid shop ID";
        }
        try {
            ResponseEntity<String> response = restTemplate.exchange(BASE_URI + "/shops/" + id, HttpMethod.DELETE, null, String.class);
            cliContext.getShops().remove(id);
            return "Shop deleted successfully";
        }catch (HttpClientErrorException ex) {
            if(HttpStatus.NOT_FOUND.equals(ex.getStatusCode())){
                return "Failed to delete shop : shop not found";
            }
            return "Error while deleting shop";
        }
    }
    @ShellMethod("delete a shop keeêr (delete SHOPKEEPER_ID)")
    public String deleteShopKeeper(@ShellOption(value = {"-i", "--id"}) Long id) {
        if (id < 0) {
            return "Invalid shop keeper ID";
        }
        try {
            ResponseEntity<String> response = restTemplate.exchange(BASE_URI + "/shopKeepers/" + id, HttpMethod.DELETE, null, String.class);
            cliContext.getShopKeepers().remove(id);
            return "Shop keeper deleted successfully";
        }catch (HttpClientErrorException ex) {
            if(HttpStatus.NOT_FOUND.equals(ex.getStatusCode())){
                return "404 Failed to delete shop : shop not found";
            }
            return "Error while deleting shop";
        }
    }
}
