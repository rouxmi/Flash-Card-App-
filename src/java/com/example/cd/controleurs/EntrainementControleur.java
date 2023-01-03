package com.example.cd.controleurs;

import com.example.cd.Observateur;
import com.example.cd.SujetObserve;
import com.example.cd.commande.quitterApplicationCommande;
import com.example.cd.modele.PaquetDeCartes;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleButton;

import java.net.URL;
import java.util.ResourceBundle;

public class EntrainementControleur extends SujetObserve implements Initializable, Observateur {
    private PaquetDeCartes paquet;
    @FXML
    private ToggleButton toggleFlashCard;
    public EntrainementControleur(PaquetDeCartes paquet){
        this.paquet=paquet;
     // paquet.ajouterObservateur(this);
    }
    @Override
    public void reagir() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO : modifier avec la carte courante
        toggleFlashCard.setText("question");
    }

    @FXML
    public void quitterAppli() {
        (new quitterApplicationCommande()).execute();
    }
    @FXML
    public void allerAccueil() throws Exception {
        GlobalControleur.changeSceneVersAccueil();
    }
    @FXML
    public void voirPaquet() throws Exception {
        // TODO : verifier le paquet courant
        GlobalControleur.changeSceneVersGestion();
    }
    @FXML
    public void majFlashCard() {
        // TODO : modifier avec le paquet courant
        if (toggleFlashCard.isSelected()) {
            toggleFlashCard.setText("Question");
        } else {
            toggleFlashCard.setText("Réponse");
        }
    }
}
