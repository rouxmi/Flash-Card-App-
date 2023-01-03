package com.example.cd.commande.sceneStrategie;

import com.example.cd.Main;
import com.example.cd.modele.PaquetDeCartes;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class SceneCreation implements ChangeScene {
    Scene newScene;

    public SceneCreation(PaquetDeCartes paquetDeCartes) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Creation.fxml"));
        fxmlLoader.setController(new com.example.cd.controleurs.CreationControleur(paquetDeCartes));
        newScene = new Scene(fxmlLoader.load());
    }
    @Override
    public void changetoScene() {
        Main.mainStage.setScene(newScene);
        Main.mainStage.setTitle("Création");
        Main.mainStage.show();

    }

}
