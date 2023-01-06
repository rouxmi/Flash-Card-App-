package com.example.cd.commande.sceneStrategie;

import com.example.cd.Main;
import com.example.cd.controleurs.CreationControleur;
import com.example.cd.controleurs.GlobalControleur;
import com.example.cd.modele.PaquetDeCartes;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class SceneCreation implements ChangeScene {
    Scene newScene;

    public SceneCreation(PaquetDeCartes paquetDeCartes, GlobalControleur globalControleur) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Creation.fxml"));
        CreationControleur creationControleur=new CreationControleur(paquetDeCartes,globalControleur);
        globalControleur.setCreation(creationControleur);
        fxmlLoader.setController(creationControleur);
        Main.mainStage.setResizable(true);
        newScene = new Scene(fxmlLoader.load());
        newScene.getStylesheets().add(Main.class.getResource("/style.css").toExternalForm());
    }


    @Override
    public void changetoScene() {
        Main.mainStage.setScene(newScene);
        Main.mainStage.setTitle("Création");
        Main.mainStage.show();

    }

}
