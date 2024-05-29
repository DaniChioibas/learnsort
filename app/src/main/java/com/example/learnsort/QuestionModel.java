package com.example.learnsort;

import java.util.Collections;
import java.util.List;

public class QuestionModel {
    private String question;
    private List<String> options;
    private String correctAnswer;

    public QuestionModel() {
        this("", Collections.emptyList(), "");
    }

    public QuestionModel(String question, List<String> options, String correct) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correct;
    }

    // Getters and setters
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrect(String correct) {
        this.correctAnswer = correct;
    }
}