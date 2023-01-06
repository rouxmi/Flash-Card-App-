package com.example.cd.commande;

import com.example.cd.controleurs.GlobalControleur;
import com.example.cd.modele.PaquetDeCartes;

public class AllerGestionCommande extends Commande{

    private GlobalControleur globalControleur;

    public AllerGestionCommande(GlobalControleur globalControleur, PaquetDeCartes paquet) {
        super(null, paquet, null);
        this.globalControleur = globalControleur;
    }

    @Override
    public void execute() throws Exception {
        new MajPaquetGlobalCommande(globalControleur, paquetDeCartes).execute();
        globalControleur.changeSceneVersGestion();
    }

}
