package com.example.cd.commande;

import com.example.cd.controleurs.GlobalControleur;
import com.example.cd.modele.Carte;

public class MajCarteGlobalCommande extends Commande {
    GlobalControleur globalControleur;

    public MajCarteGlobalCommande(GlobalControleur globalControleur, Carte carte) {
        super(null, null, carte);
        this.globalControleur = globalControleur;
    }

    @Override
    public void execute() throws Exception {
        globalControleur.sauvegarder();
        globalControleur.setCarte(carte);
    }
}
