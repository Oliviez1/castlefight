package com.btssio66.oarboux.graphiquecastlefight;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 750, 700);

        // --- CHARGEMENT DU CSS ---
        // On va chercher dans le dossier /css/ que l'on voit sur ta photo
        URL cssUrl = App.class.getResource("/css/GraphiqueCastleFight.css");
        
        if (cssUrl != null) {
            System.out.println("✅ STYLE CHARGÉ !");
            scene.getStylesheets().add(cssUrl.toExternalForm());
        } else {
            System.err.println("❌ ERREUR : Le fichier CSS est introuvable dans /src/main/resources/css/");
        }
        // -------------------------

        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}