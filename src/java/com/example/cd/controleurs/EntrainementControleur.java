package com.example.cd.controleurs;

import com.example.cd.Observateur;
import com.example.cd.SujetObserve;
import com.example.cd.commande.quitterApplicationCommande;
import com.example.cd.modele.Carte;
import com.example.cd.modele.PaquetDeCartes;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.transform.Rotate;

import java.net.URL;
import java.util.ResourceBundle;

public class EntrainementControleur extends SujetObserve implements Initializable, Observateur {
    private PaquetDeCartes paquet;
    private GlobalControleur globalControleur;
    private int decompte = 3;
    public Observateur observateur;

    private Carte carteActuelle;
    private String typeEntrainement;
    @FXML
    private ToggleButton toggleFlashCard;
    @FXML
    private Label compteurLabel;
    @FXML
    private Button questionReussieBouton;
    @FXML
    private Button questionLoupeeBouton;

    public EntrainementControleur(PaquetDeCartes paquet, GlobalControleur globalControleur, String typeEntrainement){
        this.paquet=paquet;
        this.globalControleur=globalControleur;
        this.typeEntrainement=typeEntrainement;
     // paquet.ajouterObservateur(this);
    }

    public void setTypeEntrainement(String entrainement){
        this.typeEntrainement=entrainement;
    }
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
        if ( typeEntrainement.equals("entrainement") ) {
            compteurLabel.setVisible(false);
            questionLoupeeBouton.setVisible(true);
            questionReussieBouton.setVisible(true);
        } else if ( typeEntrainement.equals("revision") ) {
            compteurLabel.setVisible(true);
            questionLoupeeBouton.setVisible(false);
            questionReussieBouton.setVisible(false);
        }
    }

    @FXML
    public void quitterAppli() {
        (new quitterApplicationCommande()).execute();
    }
    @FXML
    public void allerAccueil() throws Exception {
        majPaquetGlobalControleur(paquet);
        globalControleur.changeSceneVersAccueil();
    }
    @FXML
    public void voirPaquet() throws Exception {
        // TODO : verifier le paquet courant
        majPaquetGlobalControleur(paquet);
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

    public void majPaquetGlobalControleur(PaquetDeCartes paquetActuel) {
        this.globalControleur.setPaquet(paquetActuel);
    }
    @FXML
    public void questionReussie() {
        // TODO : modifier avec le paquet courant
        notifierObservateur();
    }
    @FXML
    public void questionLoupee() {
        // TODO : modifier avec le paquet courant
        notifierObservateur();
    }
}
