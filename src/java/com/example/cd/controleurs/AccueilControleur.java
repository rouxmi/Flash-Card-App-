package com.example.cd.controleurs;

import com.example.cd.Observateur;
import com.example.cd.modele.PaquetDeCartes;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AccueilControleur extends GlobalControleur {

    private ArrayList<PaquetDeCartes> paquet;

    public AccueilControleur(ArrayList<PaquetDeCartes> paquet){
        this.paquet = paquet;
        for(int i=0; i< paquet.size();i++){
            paquet.get(i).ajouterObservateur(this);
        }

    }


    @Override
    public void reagir() {
        // TODO Auto-generated method stub
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
