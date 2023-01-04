package com.example.cd.controleurs;

import com.example.cd.Observateur;
import com.example.cd.Sauvegarde;
import com.example.cd.SujetObserve;
import com.example.cd.commande.quitterApplicationCommande;
import com.example.cd.modele.Carte;
import com.example.cd.modele.PaquetDeCartes;
import com.example.cd.modele.apprentissage.AvancementApprentissage;
import com.example.cd.modele.apprentissage.ClassiqueApprentissage;
import com.example.cd.modele.apprentissage.RandomApprentissage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class GestionControleur extends SujetObserve implements Initializable, Observateur {

    private PaquetDeCartes paquet;

    private GlobalControleur globalControleur;
    private Carte carteActuelle;

    @FXML
    private GridPane table;

    @FXML
    private VBox PieChartBox;
    @FXML
    private Label titre;
    @FXML
    private Label description;
    @FXML
    private Button modifTitre;
    @FXML
    private Button modifDescription;
    @FXML
    private Button exportPaquet;

    public GestionControleur(PaquetDeCartes paquet, GlobalControleur globalControleur){
        this.paquet = paquet;
        this.globalControleur = globalControleur;
        paquet.ajouterObservateur(this);
    }

    private void InitialisationCamenbert() {
        PieChart pieChart = new PieChart();
        List<String> list = Arrays.asList("NonVue","DebutApprentissage","ARevoir","FinApprentissage","AcquiseParfaite");
        List<String> nom = Arrays.asList("Non Vue","Debut Apprentissage","à Revoir","Fin Apprentissage","Acquise Parfaite");
        int[] nombre= {0,0,0,0,0};
        for(int i=0;i<paquet.getCartes().size();i++){
            for (int j=0;j<5;j++){
                if (paquet.getCarte(i).getStatsCarte().getEtatCarte().toString().equals(list.get(j))){
                    nombre[j]++;
                }
            }
        }
        for (int j=0;j<5;j++) {
            PieChart.Data slice = new PieChart.Data(nom.get(j), nombre[j]);
            pieChart.getData().add(slice);
        }

        pieChart.setStartAngle(90);
        pieChart.setClockwise(true);

        PieChartBox.getChildren().add(pieChart);
    }

    @Override
    public void reagir() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        affichageCartes();
        InitialisationCamenbert();
        titre.setText(globalControleur.getPaquet().getTitre());
        description.setText(globalControleur.getPaquet().getDescription());
    }

    @FXML
    public void versCreation() throws Exception{
        majPaquetGlobalControleur(paquet);
        paquet.ajouterCarte(new Carte());
        carteActuelle=null;
        majCarteGlobalControleur(carteActuelle);
        globalControleur.changeSceneVersCreation();
    }
    public void visiterCarte()throws Exception{
        majCarteGlobalControleur(carteActuelle);
        globalControleur.changeSceneVersCreation();
    }
    @FXML
    public void handlemodifTitre() throws Exception{
        dialogBoxNouveauTitre();
        globalControleur.changeSceneVersGestion();
    }
    @FXML
    public void handlemodifDescription() throws Exception{
        dialogBoxNouvelleDescription();
        globalControleur.changeSceneVersGestion();
    }
    public void dialogBoxNouveauTitre() {
        TextInputDialog infoTitre = new TextInputDialog();
        infoTitre.setTitle("Modification Titre");
        infoTitre.setHeaderText("Renseigne ton nouveau titre");
        infoTitre.showAndWait();
        paquet.setTitre(infoTitre.getEditor().getText());
    }
    public void dialogBoxNouvelleDescription(){
        TextInputDialog infoDescription = new TextInputDialog();
        infoDescription.setTitle("Modification Description");
        infoDescription.setHeaderText("Renseigne ta nouvelle description");
        infoDescription.showAndWait();
        paquet.setDescription(infoDescription.getEditor().getText());
    }

    @FXML
    public void versEntrainement() throws Exception {
        majPaquetGlobalControleur(paquet);
        globalControleur.changeSceneVersEntrainement("entrainement");
    }
    @FXML
    public void versRevision() throws Exception {
        majPaquetGlobalControleur(paquet);
        globalControleur.changeSceneVersEntrainement("revision");
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
    @FXML
    public void supprimerPaquet() throws Exception {
        globalControleur.supprimerPaquet(paquet);
        globalControleur.sauvegarder();
        allerAccueil();
    }

    @FXML
    public void exporterPaquet() throws IOException {

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
                int finalI = i;
                button.setOnAction(event -> {
                    try {
                        this.carteActuelle=paquet.getCarte(finalI);
                        visiterCarte();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                });
                button.setPrefSize(100, 100);
                button.setMaxHeight(1.7976931348623157E308);
                button.setMaxWidth(1.7976931348623157E308);
                button.setMnemonicParsing(false);
                table.add(button, i % nbColonnes, i / nbColonnes);
            }
            table.setVgap(10);

        }
    }

    @FXML
    public void random(){
        paquet.setApprentissageStrategie(new RandomApprentissage());
    }

    @FXML
    public void classique(){
        paquet.setApprentissageStrategie(new ClassiqueApprentissage());
    }

    @FXML
    public void avancement(){
        paquet.setApprentissageStrategie(new AvancementApprentissage());
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
