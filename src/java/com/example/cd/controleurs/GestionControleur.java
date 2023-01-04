package com.example.cd.controleurs;

import com.example.cd.Observateur;
import com.example.cd.SujetObserve;
import com.example.cd.commande.quitterApplicationCommande;
import com.example.cd.modele.Carte;
import com.example.cd.modele.PaquetDeCartes;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class GestionControleur extends SujetObserve implements Initializable, Observateur {

    private PaquetDeCartes paquet;

    private GlobalControleur globalControleur;

    @FXML
    private GridPane table;

    @FXML
    private VBox PieChartBox;

    public GestionControleur(PaquetDeCartes paquet, GlobalControleur globalControleur){
        this.paquet = paquet;
        this.globalControleur = globalControleur;
        paquet.ajouterObservateur(this);
    }

    private void InitialisationCamenbert() {
        PieChart pieChart = new PieChart();

        PieChart.Data slice1 = new PieChart.Data("Desktop", 213);
        PieChart.Data slice2 = new PieChart.Data("Phone"  , 67);
        PieChart.Data slice3 = new PieChart.Data("Tablet" , 36);

        pieChart.getData().add(slice1);
        pieChart.getData().add(slice2);
        pieChart.getData().add(slice3);

        PieChartBox.getChildren().add(pieChart);
    }

    @Override
    public void reagir() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        affichageCartes();
        InitialisationCamenbert();
    }

    @FXML
    public void versCreation() throws Exception{
        //paquet.ajouterCarte(new Carte());
        majPaquetGlobalControleur(paquet);
        Carte nouvelleCarte = new Carte();
        paquet.ajouterCarte(nouvelleCarte);
        globalControleur.changeSceneVersCreation();
    }


    @FXML
    public void versEntrainement() throws Exception {
        majPaquetGlobalControleur(paquet);
        globalControleur.changeSceneVersEntrainement();
    }
    @FXML
    public void quitterAppli() {
                new quitterApplicationCommande().execute();
    }
    @FXML
    public void allerAccueil() throws Exception {
        majPaquetGlobalControleur(paquet);
        globalControleur.changeSceneVersAccueil();
    }
    public void affichageCartes() {
        if (this.paquet !=null) {
            int nbBoutons = paquet.taillePaquet();
            int nbColonnes = 1;
            int nbLignes = 0;
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

                button.setText(paquet.getCarte(i).getQuestion());

                button.setPrefSize(100, 100);
                button.setMaxHeight(1.7976931348623157E308);
                button.setMaxWidth(1.7976931348623157E308);
                button.setMnemonicParsing(false);
                table.add(button, i % nbColonnes, i / nbColonnes);
            }
            table.setVgap(10);

        }
    }

    public void majPaquetGlobalControleur(PaquetDeCartes paquetActuel) {
        this.globalControleur.setPaquet(paquetActuel);
    }

}
