package fr.univcotedazur.simpletcfs.entities;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.UUID;

@Entity
 public class Account {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String name;

    private String mail;

    private String password;

    private LocalDate birthDate;

     public Account( String name, String mail, String password, LocalDate birthDate) {
         this.name = name;
         this.mail = mail;
         this.password = password;
         this.birthDate = birthDate;
     }

    public Account() {

    }

    public Long getId() {
         return id;
     }

     public void setId(Long id) {
         this.id = id;
     }

     public String getName() {
         return name;
     }

     public void setName(String name) {
         this.name = name;
     }

     public String getMail() {
         return mail;
     }

     public void setMail(String mail) {
         this.mail = mail;
     }

     public String getPassword() {
         return password;
     }

     public void setPassword(String password) {
         this.password = password;
     }

     public LocalDate getBirthDate() {
         return birthDate;
     }

     public void setBirthDate(LocalDate birthDate) {
         this.birthDate = birthDate;
     }
 }
