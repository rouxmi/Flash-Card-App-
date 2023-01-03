package com.example.cd.controleurs;

import com.example.cd.Observateur;
import com.example.cd.commande.*;
import com.example.cd.modele.PaquetDeCartes;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreationControleur implements Initializable, Observateur {
    private PaquetDeCartes paquet;
    private int indice;
    public CreationControleur(PaquetDeCartes paquet){
        this.paquet=paquet;
        this.indice=0;
        //paquet.ajouterObservateur(this);
    }
    public CreationControleur(PaquetDeCartes paquet,int indice){
        this.paquet=paquet;
        this.indice=indice;
        paquet.ajouterObservateur(this);
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public void executerCommande(Commande commande) throws IOException {
        commande.execute();
    }
    @Override
    public void reagir() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    public void quitterAppli() throws IOException {
        executerCommande(new quitterApplicationCommande());
    }

    @FXML
    public void allerAccueil() throws Exception {
        GlobalControleur.changeSceneVersAccueil();
    }

    @FXML
    public void voirPaquet() throws Exception {
        GlobalControleur.changeSceneVersGestion();
    }
}
