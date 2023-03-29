package fr.univcotedazur.simpletcfs.entities;

import java.time.LocalDate;
import java.util.List;

public class Survey {
    private LocalDate endDate;
    private List<Question> questions;

    public Survey(LocalDate endDate, List<Question> questions) {
        this.endDate = endDate;
        this.questions = questions;
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
}
