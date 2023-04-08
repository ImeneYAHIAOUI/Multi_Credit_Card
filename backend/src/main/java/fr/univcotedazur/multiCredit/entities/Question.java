package fr.univcotedazur.multiCredit.entities;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
public class Question {

    @NotBlank
    private String title;
    @ElementCollection
    private List<String> possibleAnswers;
    @Id
    @GeneratedValue
    private Long id;

    public Question(String title, List<String> possibleAnswers) {
        this.title = title;
        this.possibleAnswers = possibleAnswers;
    }

    public Question() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getPossibleAnswers() {
        return possibleAnswers;
    }

    public void setPossibleAnswers(List<String> possibleAnswers) {
        this.possibleAnswers = possibleAnswers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
