package com.example.cd.commande;


import com.example.cd.commande.sceneStrategie.*;
import com.example.cd.controleurs.GlobalControleur;
import com.example.cd.modele.Carte;
import com.example.cd.modele.PaquetDeCartes;

import java.io.IOException;
import java.util.ArrayList;

public class ChangeurScene extends Commande {
    private final GlobalControleur globalControleur;
    private ChangeScene changeScene;

    public ChangeurScene(ArrayList<PaquetDeCartes> paquet, PaquetDeCartes paquetDeCartes, Carte carte, GlobalControleur globalControleur) throws IOException {
        super(paquet,paquetDeCartes, carte);
        this.globalControleur = globalControleur;
    }

    public void setChangeScene(ChangeScene changeScene) {
        this.changeScene = changeScene;
    }

    public void changeSceneAcceuil() throws IOException {
        setChangeScene(new SceneAccueil(paquets,globalControleur));
    }

    public void changeSceneMiniJeu() throws IOException {
        setChangeScene(new SceneMiniJeu(paquetDeCartes,globalControleur));
    }

    public void changeSceneCreation() throws Exception {
        setChangeScene(new SceneCreation(paquetDeCartes,globalControleur));
    }


    public void changeSceneEntrainement(String typeEntrainement) throws Exception {
        setChangeScene(new SceneEntrainement(paquetDeCartes, globalControleur, typeEntrainement));
    }

    public void changeSceneGestion() throws Exception {
        setChangeScene(new SceneGestion(paquetDeCartes,globalControleur));
    }




    @Override
    public void execute() {
        changeScene.changetoScene();
    }
}


