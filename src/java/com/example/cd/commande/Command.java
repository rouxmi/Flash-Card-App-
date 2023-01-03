package com.example.cd.commande;

import com.example.cd.modele.Carte;
import com.example.cd.modele.PaquetDeCartes;

import java.io.IOException;
import java.util.ArrayList;

public abstract class Command {

    public PaquetDeCartes paquetDeCartes;
    public Carte carte;

    public ArrayList<PaquetDeCartes> paquet;



    public Command(ArrayList<PaquetDeCartes> paquet,PaquetDeCartes paquetDeCartes, Carte carte) {
        this.paquetDeCartes = paquetDeCartes;
        this.carte = carte;
        this.paquet = paquet;
    }
    public abstract void execute() throws IOException;

}
