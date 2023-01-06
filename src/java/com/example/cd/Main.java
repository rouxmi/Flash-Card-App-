package com.example.cd;

import com.example.cd.controleurs.GlobalControleur;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    public Scene mainscene;
    public static Stage mainStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        GlobalControleur globalControleur = new GlobalControleur();
        Image icon = new Image("/utiles/portugal.png");
        stage.getIcons().add(icon);
        stage.show();
    }
}
