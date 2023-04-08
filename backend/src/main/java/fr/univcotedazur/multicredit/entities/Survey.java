package fr.univcotedazur.multicredit.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.util.List;

public class Survey {

    private String sender;

    private List<Question> questions;


    public Survey(String sender, List<Question> questions) {
        this.sender = sender;
        this.questions = questions;
    }

    public Survey() {
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

}
