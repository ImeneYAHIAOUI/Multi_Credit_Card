package fr.univcotedazur.simpletcfs.entities;

import java.util.List;

public class Question {
    private String title;
    private List<String> possibleAnswers;

    public Question(String title, List<String> possibleAnswers) {
        this.title = title;
        this.possibleAnswers = possibleAnswers;
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
}
