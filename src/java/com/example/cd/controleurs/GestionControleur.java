package com.example.cd.controleurs;

import com.example.cd.Observateur;
import com.example.cd.SujetObserve;
import com.example.cd.commande.quitterApplicationCommande;
import com.example.cd.modele.Carte;
import com.example.cd.modele.PaquetDeCartes;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class GestionControleur extends SujetObserve implements Initializable, Observateur {
    private PaquetDeCartes paquet;

    public GestionControleur(PaquetDeCartes paquet){
        this.paquet = paquet;
        //paquet.ajouterObservateur(this);
    }

    @Override
    public void reagir() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void versCreation() throws Exception{
        //paquet.ajouterCarte(new Carte());
        GlobalControleur.changeSceneVersCreation();
    }
    @FXML
    public void versEntrainement() throws Exception {
        GlobalControleur.changeSceneVersEntrainement();
    }
    @FXML
    public void quitterAppli() {
        (new quitterApplicationCommande()).execute();
    }
    @FXML
    public void allerAccueil() throws Exception {
        GlobalControleur.changeSceneVersAccueil();
    }
}
