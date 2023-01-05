package com.example.cd.modele.apprentissage;

import com.example.cd.modele.Carte;
import com.example.cd.modele.PaquetDeCartes;

import java.util.Queue;

public interface ApprentissageStrategie {

    @Override
    public String toString();

    public Carte getCarte(PaquetDeCartes paquet, Queue<Carte> futurCartes);
}
