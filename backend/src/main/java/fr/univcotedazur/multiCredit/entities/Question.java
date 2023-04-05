<<<<<<< Updated upstream:backend/src/main/java/fr/univcotedazur/simpletcfs/entities/Question.java
package fr.univcotedazur.simpletcfs.entities;

import javax.persistence.*;
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
=======
package fr.univcotedazur.multiCredit.entities;

import java.util.List;

public class Question {
    private String title;
    private List<String> possibleAnswers;
>>>>>>> Stashed changes:backend/src/main/java/fr/univcotedazur/multiCredit/entities/Question.java

    public Question(String title, List<String> possibleAnswers) {
        this.title = title;
        this.possibleAnswers = possibleAnswers;
    }

<<<<<<< Updated upstream:backend/src/main/java/fr/univcotedazur/simpletcfs/entities/Question.java
    public Question() {

    }

=======
>>>>>>> Stashed changes:backend/src/main/java/fr/univcotedazur/multiCredit/entities/Question.java
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
<<<<<<< Updated upstream:backend/src/main/java/fr/univcotedazur/simpletcfs/entities/Question.java

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
=======
>>>>>>> Stashed changes:backend/src/main/java/fr/univcotedazur/multiCredit/entities/Question.java
}
