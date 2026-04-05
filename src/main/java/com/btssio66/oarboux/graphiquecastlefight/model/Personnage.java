package com.btssio66.oarboux.graphiquecastlefight.model;

import java.util.Random;

public abstract class Personnage {

    // Attributs
    protected String nom;
    protected int vie;

    // Constructeur
    public Personnage(String nom) {
        this.nom = nom;
        this.vie = 20; // CONSIGNE : 20 Points de vie au départ
    }

    // Getters et Setters
    public String getNom() { return nom; }
    public int getVie() { return vie; }
    public void setVie(int vie) { this.vie = vie; }

    // Méthode utilitaire pour les dégâts (1 à 4)
    protected int calculerDegats() {
        Random rand = new Random();
        return rand.nextInt(4) + 1; // Génère 0,1,2,3 -> donc +1 donne 1,2,3,4
    }

    // Méthodes abstraites que la classe fille devra définir
    public abstract int frapper();
    public abstract void sePresenter();
    
    // Note : On n'utilise pas la méthode combattre() ici car c'est le Contrôleur 
    // qui va gérer le déroulement tour par tour via le bouton.
}