package com.example.cd.modele.apprentissage;

import com.example.cd.modele.Carte;
import com.example.cd.modele.PaquetDeCartes;

public interface ApprentissageStrategie {

    @Override
    public String toString();

    public Carte getCarte(PaquetDeCartes paquet, int index);
}
