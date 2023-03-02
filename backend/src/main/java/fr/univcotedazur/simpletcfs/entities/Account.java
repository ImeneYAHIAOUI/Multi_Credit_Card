package fr.univcotedazur.simpletcfs.entities;



import java.time.LocalDate;
import java.util.UUID;

 public class Account {

    private UUID id;

    private String name;

    private String mail;

    private String password;

    private LocalDate birthDate;

     public Account(UUID id, String name, String mail, String password, LocalDate birthDate) {
         this.id = id;
         this.name = name;
         this.mail = mail;
         this.password = password;
         this.birthDate = birthDate;
     }

     public UUID getId() {
         return id;
     }

     public void setId(UUID id) {
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
