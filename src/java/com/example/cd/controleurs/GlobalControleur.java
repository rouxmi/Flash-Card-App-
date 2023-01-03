package com.example.cd.controleurs;

import com.example.cd.commande.ChangeurScene;
import com.example.cd.modele.PaquetDeCartes;

import java.io.IOException;
import java.util.ArrayList;

public class GlobalControleur{
    private ArrayList<PaquetDeCartes> paquets;
    private AccueilControleur accueil;
    private CreationControleur creation;
    private EntrainementControleur entrainement;
    private GestionControleur gestion;

    public GlobalControleur() throws IOException {
        this.paquets = new ArrayList<PaquetDeCartes>();
        this.accueil = new AccueilControleur(paquets);
        InitialisationChangeurScene();

    }

    public void InitialisationChangeurScene() throws IOException {
        ChangeurScene Changeurscene=new ChangeurScene(paquets, null, null);
        Changeurscene.changeSceneAcceuil();
        Changeurscene.execute();
    }

}
