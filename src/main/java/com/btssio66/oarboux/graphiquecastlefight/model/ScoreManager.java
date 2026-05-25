package com.btssio66.oarboux.graphiquecastlefight.model;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ScoreManager {
    // Une carte pour stocker "Nom du perso" -> "Nombre de victoires"
    private static Map<String, Integer> scores = new HashMap<>();
    // Une carte pour stocker "Nom du perso" -> "Nombre de défaites"
    private static Map<String, Integer> defaites = new HashMap<>();

    public static void ajouterVictoire(String nomPerso) {
        // On ajoute 1 au score actuel, ou on met 1 si c'est la première fois
        scores.put(nomPerso, scores.getOrDefault(nomPerso, 0) + 1);
    }

    public static void ajouterDefaite(String nomPerso) {
        // On ajoute 1 aux défaites, ou on met 1 si c'est la première fois
        defaites.put(nomPerso, defaites.getOrDefault(nomPerso, 0) + 1);
    }

    public static String getClassement() {
        if (scores.isEmpty() && defaites.isEmpty()) return "Aucune partie jouée pour l'instant.";

        // On récupère tous les noms (victoires + défaites)
        Map<String, Integer> tousLesNoms = new HashMap<>(scores);
        defaites.keySet().forEach(nom -> tousLesNoms.putIfAbsent(nom, 0));

        // On trie par victoires décroissantes
        return tousLesNoms.keySet().stream()
                .sorted((k1, k2) -> scores.getOrDefault(k2, 0).compareTo(scores.getOrDefault(k1, 0)))
                .map(nom -> "★ " + nom
                        + " : " + scores.getOrDefault(nom, 0) + " victoire(s)"
                        + " | " + defaites.getOrDefault(nom, 0) + " défaite(s)")
                .collect(Collectors.joining("\n"));
    }
}
