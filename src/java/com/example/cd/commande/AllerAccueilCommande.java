package com.example.cd.commande;

import com.example.cd.controleurs.GlobalControleur;
import com.example.cd.modele.PaquetDeCartes;

public class AllerAccueilCommande extends Commande {

    private GlobalControleur globalControleur;

    public AllerAccueilCommande(GlobalControleur globalControleur, PaquetDeCartes paquet) {
        super(null, paquet, null);
        this.globalControleur = globalControleur;
    }

    @Override
    public void execute() throws Exception {
        new MajPaquetGlobalCommande(globalControleur, paquetDeCartes).execute();
        globalControleur.changeSceneVersAccueil();
    }
}
