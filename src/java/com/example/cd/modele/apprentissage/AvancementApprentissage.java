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
        Carte c = null;
        if (random < 0.3) {
            do{
                c = paquet.getCarte((int)(Math.random()*paquet.taillePaquet()));
            }while(c.getStatsCarte().getEtatCarte() != EtatCarte.NonVue);
        } else if (random < 0.6) {
            do{
                c = paquet.getCarte((int)(Math.random()*paquet.taillePaquet()));
            }while(c.getStatsCarte().getEtatCarte() != EtatCarte.ARevoir);
        } else if (random < 0.85) {
            do{
                c = paquet.getCarte((int)(Math.random()*paquet.taillePaquet()));
            }while(c.getStatsCarte().getEtatCarte() != EtatCarte.DebutApprentissage );
        } else if (random < 0.95) {
            do{
                c = paquet.getCarte((int)(Math.random()*paquet.taillePaquet()));
            }while(c.getStatsCarte().getEtatCarte() != EtatCarte.FinApprentissage );
        } else {
            do{
                c = paquet.getCarte((int)(Math.random()*paquet.taillePaquet()));
            }while(c.getStatsCarte().getEtatCarte() != EtatCarte.AcquiseParfaite );
        }
        return null;
    }
}

