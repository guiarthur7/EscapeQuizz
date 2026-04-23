package com.example.escapequizzz;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

public class QuizzController {

	private static final int TARGET_SCORE = 5;

	@FXML private Label questionLabel, scoreLabel;
	@FXML private Button answerA, answerB, answerC, answerD;

	private final TriviaService triviaService = new TriviaService();
	private List<Question> questionStock;
	private Question currentQuestion;
	private int score = 0;

	@FXML
	private void initialize() {
		updateScoreLabel();
		loadNextQuestion();
	}

	@FXML private void onAnswerA() { handleAnswer(answerA.getText()); }
	@FXML private void onAnswerB() { handleAnswer(answerB.getText()); }
	@FXML private void onAnswerC() { handleAnswer(answerC.getText()); }
	@FXML private void onAnswerD() { handleAnswer(answerD.getText()); }

	private void handleAnswer(String selectedAnswer) {
		if (currentQuestion == null) {
			loadNextQuestion();
			return;
		}

		if (selectedAnswer.equals(currentQuestion.correctAnswer)) {
			score++;
		} else {
			score = 0;
		}

		updateScoreLabel();

		if (score >= TARGET_SCORE) {
			changePage("emilien-fin.fxml");
			return;
		}

		loadNextQuestion();
	}

	private void loadNextQuestion() {
		if (questionStock == null || questionStock.isEmpty()) {
			questionStock = triviaService.fetchQuestions();
		}

		if (questionStock != null && !questionStock.isEmpty()) {
			currentQuestion = questionStock.remove(0);

			questionLabel.setText(decodeHtml(currentQuestion.text));
			answerA.setText(decodeHtml(currentQuestion.allAnswers.get(0)));
			answerB.setText(decodeHtml(currentQuestion.allAnswers.get(1)));
			answerC.setText(decodeHtml(currentQuestion.allAnswers.get(2)));
			answerD.setText(decodeHtml(currentQuestion.allAnswers.get(3)));
		} else {
			questionLabel.setText("Erreur : Impossible de charger des questions.");
		}
	}

	private void updateScoreLabel() {
		scoreLabel.setText("Score : " + score + " / " + TARGET_SCORE);
	}

	private void changePage(String fxmlFile) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
			Scene scene = new Scene(loader.load());
			Stage stage = (Stage) questionLabel.getScene().getWindow();
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String decodeHtml(String text) {
		if (text == null) return "";
		return text
				.replace("&quot;", "\"")
				.replace("&#039;", "'")
				.replace("&amp;", "&")
				.replace("&eacute;", "é")
				.replace("&egrave;", "è")
				.replace("&agrave;", "à");
	}
}