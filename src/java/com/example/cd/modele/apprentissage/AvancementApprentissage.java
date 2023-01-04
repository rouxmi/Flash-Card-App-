package com.example.cd.modele.apprentissage;

import com.example.cd.modele.Carte;
import com.example.cd.modele.PaquetDeCartes;
import com.example.cd.statistiques.EtatCarte;

public class AvancementApprentissage implements ApprentissageStrategie{
    @Override
    public String toString() {
        return "Avancement";
    }

    @Override
    public Carte getCarte(PaquetDeCartes paquet, int index) {
        double random = Math.random();
        Carte c = paquet.getCarte((int)(Math.random()*paquet.taillePaquet()));
        switch (c.getStatsCarte().getEtatCarte()) {
            case NonVue, ARevoir -> {
                if (random < 0.75) {
                    return c;
                }
            }
            case DebutApprentissage -> {
                if (random < 0.30) {
                    return c;
                }
            }
            case FinApprentissage -> {
                if (random < 0.15 ) {
                    return c;
                }
            }
            case AcquiseParfaite -> {
                if (random < 0.1) {
                    return c;
                }
            }
        }
        return this.getCarte(paquet,index);
    }
}

