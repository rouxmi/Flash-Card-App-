package com.example.cd.modele.apprentissage;

import com.example.cd.modele.Carte;
import com.example.cd.modele.PaquetDeCartes;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Queue;

public class MiniJeuApprentissage implements ApprentissageStrategie{

    @Override
    public Carte getCarte(PaquetDeCartes paquet, Queue<Carte> futurCartes) {
        ArrayList<Carte> notInQueue= new ArrayList<>();
        for(Carte carte : paquet.getCartesSansMedia()){
            if(!futurCartes.contains(carte)){
                notInQueue.add(carte);
            }
        }
        return notInQueue.get((int)(Math.random()*(notInQueue.size()-1)));
    }

    @Override
    public String toString() {
        return "MiniJeu";
    }
}
