package com.example.cd.controleurs;

import com.example.cd.Observateur;
import com.example.cd.modele.PaquetDeCartes;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GlobalControleur implements Observateur, Initializable {
    private ArrayList<PaquetDeCartes> paquets;

    @Override
    public void reagir() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
