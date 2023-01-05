package com.example.cd.modele.apprentissage;

import com.example.cd.modele.Carte;
import com.example.cd.modele.PaquetDeCartes;

import java.util.Queue;

public class MasterStrategie implements ApprentissageStrategie{

    @Override
    public String toString() {
        return "Master";
    }

    @Override
    public Carte getCarte(PaquetDeCartes paquet, Queue<Carte> futurCartes) {
        double random = Math.random();
        Carte c = paquet.getCarte((int)(Math.random()*paquet.taillePaquet()));
        if(c.getStatsCarte().ValidableAujourdhui()){
            return c;
        }
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
        return this.getCarte(paquet, futurCartes);
    }


}
