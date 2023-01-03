package com.example.cd.modele;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

public class Listpaquets{

    @Expose
    public ArrayList<PaquetDeCartes> paquets;

    @Expose
    private String nom;

    public Listpaquets(ArrayList<PaquetDeCartes> paquets, String nom){
        this.paquets = paquets;
        this.nom = nom;
    }
}
