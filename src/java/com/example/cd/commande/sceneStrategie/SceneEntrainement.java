package com.example.cd.commande.sceneStrategie;

import com.example.cd.Main;
import com.example.cd.controleurs.EntrainementControleur;
import com.example.cd.controleurs.GlobalControleur;
import com.example.cd.modele.PaquetDeCartes;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class SceneEntrainement implements ChangeScene {
    Scene newScene;

    public SceneEntrainement(PaquetDeCartes paquetDeCartes, GlobalControleur globalControleur, String typeEntrainement) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Entrainement.fxml"));
        EntrainementControleur entrainementControleur= new EntrainementControleur(paquetDeCartes, globalControleur, typeEntrainement);
        globalControleur.setEntrainement(entrainementControleur);
        fxmlLoader.setController(entrainementControleur);
        newScene = new Scene(fxmlLoader.load());
        Main.mainStage.setResizable(false);
        newScene.getStylesheets().add(Main.class.getResource("/style.css").toExternalForm());
    }
    @Override
    public void changetoScene() {
        Main.mainStage.setScene(newScene);
        Main.mainStage.setTitle("Entrainement");
        Main.mainStage.show();
    }

}
