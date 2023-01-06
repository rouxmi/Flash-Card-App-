package com.example.cd.commande.sceneStrategie;


import com.example.cd.Main;
import com.example.cd.controleurs.AccueilControleur;
import com.example.cd.controleurs.GlobalControleur;
import com.example.cd.modele.PaquetDeCartes;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javax.swing.plaf.basic.BasicColorChooserUI;
import java.io.IOException;
import java.util.ArrayList;

public class SceneAccueil implements ChangeScene {
    Scene newScene;

    //change to scene in menu.fxml
    public SceneAccueil(ArrayList<PaquetDeCartes> paquets, GlobalControleur globalControleur) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Accueil.fxml"));
        AccueilControleur accueilControleur=new AccueilControleur(paquets,globalControleur);
        globalControleur.setAccueil(accueilControleur);
        fxmlLoader.setController(accueilControleur);
        Main.mainStage.setResizable(true);
        newScene = new Scene(fxmlLoader.load());
        newScene.getStylesheets().add(Main.class.getResource("/style.css").toExternalForm());
    }
    @Override
    public void changetoScene() {
        Main.mainStage.setScene(newScene);
        Main.mainStage.setTitle("Accueil");
        Main.mainStage.show();
    }
}
