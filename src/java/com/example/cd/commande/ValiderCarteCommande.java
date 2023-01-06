package com.example.cd.commande;

import com.example.cd.controleurs.GlobalControleur;
import com.example.cd.modele.Carte;
import javafx.scene.control.*;

import java.util.Optional;

public class ValiderCarteCommande extends Commande{

    GlobalControleur globalControleur;
    private Carte carte;


    public ValiderCarteCommande(GlobalControleur globalControleur, Carte carte) {
        super(null, globalControleur.getPaquet(), carte);
        this.globalControleur = globalControleur;
        this.carte=carte;
    }

    @Override
    public void execute() throws Exception {
        new MajPaquetGlobalCommande(globalControleur, paquetDeCartes).execute();
        new MajCarteGlobalCommande(globalControleur, carte).execute();
        globalControleur.changeSceneVersCreation();

    }
}
