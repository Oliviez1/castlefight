package com.btssio66.oarboux.graphiquecastlefight.controllers;

import com.btssio66.oarboux.graphiquecastlefight.App;
import com.btssio66.oarboux.graphiquecastlefight.model.Combattant;
import com.btssio66.oarboux.graphiquecastlefight.model.Personnage;
import com.btssio66.oarboux.graphiquecastlefight.model.ScoreManager;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.net.URL;
import java.util.Random;

public class GameSceneController {

    @FXML private Label player1Label;
    @FXML private Label player2Label;
    @FXML private ImageView player1Image;
    @FXML private ImageView player2Image;
    @FXML private Button attackButton;
    @FXML private ProgressBar hpBar1;
    @FXML private ProgressBar hpBar2;
    @FXML private TextFlow combatLogFlow;
    @FXML private ScrollPane scrollPane;

    @FXML private VBox endGameBox;
    @FXML private TextArea rankingArea;

    private Personnage p1;
    private Personnage p2;
    private int p1MaxHp;
    private int p2MaxHp;
    
    private Random random = new Random();

    private String[] verbes = {
        "frappe", "attaque", "cogne", "blesse", "touche", "percute", "découpe", "brûle", "tranche"
    };

    public void initData(String id1, String id2) {
        String nom1 = getRealName(id1);
        String nom2 = getRealName(id2);

        p1 = new Combattant(nom1);
        p2 = new Combattant(nom2);

        applyClassStats(p1, id1);
        applyClassStats(p2, id2);
        
        p1MaxHp = p1.getVie();
        p2MaxHp = p2.getVie();

        if (player1Image != null) player1Image.setImage(getImageFromId(id1));
        if (player2Image != null) player2Image.setImage(getImageFromId(id2));

        hpBar1.setProgress(1.0);
        hpBar2.setProgress(1.0);
        updateInfos();

        combatLogFlow.getChildren().clear();
        addText("LE COMBAT COMMENCE !", "text-win");
        
        if (endGameBox != null) endGameBox.setVisible(false);
    }

    private void applyClassStats(Personnage p, String id) {
        switch (id) {
            case "character2": p.setVie(30); break; // Nain
            case "character4": p.setVie(15); break; // Sorcière
            default: p.setVie(20); break;
        }
    }

    @FXML
    private void handleAttack(ActionEvent event) {
        if (p1 == null || p2 == null || p1.getVie() <= 0 || p2.getVie() <= 0) return;

        combatLogFlow.getChildren().clear();

        jouerAction(p1, p2, player2Image, "text-p1");
        if (p2.getVie() <= 0) {
            victoire(p1, p2);
            return;
        }

        addText("\n\n", "text-normal");

        jouerAction(p2, p1, player1Image, "text-p2");
        if (p1.getVie() <= 0) {
            victoire(p2, p1);
        }
        
        updateInfos();
    }
    
    @FXML
    private void handleReplay(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("primary.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 750, 700);
            
            URL cssUrl = App.class.getResource("/css/GraphiqueCastleFight.css");
            if (cssUrl != null) scene.getStylesheets().add(cssUrl.toExternalForm());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jouerAction(Personnage attaquant, Personnage defenseur, ImageView imgDefenseur, String styleClass) {
        int r = random.nextInt(100);
        int degats = attaquant.frapper();
        boolean critique = false;
        boolean esquive = false;

        if (r < 15) {
            esquive = true;
            degats = 0;
        } else if (r > 85) {
            critique = true;
            degats = degats * 2;
        }

        int nouvelleVie = defenseur.getVie() - degats;
        if (nouvelleVie < 0) nouvelleVie = 0;
        defenseur.setVie(nouvelleVie);

        if (esquive) {
            addText("➤ " + attaquant.getNom() + " attaque mais... ", styleClass);
            addText(defenseur.getNom() + " ESQUIVE !", "text-esquive");
        } else {
            String action = verbes[random.nextInt(verbes.length)];
            addText("➤ " + attaquant.getNom(), styleClass);
            addText(" " + action + " ", "text-normal");
            addText(defenseur.getNom(), "text-normal");
            
            if (critique) {
                addText("\n     🔥 COUP CRITIQUE ! -" + degats + " PV 🔥", "text-critique");
                animHit(imgDefenseur, true);
            } else {
                addText("\n     💥 -" + degats + " PV", "text-degats");
                animHit(imgDefenseur, false);
            }
        }
    }

    private void animHit(ImageView img, boolean isBigHit) {
        TranslateTransition shake = new TranslateTransition(Duration.millis(50), img);
        shake.setFromX(0);
        shake.setByX(isBigHit ? 20 : 10);
        shake.setCycleCount(6);
        shake.setAutoReverse(true);
        shake.play();
    }

    private void victoire(Personnage gagnant, Personnage perdant) {
        updateInfos();
        combatLogFlow.getChildren().clear();
        addText("🏆 VICTOIRE 🏆\n", "text-win");
        addText(gagnant.getNom().toUpperCase() + " GAGNE !", "text-win");
        
        if (attackButton != null) {
            attackButton.setDisable(true);
            attackButton.setVisible(false);
        }

        ScoreManager.ajouterVictoire(gagnant.getNom());

        if (endGameBox != null) {
            endGameBox.setVisible(true);
            rankingArea.setText(ScoreManager.getClassement());
        }

        ImageView imgGagnant = (gagnant == p1) ? player1Image : player2Image;
        ImageView imgPerdant = (gagnant == p1) ? player2Image : player1Image;
        ProgressBar barPerdant = (gagnant == p1) ? hpBar2 : hpBar1;

        ScaleTransition zoom = new ScaleTransition(Duration.seconds(1), imgGagnant);
        zoom.setToX(1.3); zoom.setToY(1.3); zoom.play();

        RotateTransition rotate = new RotateTransition(Duration.seconds(1), imgPerdant);
        rotate.setByAngle(360);
        FadeTransition fade = new FadeTransition(Duration.seconds(1), imgPerdant);
        fade.setToValue(0.0);

        ParallelTransition animPerdant = new ParallelTransition(rotate, fade);
        animPerdant.play();
        barPerdant.setVisible(false);
    }
    
    private void addText(String contenu, String styleClass) {
        Text text = new Text(contenu);
        text.getStyleClass().add(styleClass);
        combatLogFlow.getChildren().add(text);
    }

    private void updateInfos() {
        if (player1Label != null) player1Label.setText(p1.getNom() + "\n" + p1.getVie() + " / " + p1MaxHp);
        if (player2Label != null) player2Label.setText(p2.getNom() + "\n" + p2.getVie() + " / " + p2MaxHp);

        if (hpBar1 != null) { 
            hpBar1.setProgress((double)p1.getVie() / (double)p1MaxHp); 
            updateBarStyle(hpBar1, p1.getVie()); 
        }
        if (hpBar2 != null) { 
            hpBar2.setProgress((double)p2.getVie() / (double)p2MaxHp); 
            updateBarStyle(hpBar2, p2.getVie()); 
        }
    }

    private void updateBarStyle(ProgressBar bar, int vie) {
        if (vie <= 5) bar.setStyle("-fx-accent: #ff0000;"); 
        else if (vie <= 10) bar.setStyle("-fx-accent: #ffaa00;"); 
        else bar.setStyle("-fx-accent: #00ff00;"); 
    }

    private String getRealName(String id) {
        switch (id) {
            case "character1": return "Elfe";
            case "character2": return "Nain";
            case "character3": return "Guerrier";
            case "character4": return "Sorcière";
            default: return "Combattant";
        }
    }

    private Image getImageFromId(String id) {
        String name = "";
        switch (id) {
            case "character1": name = "pixellab-an-elf--boy---and-armor-with-b-1762423256113.png"; break;
            case "character2": name = "pixellab-dwarf--boy--with-helmet-and-ha-1762423570721.png"; break;
            case "character3": name = "pixellab-warrior-with-plastron-and-axe--1762423914101.png"; break;
            case "character4": name = "pixellab-witch--girl--with-potion-and-h-1762423722830.png"; break;
        }
        try {
            URL url = App.class.getResource("/images/" + name);
            if (url == null) url = App.class.getResource("/" + name);
            if (url != null) return new Image(url.toString());
            else return null;
        } catch (Exception e) {
            return null;
        }
    }
}