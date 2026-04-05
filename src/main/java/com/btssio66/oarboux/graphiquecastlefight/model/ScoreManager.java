package com.btssio66.oarboux.graphiquecastlefight.model;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ScoreManager {
    // Une carte pour stocker "Nom du perso" -> "Nombre de victoires"
    private static Map<String, Integer> scores = new HashMap<>();

    public static void ajouterVictoire(String nomPerso) {
        // On ajoute 1 au score actuel, ou on met 1 si c'est la première fois
        scores.put(nomPerso, scores.getOrDefault(nomPerso, 0) + 1);
    }

    public static String getClassement() {
        if (scores.isEmpty()) return "Aucune victoire pour l'instant.";

        // On trie pour avoir le premier en haut (Techique Java Stream)
        return scores.entrySet().stream()
                .sorted((k1, k2) -> k2.getValue().compareTo(k1.getValue())) // Tri décroissant
                .map(entry -> "★ " + entry.getKey() + " : " + entry.getValue() + " victoire(s)")
                .collect(Collectors.joining("\n"));
    }
}