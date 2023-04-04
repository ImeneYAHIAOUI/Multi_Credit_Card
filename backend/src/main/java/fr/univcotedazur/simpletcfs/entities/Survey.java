package fr.univcotedazur.simpletcfs.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Survey {

    @NotBlank
    private String sender;

    @NotBlank
    private LocalDate endDate;

    @OneToMany(targetEntity = Question.class)
    private List<Question> questions;
    @Id
    @GeneratedValue
    private Long id;

    public Survey(String sender, LocalDate endDate, List<Question> questions) {
        this.sender = sender;
        this.endDate = endDate;
        this.questions = questions;
    }

    public Survey() {

    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
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
