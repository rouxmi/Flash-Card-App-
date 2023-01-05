package com.example.cd.controleurs;

import com.example.cd.Observateur;
import com.example.cd.SujetObserve;
import com.example.cd.modele.PaquetDeCartes;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MiniJeuControleur extends SujetObserve implements Initializable, Observateur {

    private final PaquetDeCartes paquet;
    private final GlobalControleur globalControleur;

    public MiniJeuControleur(PaquetDeCartes paquet, GlobalControleur globalControleur){
        this.paquet = paquet;
        this.globalControleur = globalControleur;
        paquet.ajouterObservateur(this);
    }
    @Override
    public void reagir() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
