package fr.univcotedazur.multicredit.connectors.externaldto.externaldto;

import java.util.List;

public class QuestionDTO {
    private String title;

    private List<String> possibleAnswers;

    public QuestionDTO() {
    }

    public QuestionDTO(String title, List<String> possibleAnswers) {
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
