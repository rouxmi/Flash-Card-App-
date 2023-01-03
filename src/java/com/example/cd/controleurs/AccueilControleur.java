package com.example.cd.controleurs;

import com.example.cd.Observateur;
import com.example.cd.SujetObserve;
import com.example.cd.commande.quitterApplicationCommande;
import com.example.cd.modele.PaquetDeCartes;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AccueilControleur extends SujetObserve implements Initializable, Observateur {
    private ArrayList<PaquetDeCartes> paquets;
    @FXML
    private GridPane table;
    @FXML
    private ToggleButton toggleBouton;

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
        creationBoutons();
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
    public void majToggle() {
        // TODO : verifier que ca part pas quand on remet la toolbar
        if ( toggleBouton.isSelected() ) {
            toggleBouton.setText("Gestion");
        } else {
            toggleBouton.setText("Entrainement");
        }
    }
    @FXML
    public void quitterAppli() {
        // TODO : relier  toolbar quand remi aura fini le responsive
        (new quitterApplicationCommande()).execute();
    }

    public void creationBoutons() {
        int nbBoutons = paquets.size()+1;
        int nbLignes = 0;
        int nbColonnes = 3;

        while (nbColonnes * nbLignes < nbBoutons) {
            nbLignes++;
        }
        if (nbLignes > table.getRowConstraints().size()) {
            for (int j = table.getRowConstraints().size(); j < nbLignes; j++) {
                RowConstraints row = new RowConstraints();
                row.setVgrow(Priority.SOMETIMES);
                row.setMinHeight(10);
                row.setPrefHeight(100);
                table.getRowConstraints().add(row);
            }
        }

        for (int i = 0; i < nbBoutons; i++) {
            Button button;
            button = new Button();

            if (i == nbBoutons - 1) {
                button.setText("+");
                button.setOnAction(event -> {
                    try {
                        ajouterNouveauPaquet();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
            else {
                button.setText(paquets.get(i).getTitre());
            }
            button.setPrefSize(100, 100);
            button.setMaxHeight(1.7976931348623157E308);
            button.setMaxWidth(1.7976931348623157E308);
            button.setMnemonicParsing(false);
            table.add(button, i % nbColonnes, i / nbColonnes);
        }
        table.setHgap(10);
        table.setVgap(10);
    }
}
