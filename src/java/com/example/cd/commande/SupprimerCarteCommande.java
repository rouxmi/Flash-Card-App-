package com.example.cd.commande;

import com.example.cd.controleurs.GlobalControleur;

public class SupprimerCarteCommande extends Commande {

    GlobalControleur globalControleur;

    public SupprimerCarteCommande(GlobalControleur globalControleur) {
        super(null, null, null);
        this.globalControleur = globalControleur;
    }

    @Override
    public void execute() throws Exception {
        int indicePrec = this.globalControleur.findIndice(globalControleur.getPaquet(),globalControleur.getCarte())-1;
        this.globalControleur.getPaquet().supprimerCarte(this.globalControleur.getCarte());
        new MajPaquetGlobalCommande(globalControleur, globalControleur.getPaquet()).execute();
        globalControleur.sauvegarder();
        if (indicePrec>=0) {
            new MajCarteGlobalCommande(globalControleur, globalControleur.getPaquet().getCarte(indicePrec)).execute();
            globalControleur.changeSceneVersCreation();
        } else {
            new MajPaquetGlobalCommande(globalControleur, globalControleur.getPaquet()).execute();
            globalControleur.changeSceneVersGestion();
        }

    }
}
