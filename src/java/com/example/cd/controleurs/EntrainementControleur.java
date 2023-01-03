package com.example.cd.controleurs;

import com.example.cd.Observateur;
import com.example.cd.modele.PaquetDeCartes;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class EntrainementControleur implements Initializable, Observateur {
    private PaquetDeCartes paquet;
    public EntrainementControleur(PaquetDeCartes paquet){
        this.paquet=paquet;
     //   paquet.ajouterObservateur(this);
    }
    @Override
    public void reagir() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
