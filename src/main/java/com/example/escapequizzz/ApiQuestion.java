package com.example.escapequizzz;

import java.util.List;

public class ApiQuestion {
    public String question;
    public String correct_answer;
    public List<String> incorrect_answers;

    public ApiQuestion(String question, String correct_answer, List<String> incorrect_answers) {
        this.question = question;
        this.correct_answer = correct_answer;
        this.incorrect_answers = incorrect_answers;
    }
}