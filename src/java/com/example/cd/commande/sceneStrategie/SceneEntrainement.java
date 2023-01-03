package com.example.cd.commande.sceneStrategie;

import com.example.cd.Main;
import com.example.cd.modele.PaquetDeCartes;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class SceneEntrainement implements ChangeScene {
    Scene newScene;

    public SceneEntrainement(PaquetDeCartes paquetDeCartes) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Gestion.fxml"));
        fxmlLoader.setController(new com.example.cd.controleurs.EntrainementControleur(paquetDeCartes));
        newScene = new Scene(fxmlLoader.load());
        Main.mainStage.setResizable(false);
    }
    @Override
    public void changetoScene() {
        Main.mainStage.setScene(newScene);
        Main.mainStage.setTitle("Gestion");
        Main.mainStage.show();
    }

}