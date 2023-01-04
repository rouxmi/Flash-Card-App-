package com.example.cd.controleurs;

import com.example.cd.Observateur;
import com.example.cd.SujetObserve;
import com.example.cd.commande.quitterApplicationCommande;
import com.example.cd.modele.Carte;
import com.example.cd.modele.PaquetDeCartes;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AccueilControleur extends SujetObserve implements Initializable, Observateur {
    private ArrayList<PaquetDeCartes> paquets;

    private GlobalControleur globalControleur;

    private PaquetDeCartes paquetActuel;
    @FXML
    private GridPane table;
    @FXML
    private ToggleButton toggleBouton;

    public AccueilControleur(ArrayList<PaquetDeCartes> paquets, GlobalControleur globalControleur){
        this.paquets = paquets;
        this.globalControleur = globalControleur;
        for(int i=0; i< paquets.size();i++){
            paquets.get(i).ajouterObservateur(this);
        }
    }

    @Override
    public void reagir() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        creationBoutons();
    }

    @FXML
    public void ajouterNouveauPaquet() throws Exception {
        paquets.add(new PaquetDeCartes());
        paquetActuel = paquets.get(paquets.size()-1);
        majPaquetGlobalControleur(paquetActuel);
        dialogBoxNouveauPaquet();
        globalControleur.changeSceneVersGestion();
    }

    public void dialogBoxNouveauPaquet() {
        TextInputDialog infoTitre = new TextInputDialog();
        infoTitre.setTitle("Création nouveau paquet");
        infoTitre.setHeaderText("Renseigne le titre");
        infoTitre.showAndWait();
        paquetActuel.setTitre(infoTitre.getEditor().getText());
    }
    @FXML
    public void majToggle() {
        // TODO : verifier que ca part pas quand on remet la toolbar
        if ( toggleBouton.isSelected() ) {
            toggleBouton.setText("Entrainement");
        } else {
            toggleBouton.setText("Gestion");
        }
    }
    @FXML
    public void quitterAppli() {
        // TODO : relier  toolbar quand remi aura fini le responsive
        (new quitterApplicationCommande()).execute();
    }

    @FXML
    public void visiterPaquet() throws Exception{
        majPaquetGlobalControleur(paquetActuel);
        if ( toggleBouton.isSelected() ) {
            globalControleur.changeSceneVersEntrainement("entrainement");
        } else {
            globalControleur.changeSceneVersGestion();
        }
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
                int finalI = i;
                button.setOnAction(event -> {
                    try {
                        this.paquetActuel=paquets.get(finalI);
                        visiterPaquet();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                });
            }
            button.setPrefSize(100, 100);
            button.setMinSize(100,100);
            button.setMaxHeight(1.7976931348623157E308);
            button.setMaxWidth(1.7976931348623157E308);
            button.setMnemonicParsing(false);
            table.add(button, i % nbColonnes, i / nbColonnes);
        }
        table.setHgap(10);
        table.setVgap(10);
    }

    public PaquetDeCartes getPaquetActuel() {
        return paquetActuel;
    }

    public void majPaquetGlobalControleur(PaquetDeCartes paquetActuel) throws IOException {
        globalControleur.sauvegarder();
        this.globalControleur.setPaquet(paquetActuel);
    }
    public void majCarteGlobalControleur(Carte carteActuelle) throws IOException {
        globalControleur.sauvegarder();
        this.globalControleur.setCarte(carteActuelle);
    }
}
