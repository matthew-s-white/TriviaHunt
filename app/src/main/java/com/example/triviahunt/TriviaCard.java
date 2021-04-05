package com.example.triviahunt;

import java.io.Serializable;
import java.util.ArrayList;

public class TriviaCard implements Serializable {

    private String question;
    private String correctAnswer;
    private ArrayList<String> otherAnswers;

    public TriviaCard(String question, String correctAnswer, ArrayList<String> otherAnswers) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.otherAnswers = otherAnswers;
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public ArrayList<String> getOtherAnswers() {
        return otherAnswers;
    }

}
