package com.example.cd.modele.apprentissage;

import com.example.cd.modele.Carte;
import com.example.cd.modele.PaquetDeCartes;

import java.util.Queue;

public class RandomApprentissage implements ApprentissageStrategie{
    @Override
    public Carte getCarte(PaquetDeCartes paquet, Queue<Carte> futurCartes) {
        return paquet.getCarte((int)(Math.random()*paquet.taillePaquet()));
    }

    @Override
    public String toString() {
        return "Random";
    }
}
