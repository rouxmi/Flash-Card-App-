package com.example.cd.modele.apprentissage;

import com.example.cd.modele.Carte;
import com.example.cd.modele.PaquetDeCartes;

public class ClassiqueApprentissage implements ApprentissageStrategie{
    @Override
    public Carte getCarte(PaquetDeCartes paquet, int index) {
        return paquet.getCarte(index%paquet.taillePaquet());
    }

    @Override
    public String toString() {
        return "Classique";
    }
}
