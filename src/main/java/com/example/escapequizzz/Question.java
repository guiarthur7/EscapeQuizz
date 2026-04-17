package com.example.escapequizzz;

import java.util.List;

public class Question {
    public String text;
    public String correctAnswer;
    public List<String> allAnswers;

    public Question(String text, String correctAnswer, List<String> allAnswers) {
        this.text = text;
        this.correctAnswer = correctAnswer;
        this.allAnswers = allAnswers;
    }
}