package com.example.cd.commande.sceneStrategie;


import com.example.cd.Main;
import com.example.cd.modele.Carte;
import com.example.cd.modele.PaquetDeCartes;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.ArrayList;

public class SceneAcceuil implements ChangeScene {
    Scene newScene;

    //change to scene in menu.fxml
    public SceneAcceuil(ArrayList<PaquetDeCartes> paquet) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Accueil.fxml"));
        fxmlLoader.setController(new com.example.cd.controleurs.AccueilControleur(paquet));
        newScene = new Scene(fxmlLoader.load());
        Main.mainStage.setResizable(false);

    }
    @Override
    public void changetoScene() {
        Main.mainStage.setScene(newScene);
        Main.mainStage.setTitle("Accueil");
        Main.mainStage.show();
    }
}
