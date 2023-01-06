package com.example.cd.commande.sceneStrategie;


import com.example.cd.Main;
import com.example.cd.controleurs.MiniJeuControleur;
import com.example.cd.controleurs.GlobalControleur;
import com.example.cd.modele.PaquetDeCartes;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javax.swing.plaf.basic.BasicColorChooserUI;
import java.io.IOException;
import java.util.ArrayList;

public class SceneMiniJeu implements ChangeScene {
    Scene newScene;

    //change to scene in menu.fxml
    public SceneMiniJeu(PaquetDeCartes paquet, GlobalControleur globalControleur) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MiniJeu.fxml"));
        MiniJeuControleur MiniJeuControleur=new MiniJeuControleur(paquet,globalControleur);
        globalControleur.setMiniJeu(MiniJeuControleur);
        fxmlLoader.setController(MiniJeuControleur);
        Main.mainStage.setResizable(true);
        newScene = new Scene(fxmlLoader.load());
        newScene.getStylesheets().add(Main.class.getResource("/style.css").toExternalForm());

    }
    @Override
    public void changetoScene() {
        Main.mainStage.setScene(newScene);
        Main.mainStage.setTitle("MiniJeu");
        Main.mainStage.show();
    }
}
