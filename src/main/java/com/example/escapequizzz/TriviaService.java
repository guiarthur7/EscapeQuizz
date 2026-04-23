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

    private static final String API_URL = "https://opentdb.com/api.php?amount=1&type=multiple";
    private static final int MAX_ATTEMPTS = 3;
    private final HttpClient client = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    public Question fetchQuestion() {
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

                Question question = parseJson(response.body());
                if (question != null) {
                    return question;
                }
            } catch (Exception e) {
                System.err.println("Tentative API " + attempt + " echouee: " + e.getMessage());
            }
        }

        return null;
    }

    private Question parseJson(String responseBody) {
        if (responseBody == null || responseBody.isBlank()) {
            return null;
        }

        ApiResponse apiResponse = gson.fromJson(responseBody, ApiResponse.class);
        if (apiResponse == null || apiResponse.response_code != 0 || apiResponse.results == null || apiResponse.results.isEmpty()) {
            return null;
        }

        ApiQuestion rawQuestion = apiResponse.results.get(0);
        if (rawQuestion == null || rawQuestion.correct_answer == null || rawQuestion.incorrect_answers == null || rawQuestion.incorrect_answers.size() < 3) {
            return null;
        }

        List<String> allAnswers = new ArrayList<>();
        allAnswers.add(rawQuestion.correct_answer);
        allAnswers.addAll(rawQuestion.incorrect_answers);

        Collections.shuffle(allAnswers);
        return new Question(rawQuestion.question, rawQuestion.correct_answer, allAnswers);
    }
}