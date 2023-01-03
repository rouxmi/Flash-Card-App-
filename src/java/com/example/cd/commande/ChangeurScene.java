package com.example.cd.commande;


import com.example.cd.commande.sceneStrategie.ChangeScene;
import com.example.cd.commande.sceneStrategie.SceneAcceuil;
import com.example.cd.modele.Carte;
import com.example.cd.modele.PaquetDeCartes;

import java.io.IOException;
import java.util.ArrayList;

public class ChangeurScene extends Command {
    private ChangeScene changeScene;

    public ChangeurScene(ArrayList<PaquetDeCartes> paquet, PaquetDeCartes paquetDeCartes, Carte carte) {
        super(paquet,paquetDeCartes, carte);
    }

    public void setChangeScene(ChangeScene changeScene) {
        this.changeScene = changeScene;
    }

    public void changeSceneAcceuil() throws IOException {
        setChangeScene(new SceneAcceuil(paquet));
    }

    @Override
    public void execute() {
        changeScene.changetoScene();
    }
}


