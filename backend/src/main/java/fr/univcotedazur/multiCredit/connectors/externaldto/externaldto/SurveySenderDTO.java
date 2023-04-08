package fr.univcotedazur.multiCredit.connectors.externaldto.externaldto;

import fr.univcotedazur.multiCredit.entities.Question;

import java.util.ArrayList;
import java.util.List;

public class SurveySenderDTO {

    private String sender;
    private List<String> receivers;
    private List<QuestionDTO> questions;

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SurveySenderDTO() {
    }
    public SurveySenderDTO(String sender, List<String> receivers, List<Question> questions) {
        this.sender = sender;
        this.receivers = receivers;
        this.questions = new ArrayList<>();
        for (Question question : questions) {
            this.questions.add(new QuestionDTO(question.getTitle(), question.getPossibleAnswers()));
        }
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public List<String> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<String> receivers) {
        this.receivers = receivers;
    }

    public List<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDTO> questions) {
        this.questions = questions;
    }


}
