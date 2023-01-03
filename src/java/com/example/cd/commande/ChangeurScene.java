package com.example.cd.commande;


import com.example.cd.commande.sceneStrategie.*;
import com.example.cd.modele.Carte;
import com.example.cd.modele.PaquetDeCartes;

import java.io.IOException;
import java.util.ArrayList;

public class ChangeurScene extends Commande {
    private ChangeScene changeScene;

    public ChangeurScene(ArrayList<PaquetDeCartes> paquet, PaquetDeCartes paquetDeCartes, Carte carte) {
        super(paquet,paquetDeCartes, carte);
    }

    public void setChangeScene(ChangeScene changeScene) {
        this.changeScene = changeScene;
    }

    public void changeSceneAcceuil() throws IOException {
        setChangeScene(new SceneAccueil(paquet));
    }

    public void changeSceneCreation() throws Exception {
        setChangeScene(new SceneCreation(paquetDeCartes));
    }

    public void changeSceneEntrainement() throws Exception {
        setChangeScene(new SceneEntrainement(paquetDeCartes));
    }

    public void changeSceneGestion() throws Exception {
        setChangeScene(new SceneGestion(paquetDeCartes));
    }



    @Override
    public void execute() {
        changeScene.changetoScene();
    }
}


