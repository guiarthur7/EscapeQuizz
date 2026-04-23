package com.example.escapequizzz;

import com.google.gson.Gson;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TriviaService {

    private static final String API_URL = "https://opentdb.com/api.php?amount=10&type=multiple";
    private static final int MAX_ATTEMPTS = 3;
    private final HttpClient client = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    public List<Question> fetchQuestions() {
        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(API_URL))
                        .timeout(Duration.ofSeconds(6))
                        .GET()
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() != 200) {
                    continue;
                }

                List<Question> questions = parseJson(response.body());
                if (questions != null && !questions.isEmpty()) {
                    return questions;
                }
            } catch (Exception e) {
                System.err.println("Tentative API " + attempt + " echouee: " + e.getMessage());
            }
        }
        return new ArrayList<>();
    }

    private List<Question> parseJson(String responseBody) {
        if (responseBody == null || responseBody.isBlank()) {
            return null;
        }

        ApiResponse apiResponse = gson.fromJson(responseBody, ApiResponse.class);
        if (apiResponse == null || apiResponse.response_code != 0 || apiResponse.results == null) {
            return null;
        }

        List<Question> questions = new ArrayList<>();
        for (ApiQuestion raw : apiResponse.results) {
            List<String> allAnswers = new ArrayList<>();
            allAnswers.add(raw.correct_answer);
            allAnswers.addAll(raw.incorrect_answers);
            Collections.shuffle(allAnswers);
            questions.add(new Question(raw.question, raw.correct_answer, allAnswers));
        }
        return questions;
    }
}