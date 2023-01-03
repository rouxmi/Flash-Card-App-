package com.example.cd.controleurs;

import com.example.cd.Observateur;
import com.example.cd.modele.PaquetDeCartes;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AccueilControleur implements Initializable, Observateur {
    private ArrayList<PaquetDeCartes> paquets;

    public AccueilControleur(ArrayList<PaquetDeCartes> paquet){
        this.paquets = paquet;
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

    @FXML
    public void ajouterNouveauPaquet() throws Exception {
        paquets.add(new PaquetDeCartes());
        PaquetDeCartes paquetActuel = paquets.get(paquets.size()-1);
        // TODO :
        //        paquetActuel.ajouterObservateur(this);
        GlobalControleur.changeSceneVersGestion();

    }
}
