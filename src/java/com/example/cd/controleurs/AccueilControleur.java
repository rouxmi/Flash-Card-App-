package com.example.cd.controleurs;

import com.example.cd.Observateur;
import com.example.cd.modele.PaquetDeCartes;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class AccueilControleur implements Observateur, Initializable {

    private PaquetDeCartes paquet;

    public AccueilControleur(PaquetDeCartes paquet){
        this.paquet = paquet;
        paquet.ajouterObservateur(this);
    }


    @Override
    public void reagir() {
        // TODO Auto-generated method stub
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
