package com.example.cd.controleurs;

import com.example.cd.Observateur;
import com.example.cd.SujetObserve;
import com.example.cd.commande.*;
import com.example.cd.modele.PaquetDeCartes;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class CreationControleur extends SujetObserve implements Initializable, Observateur {
    private PaquetDeCartes paquet;

    private GlobalControleur globalControleur;
    private int indice;

    @FXML
    private TextArea question;
    @FXML
    private TextArea reponse;

    public CreationControleur(PaquetDeCartes paquet,GlobalControleur globalControleur){
        this.paquet=paquet;
        this.indice=0;
        this.globalControleur=globalControleur;
        //paquet.ajouterObservateur(this);
    }
    public CreationControleur(PaquetDeCartes paquet,int indice,GlobalControleur globalControleur){
        this.paquet=paquet;
        this.indice=indice;
        this.globalControleur=globalControleur;
        paquet.ajouterObservateur(this);
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    @Override
    public void reagir() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
    public void validerCarte() throws Exception {
        //this.paquet.getCarte(this.indice).setQuestion(question.getText());
        //this.paquet.getCarte(this.indice).setReponse(reponse.getText());
        globalControleur.changeSceneVersCreation();
    }

}
