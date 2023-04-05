package fr.univcotedazur.multiCredit.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Survey {

    @NotBlank
    private String sender;

    @OneToMany(targetEntity = Question.class)
    private List<Question> questions;
    @Id
    @GeneratedValue
    private Long id;

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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
