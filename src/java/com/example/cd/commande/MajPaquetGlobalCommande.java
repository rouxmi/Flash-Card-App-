package com.example.cd.commande;

import com.example.cd.controleurs.GlobalControleur;
import com.example.cd.modele.PaquetDeCartes;

public class MajPaquetGlobalCommande extends Commande {

    GlobalControleur globalControleur;

    public MajPaquetGlobalCommande(GlobalControleur globalControleur, PaquetDeCartes paquet) {
        super(null, paquet, null);
        this.globalControleur = globalControleur;
    }

    @Override
    public void execute() throws Exception {
        globalControleur.sauvegarder();
        globalControleur.setPaquet(paquetDeCartes);
    }
}
