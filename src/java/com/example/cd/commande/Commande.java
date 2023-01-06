package com.example.cd.commande;

import com.example.cd.modele.Carte;
import com.example.cd.modele.PaquetDeCartes;

import java.io.IOException;
import java.util.ArrayList;

public abstract class Commande {

    public PaquetDeCartes paquetDeCartes;
    public Carte carte;

    public ArrayList<PaquetDeCartes> paquets;


    public Commande(ArrayList<PaquetDeCartes> paquets, PaquetDeCartes paquetDeCartes, Carte carte) {
        this.paquetDeCartes = paquetDeCartes;
        this.carte = carte;
        this.paquets = paquets;
    }

    public void setPaquet(PaquetDeCartes paquet) {
        this.paquetDeCartes = paquet;
    }

    public void setCarte(Carte carte) {
        this.carte = carte;
    }

    public abstract void execute() throws Exception;

}
