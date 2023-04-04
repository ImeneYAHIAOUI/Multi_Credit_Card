package fr.univcotedazur.simpletcfs.controllers.dto;

import fr.univcotedazur.simpletcfs.entities.Question;

import java.util.List;

public class SurveyDTO {

    private String sender;
    private String endDate;
    private List<Question> questions;

    public SurveyDTO(String sender, String endDate, List<Question> questions) {
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
