/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btssio66.oarboux.graphiquecastlefight.model;

/**
 *
 * @author Utilisateur
 */
public class Nain extends Personnage {

    public Nain(String nom) {
        super(nom);
    }

    @Override
    public int frapper() {
        int forceDuCoup = calculerDegats();
        System.out.println(this.nom + " frappe avec son marteau et provoque "+forceDuCoup+ " de dégats.");
        return forceDuCoup;
    }

    @Override
    public void sePresenter() {
        System.out.println("Un nain qui frappe fort, très fort !");
    }
}
