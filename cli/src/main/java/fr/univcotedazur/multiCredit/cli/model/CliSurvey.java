package fr.univcotedazur.multiCredit.cli.model;

import java.time.LocalDate;
import java.util.List;

public class CliSurvey {

    private String sender;
    private List<CliQuestion> questions;

    public CliSurvey(String sender, List<CliQuestion> questions) {
        this.sender = sender;
        this.questions = questions;
    }

    public List<CliQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<CliQuestion> questions) {
        this.questions = questions;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
