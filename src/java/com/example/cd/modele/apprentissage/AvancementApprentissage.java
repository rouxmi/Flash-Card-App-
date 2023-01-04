package com.example.cd.modele.apprentissage;

import com.example.cd.modele.Carte;
import com.example.cd.modele.PaquetDeCartes;

public class AvancementApprentissage implements ApprentissageStrategie{
    @Override
    public String toString() {
        return "Avancement";
    }

    @Override
    public Carte getCarte(PaquetDeCartes paquet, int index) {
        double random = Math.random();
        if (random < 0.3) {
            // non Vue
        } else if (random < 0.6) {
            // a revoir
        } else if (random < 0.85) {
            // debut Acquisition
        } else if (random < 0.95) {
            // fin Acquisition
        } else {
            // Acquis
            //TODO
        }
        return null;
    }
}

