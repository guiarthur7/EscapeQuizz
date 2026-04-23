package com.example.escapequizzz;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class QuizzController {

	private static final int TARGET_SCORE = 5;

	@FXML
	private Label questionLabel;

	@FXML
	private Label scoreLabel;

	@FXML
	private Button answerA;

	@FXML
	private Button answerB;

	@FXML
	private Button answerC;

	@FXML
	private Button answerD;

	private final TriviaService triviaService = new TriviaService();
	private Question currentQuestion;
	private int score = 0;

	@FXML
	private void initialize() {
		updateScoreLabel();
		loadNextQuestion();
	}

	@FXML
	private void onAnswerA() {
		handleAnswer(answerA.getText());
	}

	@FXML
	private void onAnswerB() {
		handleAnswer(answerB.getText());
	}

	@FXML
	private void onAnswerC() {
		handleAnswer(answerC.getText());
	}

	@FXML
	private void onAnswerD() {
		handleAnswer(answerD.getText());
	}

	private void handleAnswer(String selectedAnswer) {
		if (currentQuestion == null) {
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
		currentQuestion = triviaService.fetchQuestion();

		if (currentQuestion == null || currentQuestion.allAnswers == null || currentQuestion.allAnswers.size() < 4) {
			questionLabel.setText("Impossible de charger une question pour le moment.");
			answerA.setDisable(true);
			answerB.setDisable(true);
			answerC.setDisable(true);
			answerD.setDisable(true);
			return;
		}

		answerA.setDisable(false);
		answerB.setDisable(false);
		answerC.setDisable(false);
		answerD.setDisable(false);

		questionLabel.setText(decodeHtml(currentQuestion.text));
		answerA.setText(decodeHtml(currentQuestion.allAnswers.get(0)));
		answerB.setText(decodeHtml(currentQuestion.allAnswers.get(1)));
		answerC.setText(decodeHtml(currentQuestion.allAnswers.get(2)));
		answerD.setText(decodeHtml(currentQuestion.allAnswers.get(3)));
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
			questionLabel.setText("Erreur de navigation vers la page finale.");
		}
	}

	private String decodeHtml(String text) {
		if (text == null) {
			return "";
		}

		return text
				.replace("&quot;", "\"")
				.replace("&#039;", "'")
				.replace("&apos;", "'")
				.replace("&amp;", "&")
				.replace("&lt;", "<")
				.replace("&gt;", ">")
				.replace("&eacute;", "e")
				.replace("&uuml;", "u");
	}
}
