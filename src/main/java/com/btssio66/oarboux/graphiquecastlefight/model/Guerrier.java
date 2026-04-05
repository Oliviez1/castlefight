/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btssio66.oarboux.graphiquecastlefight.model;

/**
 *
 * @author Utilisateur
 */
public class Guerrier extends Personnage {

    public Guerrier(String nom) {
        super(nom);
    }

    @Override
    public int frapper() {
        int forceDuCoup = calculerDegats();
        System.out.println(this.nom + " donne un coup d'épée avec un force de "+forceDuCoup+".");
        return forceDuCoup;
    }

    @Override
    public void sePresenter() {
        System.out.println("Un combattant qui a son épée pour seule compagne.");
    }
}
