package com.example.cd.modele.apprentissage;

import com.example.cd.modele.Carte;
import com.example.cd.modele.PaquetDeCartes;

public class ClassiqueApprentissage implements ApprentissageStrategie{
    @Override
    public Carte getCarte(PaquetDeCartes paquet) {
        return paquet.getCarte(0);
    }

    @Override
    public String toString() {
        return "Classique";
    }
}
