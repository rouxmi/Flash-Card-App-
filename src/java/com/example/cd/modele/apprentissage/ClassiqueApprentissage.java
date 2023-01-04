package com.example.cd.modele.apprentissage;

import com.example.cd.modele.Carte;
import com.example.cd.modele.PaquetDeCartes;

public class ClassiqueApprentissage implements ApprentissageStrategie{
    @Override
    public Carte getCarte(PaquetDeCartes paquet) {
        return paquet.getCarte(paquet.getCartes().size()-1);
    }

    @Override
    public String toString() {
        return "Classique";
    }
}
