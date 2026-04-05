module com.btssio66.oarboux.graphiquecastlefight {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics; // Assure-toi que cette ligne est bien présente aussi

    // C'est la ligne cruciale pour cette erreur :
    // Elle permet au module javafx.graphics d'accéder à ton package principal
    // où se trouve ta classe App.
    opens com.btssio66.oarboux.graphiquecastlefight to javafx.graphics, javafx.fxml;

    // Cette ligne était pour l'erreur précédente concernant les contrôleurs
    opens com.btssio66.oarboux.graphiquecastlefight.controllers to javafx.fxml;

    // Si tu as d'autres packages contenant des FXML ou des ressources qui doivent être ouverts,
    // tu devras les ajouter ici également.
    // Exemple si tu as un package "model" qui est utilisé dans FXML ou via réflexion:
    // opens com.btssio66.oarboux.graphiquecastlefight.model to javafx.fxml;
}
