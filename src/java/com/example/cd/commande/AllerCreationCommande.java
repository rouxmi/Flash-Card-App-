package com.example.cd.commande;

import com.example.cd.controleurs.GlobalControleur;
import com.example.cd.modele.Carte;
import com.example.cd.modele.PaquetDeCartes;

public class AllerCreationCommande extends Commande {

    private GlobalControleur globalControleur;

    public AllerCreationCommande(GlobalControleur globalControleur, PaquetDeCartes paquet) {
        super(null, paquet, null);
        this.globalControleur = globalControleur;
    }

    @Override
    public void execute() throws Exception {
        new MajPaquetGlobalCommande(globalControleur, paquetDeCartes).execute();
        paquetDeCartes.ajouterCarte(new Carte());
        new MajCarteGlobalCommande(globalControleur, paquetDeCartes.getCarte(paquetDeCartes.getCartes().size()-1) ).execute();
        globalControleur.changeSceneVersCreation();
    }
}
