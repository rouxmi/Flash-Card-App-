package com.example.cd.controleurs;

import com.example.cd.Observateur;
import com.example.cd.SujetObserve;
import com.example.cd.commande.quitterApplicationCommande;
import com.example.cd.modele.PaquetDeCartes;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.transform.Rotate;

import java.net.URL;
import java.util.ResourceBundle;

public class EntrainementControleur extends SujetObserve implements Initializable, Observateur {
    private PaquetDeCartes paquet;
    private GlobalControleur globalControleur;
    @FXML
    private ToggleButton toggleFlashCard;
    private int decompte = 3;

    @FXML
    private Label compteurLabel;
    public EntrainementControleur(PaquetDeCartes paquet, GlobalControleur globalControleu){
        this.paquet=paquet;
     // paquet.ajouterObservateur(this);
    }
    public Observateur observateur;

    public void setObservateur(Observateur observateur) {
        this.observateur = observateur;
    }

    public void notifierObservateur() {
        observateur.reagir();
    }
    @Override
    public void reagir() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO : modifier avec la carte courante
        compteurLabel.setText(String.valueOf(decompte));
        toggleFlashCard.setText("Question");
        Timeline compteur = new Timeline((new KeyFrame(javafx.util.Duration.seconds(1), event -> {
            compteurLabel.setText(String.valueOf(decompte));
            decompte--;
        })));
        compteur.setCycleCount(4);
        compteur.play();
    }

    @FXML
    public void quitterAppli() {
        (new quitterApplicationCommande()).execute();
    }
    @FXML
    public void allerAccueil() throws Exception {
        globalControleur.changeSceneVersAccueil();
    }
    @FXML
    public void voirPaquet() throws Exception {
        // TODO : verifier le paquet courant
        globalControleur.changeSceneVersGestion();
    }
    @FXML
    public void majFlashCard() {
        // TODO : modifier avec le paquet courant
        RotateTransition rotate = new RotateTransition();
        rotate.setNode(toggleFlashCard);
        rotate.setDuration(javafx.util.Duration.seconds(1));
        rotate.setAxis(Rotate.Y_AXIS);
        rotate.setByAngle(180);
        rotate.play();
        if (toggleFlashCard.isSelected()) {
            toggleFlashCard.setText("Reponse");
        } else {
            toggleFlashCard.setText("Question");
        }
    }
}
