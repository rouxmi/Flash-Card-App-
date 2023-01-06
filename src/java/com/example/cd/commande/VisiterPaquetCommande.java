package com.example.cd.commande;

import com.example.cd.controleurs.GlobalControleur;
import com.example.cd.modele.PaquetDeCartes;

public class VisiterPaquetCommande extends Commande {

    private GlobalControleur globalControleur;
    private boolean versEntrainement;

    public VisiterPaquetCommande(GlobalControleur globalControleur, PaquetDeCartes paquet, boolean versEntrainement) {
        super(null, paquet, null);
        this.globalControleur = globalControleur;
        this.versEntrainement = versEntrainement;
    }

    @Override
    public void execute() throws Exception {
        new MajPaquetGlobalCommande(globalControleur, paquetDeCartes).execute();
        if ( versEntrainement ) {
            globalControleur.changeSceneVersEntrainement("entrainement");
        } else {
            globalControleur.changeSceneVersGestion();
        }
    }
}
