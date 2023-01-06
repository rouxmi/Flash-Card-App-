package com.example.cd.commande;

import com.example.cd.controleurs.GlobalControleur;
import com.example.cd.modele.Carte;
import com.example.cd.modele.PaquetDeCartes;

public class AllerCreationCommande extends Commande {

    private GlobalControleur globalControleur;

    public AllerCreationCommande(GlobalControleur globalControleur, PaquetDeCartes paquet, Carte carte) {
        super(null, paquet, carte);
        this.globalControleur = globalControleur;
    }

    @Override
    public void execute() throws Exception {
        if ( carte == null ) {
            new MajPaquetGlobalCommande(globalControleur, paquetDeCartes).execute();
            paquetDeCartes.ajouterCarte(new Carte());
            new MajCarteGlobalCommande(globalControleur, paquetDeCartes.getCarte(paquetDeCartes.getCartes().size() - 1)).execute();
            globalControleur.changeSceneVersCreation();
        } else {
            new MajPaquetGlobalCommande(globalControleur, paquetDeCartes).execute();
            new MajCarteGlobalCommande(globalControleur, carte).execute();
            globalControleur.changeSceneVersCreation();
        }
    }
}
