package com.btssio66.oarboux.graphiquecastlefight.controllers;

import com.btssio66.oarboux.graphiquecastlefight.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class PrimaryController {

    @FXML private Label playerTurnLabel;
    @FXML private GridPane imageGrid;
    @FXML private ImageView imageView1, imageView2, imageView3, imageView4;
    @FXML private Label playerLabel1, playerLabel2, playerLabel3, playerLabel4;
    @FXML private Button playButton;

    private int currentPlayer = 1;
    private String savedID1 = null;
    private String savedID2 = null;

    @FXML
    public void initialize() {
        if(playButton != null) playButton.setDisable(true);
        if(playerTurnLabel != null) playerTurnLabel.setText("Joueur 1 : Choisissez votre personnage");
    }

    @FXML
    private void handleImageClick(MouseEvent event) {
        StackPane clickedPane = (StackPane) event.getSource();
        String characterId = (String) clickedPane.getUserData();

        if (currentPlayer == 1) {
            savedID1 = characterId;
            highlightSelection(characterId, "Joueur 1");
            playerTurnLabel.setText("Joueur 2 : Choisissez votre personnage");
            currentPlayer = 2;

        } else if (currentPlayer == 2) {
            if (characterId.equals(savedID1)) return; 

            savedID2 = characterId;
            highlightSelection(characterId, "Joueur 2");
            playerTurnLabel.setText("Prêt à combattre !");
            playButton.setDisable(false);
            currentPlayer = 0;
        }
    }

    @FXML
    private void handlePlayButton(ActionEvent event) {
        if (savedID1 != null && savedID2 != null) {
            try {
                // 1. Charger le fichier FXML de la scène de jeu
                FXMLLoader loader = new FXMLLoader(App.class.getResource("gameScene.fxml"));
                Parent root = loader.load();

                // 2. Envoyer les infos (qui joue ?)
                GameSceneController gameController = loader.getController();
                if (gameController != null) {
                    gameController.initData(savedID1, savedID2);
                }

                // 3. Créer la nouvelle scène
                Scene scene = new Scene(root, 750, 700);

                // --- FIX : ON AJOUTE LE CSS MANUELLEMENT À LA NOUVELLE SCÈNE ---
                // Vérifie bien que le chemin correspond à ton dossier (/css/ ou juste /)
                URL cssUrl = App.class.getResource("/css/GraphiqueCastleFight.css");
                
                // Si ton fichier est à la racine, décommente la ligne dessous :
                // URL cssUrl = App.class.getResource("/GraphiqueCastleFight.css");

                if (cssUrl != null) {
                    scene.getStylesheets().add(cssUrl.toExternalForm());
                    System.out.println(">>> Style appliqué à la GameScene !");
                } else {
                    System.err.println(">>> ERREUR : CSS introuvable pour la GameScene !");
                }
                // -------------------------------------------------------------

                // 4. Afficher
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void highlightSelection(String characterId, String playerText) {
       // Reset visuel (optionnel)
       resetStyle();
       
       switch (characterId) {
            case "character1": playerLabel1.setText(playerText); break;
            case "character2": playerLabel2.setText(playerText); break;
            case "character3": playerLabel3.setText(playerText); break;
            case "character4": playerLabel4.setText(playerText); break;
       }
    }
    
    private void resetStyle() {
        // Tu peux ajouter ici du code pour enlever les sélections précédentes si besoin
    }
}