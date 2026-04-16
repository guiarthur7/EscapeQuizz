package com.example.escapequizzz;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloController {

    @FXML
    private Label emilien;

    private String[] dialogues = {
            "Emilien : Tu vas devoir répondre à des questions de culture générale",
            "Emilien : Si tu réussi à répondre à 5 questions consécutives j'irais désamorcer la bombe",
            "Emilien : Jean-Luc compte sur toi !"
    };

    private int indexDialogue = 0;

    @FXML
    protected void Suivant() {
        if (indexDialogue < dialogues.length) {
            emilien.setText(dialogues[indexDialogue]);
            indexDialogue++;
        } else {
            changerDePage("quizz-view.fxml");
        }
    }

    private void changerDePage(String fxmlFile) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            Scene nextScene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) emilien.getScene().getWindow();

            stage.setScene(nextScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}