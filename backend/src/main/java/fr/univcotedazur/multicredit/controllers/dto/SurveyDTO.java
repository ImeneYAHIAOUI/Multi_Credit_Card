package fr.univcotedazur.multicredit.controllers.dto;

import fr.univcotedazur.multicredit.entities.Question;

import java.util.List;

public class SurveyDTO {

    private String sender;
    private List<Question> questions;

    public SurveyDTO(String sender, List<Question> questions) {
        this.sender = sender;
        this.questions = questions;
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
