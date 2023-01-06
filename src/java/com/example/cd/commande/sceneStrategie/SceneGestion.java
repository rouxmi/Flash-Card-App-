package com.example.cd.commande.sceneStrategie;

import com.example.cd.Main;
import com.example.cd.controleurs.GestionControleur;
import com.example.cd.controleurs.GlobalControleur;
import com.example.cd.modele.PaquetDeCartes;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class SceneGestion implements ChangeScene {

    Scene newScene;



    public SceneGestion(PaquetDeCartes paquetDeCartes, GlobalControleur globalControleur) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Gestion.fxml"));
        GestionControleur gestionControleur = new GestionControleur(paquetDeCartes, globalControleur);
        fxmlLoader.setController(gestionControleur);
        Main.mainStage.setResizable(true);
        newScene = new Scene(fxmlLoader.load());
        newScene.getStylesheets().add(Main.class.getResource("/style.css").toExternalForm());

    }
    @Override
    public void changetoScene() {
        Main.mainStage.setScene(newScene);
        Main.mainStage.setTitle("Gestion");
        Main.mainStage.show();

    }
}
