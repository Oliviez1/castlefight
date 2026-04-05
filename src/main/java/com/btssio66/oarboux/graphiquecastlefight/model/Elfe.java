/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btssio66.oarboux.graphiquecastlefight.model;

/**
 *
 * @author Utilisateur
 */
public class Elfe extends Personnage {

    public Elfe(String nom) {
        super(nom);
    }

    @Override
    public int frapper() {
        int forceDuCoup = calculerDegats();
        System.out.println(this.nom + " décoche un flèche et occasionne "+forceDuCoup+ " de dégats.");
        return forceDuCoup;
    }

    @Override
    public void sePresenter() {
        System.out.println("Une dame elfe qui sait manier l'arc comme personne.");
    }
}
