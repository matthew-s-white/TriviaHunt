package com.example.triviahunt;

import java.io.Serializable;
import java.util.ArrayList;

public class TriviaCard implements Serializable {

    private String question;
    int correctIndex;
    private ArrayList<String> answers;

    public TriviaCard(String question, int correctIndex, ArrayList<String> answers) {
        this.question = question;
        this.correctIndex = correctIndex;
        this.answers = answers;
    }

    public String getQuestion() {
        return question;
    }

    public int getCorrectIndex() {
        return correctIndex;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

}
