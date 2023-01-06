package com.example.cd.commande;

import com.example.cd.controleurs.*;
import com.example.cd.modele.PaquetDeCartes;

import java.util.ArrayList;

public class ImporterCommande extends Commande{

    private GlobalControleur globalControleur;

    public ImporterCommande(GlobalControleur globalControleur, ArrayList<PaquetDeCartes> paquets) {
        super(paquets, null, null);
        this.globalControleur = globalControleur;

    }

    @Override
    public void execute() throws Exception {
        globalControleur.importerPaquets();
        globalControleur.changeSceneVersAccueil();
    }
}
