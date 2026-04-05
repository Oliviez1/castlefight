package com.btssio66.oarboux.graphiquecastlefight.model;

public class Combattant extends Personnage {

    public Combattant(String nom) {
        super(nom); // Appelle le constructeur de Personnage (met la vie à 20)
    }

    @Override
    public int frapper() {
        // On utilise la méthode de la classe mère pour avoir entre 1 et 4
        return this.calculerDegats();
    }

    @Override
    public void sePresenter() {
        // Pas très utile en graphique, mais obligatoire car abstract
    }
}