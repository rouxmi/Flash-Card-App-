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
    private int decompte;
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
        this.carteActuelle = paquet.getApprentissageStrategie().getCarte(this.paquet);
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
        decompte = 3;
        // Compteur
        compteurLabel.setText(String.valueOf(decompte));
        toggleFlashCard.setText(carteActuelle.getQuestion());
        Timeline compteur = new Timeline((new KeyFrame(javafx.util.Duration.seconds(1), event -> {
            compteurLabel.setText(String.valueOf(decompte));
            decompte--;
        })));
        compteur.setCycleCount(4);

        if ( typeEntrainement.equals("revision") ) {
            compteur.play();
            compteur.setOnFinished(event -> {
                        if (!toggleFlashCard.isSelected()) {
                            toggleFlashCard.setSelected(true);
                            majFlashCard();
                            compteurLabel.setText("");
                        };}
            );
            compteurLabel.setVisible(true);
        } else if ( typeEntrainement.equals("entrainement") ) {
            compteurLabel.setVisible(false);
        }

        toggleFlashCard.setSelected(false);
        questionLoupeeBouton.setVisible(false);
        questionReussieBouton.setVisible(false);
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
        toggleFlashCard.setText("");
        RotateTransition rotate = new RotateTransition();
        rotate.setNode(toggleFlashCard);
        rotate.setDuration(javafx.util.Duration.seconds(0.5));
        rotate.setAxis(Rotate.Y_AXIS);
        rotate.setByAngle(180);
        rotate.setAutoReverse(true);
        rotate.play();
        rotate.setOnFinished(event -> {
            RotateTransition bonSensRotate = new RotateTransition();
            bonSensRotate.setNode(toggleFlashCard);
            bonSensRotate.setDuration(javafx.util.Duration.millis(1));
            bonSensRotate.setByAngle(180);
            bonSensRotate.play();
            if (toggleFlashCard.isSelected()) {
                toggleFlashCard.setText(carteActuelle.getReponse());
                questionLoupeeBouton.setVisible(true);
                questionReussieBouton.setVisible(true);
                compteurLabel.setText("");
            } else {
                toggleFlashCard.setText(carteActuelle.getQuestion());
                questionLoupeeBouton.setVisible(false);
                questionReussieBouton.setVisible(false);
            }
        });
    }

    public void majPaquetGlobalControleur(PaquetDeCartes paquetActuel) {
        this.globalControleur.setPaquet(paquetActuel);
    }
    public void majCarteGlobalControleur(Carte carteActuelle) {
        this.globalControleur.setCarte(carteActuelle);
    }
    @FXML
    public void reussite() throws Exception {
        majPaquetGlobalControleur(paquet);
        carteActuelle.getStatsCarte().MajStatsCarteReussite();
        this.carteActuelle = paquet.getApprentissageStrategie().getCarte(this.paquet);
        initialize(null, null);
        toggleFlashCard.setSelected(false);
        majFlashCard();
    }
    @FXML
    public void echec() throws Exception {
        majPaquetGlobalControleur(paquet);
        carteActuelle.getStatsCarte().MajStatsCarteEchec();
        this.carteActuelle = paquet.getApprentissageStrategie().getCarte(this.paquet);
        initialize(null, null);
        toggleFlashCard.setSelected(false);
        majFlashCard();
    }
}
