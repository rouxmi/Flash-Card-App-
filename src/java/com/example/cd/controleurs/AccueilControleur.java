package com.example.cd.controleurs;

import com.example.cd.Observateur;
import com.example.cd.modele.PaquetDeCartes;
import com.example.cd.commande.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleButton;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AccueilControleur implements Initializable, Observateur {
    private ArrayList<PaquetDeCartes> paquets;
    @FXML
    private ToggleButton toggleButton;

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

    public void executerCommande(Commande commande) throws Exception {
        commande.execute();
    }

    @FXML
    public void ajouterNouveauPaquet() throws Exception {
        paquets.add(new PaquetDeCartes());
        PaquetDeCartes paquetActuel = paquets.get(paquets.size()-1);
        // TODO :
        //        paquetActuel.ajouterObservateur(this);
        GlobalControleur.changeSceneVersGestion();

    }

    @FXML
    public void utiliserPaquet() throws Exception {
        if ( toggleButton.isSelected() ) {
            toggleButton.setText("Gestion");
            GlobalControleur.changeSceneVersGestion();
        } else if ( !toggleButton.isSelected() ) {
            toggleButton.setText("Entrainement");
            GlobalControleur.changeSceneVersEntrainement();
        }
    }

    @FXML
    public void quitterAppli() throws Exception {
        executerCommande(new quitterApplicationCommand());
    }
}
