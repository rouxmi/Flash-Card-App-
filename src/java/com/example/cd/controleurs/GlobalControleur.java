package com.example.cd.controleurs;

import com.example.cd.commande.ChangeurScene;
import com.example.cd.modele.PaquetDeCartes;

import java.io.IOException;
import java.util.ArrayList;

public class GlobalControleur{
    private ArrayList<PaquetDeCartes> paquets;

    private ChangeurScene changeurScene;
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
        changeurScene=new ChangeurScene(paquets, null, null);
        changeurScene.changeSceneAcceuil();
        changeurScene.execute();
    }

    public void changeSceneVersCreation() throws Exception {
        changeurScene.changeSceneCreation();
        changeurScene.execute();
    }

    public void changeSceneVersEntrainement() throws Exception {
        changeurScene.changeSceneEntrainement();
        changeurScene.execute();
    }

    public void changeSceneVersGestion() throws Exception {
        changeurScene.changeSceneGestion();
        changeurScene.execute();
    }

    public void changeSceneVersAccueil() throws Exception {
        changeurScene.changeSceneAcceuil();
        changeurScene.execute();
    }

}
