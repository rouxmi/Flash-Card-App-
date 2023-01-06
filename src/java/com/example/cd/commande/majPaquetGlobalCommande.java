package com.example.cd.commande;

import com.example.cd.controleurs.GlobalControleur;
import com.example.cd.modele.PaquetDeCartes;

import java.util.ArrayList;

public class majPaquetGlobalCommande extends Commande {

    GlobalControleur globalControleur;

    public majPaquetGlobalCommande(GlobalControleur globalControleur, PaquetDeCartes paquet) {
        super(null, paquet, null);
        this.globalControleur = globalControleur;
    }

    @Override
    public void execute() throws Exception {
        globalControleur.sauvegarder();
        globalControleur.setPaquet(paquetDeCartes);
    }
}
