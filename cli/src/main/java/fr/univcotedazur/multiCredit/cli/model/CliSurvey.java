package fr.univcotedazur.simpletcfs.cli.model;

import java.time.LocalDate;
import java.util.List;

public class CliSurvey {

    private String sender;
    private String endDate;
    private List<CliQuestion> questions;

    public CliSurvey(String sender, String endDate, List<CliQuestion> questions) {
        this.sender = sender;
        this.endDate = endDate;
        this.questions = questions;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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
