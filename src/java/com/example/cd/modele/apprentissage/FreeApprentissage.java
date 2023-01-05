package com.example.cd.modele.apprentissage;

import com.example.cd.modele.Carte;
import com.example.cd.modele.PaquetDeCartes;
import com.example.cd.statistiques.EtatCarte;

import java.util.ArrayList;
import java.util.Queue;

public class FreeApprentissage implements ApprentissageStrategie{

    double nonVue;
    double debutApprentissage;
    double aRevoir;
    double finApprentissage;
    double acquiseParfaite;


    public FreeApprentissage(double nonVue, double debutApprentissage, double aRevoir, double finApprentissage, double acquiseParfaite) {
        this.nonVue = nonVue;
        this.debutApprentissage = debutApprentissage;
        this.aRevoir = aRevoir;
        this.finApprentissage = finApprentissage;
        this.acquiseParfaite = acquiseParfaite;
    }

    @Override
    public String toString() {
        return "Free";
    }

    @Override
    public Carte getCarte(PaquetDeCartes paquet, Queue<Carte> futurCartes) {
        double random = Math.random();
        Carte c;
        ArrayList<Carte> notInQueue= new ArrayList<>();
        if(random < nonVue){
            if(paquet.getCartesNonVue().size() == 0 || futurCartes.containsAll(paquet.getCartesNonVue()) ){
                random+=nonVue;
            }else{
                do{
                    for(Carte carte : paquet.getCartesNonVue()){
                        if(!futurCartes.contains(carte)){
                            notInQueue.add(carte);
                        }
                    }
                    c = notInQueue.get((int)(Math.random()*(notInQueue.size()-1)));
                }
                while(c.getStatsCarte().getEtatCarte() != EtatCarte.NonVue);
                return c;
            }
        }
        if(random < nonVue + aRevoir){
            if(paquet.getCartesARevoir().size() == 0 || futurCartes.containsAll(paquet.getCartesARevoir()) ){
                random+=aRevoir;
            }else{
                do{
                    for(Carte carte : paquet.getCartesARevoir()){
                        if(!futurCartes.contains(carte)){
                            notInQueue.add(carte);
                        }
                    }
                    c = notInQueue.get((int)(Math.random()*(notInQueue.size()-1)));
                }
                while(c.getStatsCarte().getEtatCarte() != EtatCarte.ARevoir);
                return c;
            }

        }
        if (random < nonVue + aRevoir + debutApprentissage){
            if(paquet.getCartesDebutApprentissage().size() == 0 || futurCartes.containsAll(paquet.getCartesDebutApprentissage()) ){
                random+=debutApprentissage;
            }else{
                do{
                    for(Carte carte : paquet.getCartesDebutApprentissage()){
                        if(!futurCartes.contains(carte)){
                            notInQueue.add(carte);
                        }
                    }
                    c = notInQueue.get((int)(Math.random()*(notInQueue.size()-1)));
                }
                while(c.getStatsCarte().getEtatCarte() != EtatCarte.DebutApprentissage);
                return c;
            }

        }
        if (random < nonVue + aRevoir + debutApprentissage + finApprentissage){
            if(paquet.getCartesFinApprentissage().size() == 0 || futurCartes.containsAll(paquet.getCartesFinApprentissage()) ){
                random+=nonVue;
            }else{
                do{
                    for(Carte carte : paquet.getCartesFinApprentissage()){
                        if(!futurCartes.contains(carte)){
                            notInQueue.add(carte);
                        }
                    }
                    c = notInQueue.get((int)(Math.random()*(notInQueue.size()-1)));
                }
                while(c.getStatsCarte().getEtatCarte() != EtatCarte.FinApprentissage);
                return c;
            }

        }
        do{
            for(Carte carte : paquet.getCartesAcquiseParfaite()){
                if(!futurCartes.contains(carte)){
                    notInQueue.add(carte);
                }
            }
            c = notInQueue.get((int)(Math.random()*(notInQueue.size()-1)));
        }
        while(c.getStatsCarte().getEtatCarte() != EtatCarte.AcquiseParfaite);
        return c;


    }



}

