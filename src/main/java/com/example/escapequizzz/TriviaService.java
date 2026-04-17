package com.example.escapequizzz;

import com.google.gson.Gson;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TriviaService {

    private static final String API_URL = "https://opentdb.com/api.php?amount=1&type=multiple";
    private final HttpClient client = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    public Question fetchQuestion() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return parseJson(response.body());
        } catch (Exception e) {
            return null;
        }
    }

    private Question parseJson(String responseBody) {
        ApiResponse apiResponse = gson.fromJson(responseBody, ApiResponse.class);
        ApiQuestion rawQuestion = apiResponse.results.get(0);

        List<String> allAnswers = new ArrayList<>();
        allAnswers.add(rawQuestion.correct_answer);
        allAnswers.addAll(rawQuestion.incorrect_answers);

        Collections.shuffle(allAnswers);
        return new Question(rawQuestion.question, rawQuestion.correct_answer, allAnswers);
    }
}