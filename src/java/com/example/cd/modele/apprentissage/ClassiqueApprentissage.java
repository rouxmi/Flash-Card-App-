package com.example.cd.modele.apprentissage;

import com.example.cd.modele.Carte;
import com.example.cd.modele.PaquetDeCartes;

import java.util.Queue;

public class ClassiqueApprentissage implements ApprentissageStrategie{
    @Override
    public Carte getCarte(PaquetDeCartes paquet, Queue<Carte> futuresCartes) {
        int index;
        if(futuresCartes.isEmpty()){
            index=0;
        }
        else {index = paquet.getIndiceOfCartes(futuresCartes.peek())+futuresCartes.size();}
        return paquet.getCarte(index%paquet.taillePaquet());
    }

    @Override
    public String toString() {
        return "Classique";
    }
}
