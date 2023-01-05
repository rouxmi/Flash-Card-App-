package com.example.cd.controleurs;

import com.example.cd.Observateur;
import com.example.cd.SujetObserve;
import com.example.cd.commande.quitterApplicationCommande;
import com.example.cd.modele.Carte;
import com.example.cd.modele.PaquetDeCartes;
import com.example.cd.statistiques.EtatCarte;
import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;

public class EntrainementControleur extends SujetObserve implements Initializable, Observateur {
    private PaquetDeCartes paquet;
    private GlobalControleur globalControleur;
    private int decompte;
    public Observateur observateur;

    private Carte carteActuelle;
    private Queue<Carte> futurCartes;
    private String typeEntrainement;

    private int nbReussite;
    private int nbEchec;
    @FXML
    private ToggleButton toggleFlashCard;
    @FXML
    private Label compteurLabel;
    @FXML
    private Button questionReussieBouton;
    @FXML
    private Button questionLoupeeBouton;

    @FXML
    private PieChart graphPaquet;

    @FXML
    private PieChart graphCarte;

    @FXML
    private VBox statsboxEntrainement;
    @FXML
    private VBox statsboxCarte;
    @FXML
    private VBox statsboxPaquet;

    private int index;

    @FXML
    private MenuItem carteVisibility;

    @FXML
    private MenuItem paquetVisibility;

    @FXML
    private MenuItem entrainementVisibility;

    public EntrainementControleur(PaquetDeCartes paquet, GlobalControleur globalControleur, String typeEntrainement){
        this.paquet=paquet;
        this.globalControleur=globalControleur;
        this.carteActuelle = paquet.getApprentissageStrategie().getCarte(this.paquet, 0);
        this.futurCartes = new LinkedList<Carte>();
        for(index=1; index< paquet.getCartes().size();index++){
            futurCartes.add(paquet.getApprentissageStrategie().getCarte(this.paquet, index));
        }
        this.typeEntrainement=typeEntrainement;
     // paquet.ajouterObservateur(this);
    }

    public void setTypeEntrainement(String entrainement){
        this.typeEntrainement=entrainement;
    }
    public void setObservateur(Observateur observateur) {
        this.observateur = observateur;
    }

    public void notifierObservateur() {
        observateur.reagir();
    }
    @Override
    public void reagir() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        decompte = 3;
        // Compteur
        compteurLabel.setText(String.valueOf(decompte));
        toggleFlashCard.setText(carteActuelle.getQuestion());
        Timeline compteur = new Timeline((new KeyFrame(javafx.util.Duration.seconds(1), event -> {
            compteurLabel.setText(String.valueOf(decompte));
            decompte--;
        })));
        compteur.setCycleCount(4);

        if ( typeEntrainement.equals("revision") ) {
            compteur.play();
            compteur.setOnFinished(event -> {
                        if (!toggleFlashCard.isSelected()) {
                            toggleFlashCard.setSelected(true);
                            majFlashCard();
                            compteurLabel.setText("");
                        };}
            );
            compteurLabel.setVisible(true);
        } else if ( typeEntrainement.equals("entrainement") ) {
            compteurLabel.setVisible(false);
        }

        MajStats();

        toggleFlashCard.setSelected(false);
        questionLoupeeBouton.setVisible(false);
        questionReussieBouton.setVisible(false);
    }

    @FXML
    public void quitterAppli() {
        (new quitterApplicationCommande()).execute();
    }
    @FXML
    public void allerAccueil() throws Exception {
        majPaquetGlobalControleur(paquet);
        globalControleur.changeSceneVersAccueil();
    }
    @FXML
    public void voirPaquet() throws Exception {
        // TODO : verifier le paquet courant
        majPaquetGlobalControleur(paquet);
        globalControleur.changeSceneVersGestion();
    }
    @FXML
    public void majFlashCard() {
        toggleFlashCard.setText("");
        RotateTransition rotate = new RotateTransition();
        rotate.setNode(toggleFlashCard);
        rotate.setDuration(javafx.util.Duration.seconds(0.5));
        rotate.setAxis(Rotate.Y_AXIS);
        rotate.setByAngle(180);
        rotate.setAutoReverse(true);
        rotate.play();

        AudioClip player = new AudioClip(getClass().getResource("/utiles/flip.wav").toExternalForm());
        player.play();

        rotate.setOnFinished(event -> {
            RotateTransition bonSensRotate = new RotateTransition();
            bonSensRotate.setNode(toggleFlashCard);
            bonSensRotate.setDuration(javafx.util.Duration.millis(1));
            bonSensRotate.setByAngle(180);
            bonSensRotate.play();
            if (toggleFlashCard.isSelected()) {
                toggleFlashCard.setText(carteActuelle.getReponse());
                questionLoupeeBouton.setVisible(true);
                questionReussieBouton.setVisible(true);
                compteurLabel.setText("");
            } else {
                toggleFlashCard.setText(carteActuelle.getQuestion());
                questionLoupeeBouton.setVisible(false);
                questionReussieBouton.setVisible(false);
            }
        });
    }

    public void MajStats(){
        MajStatsPaquet();
        MajStatsCarte();
        MajStatsEntrainement();
    }

    public void MajStatsCarte(){

        List<String> nom = Arrays.asList("Non Vue","Debut Apprentissage","à Revoir","Fin Apprentissage","Acquise Parfaite");
        Label label = new Label("Stats de la carte:");
        label.setFont(new Font("Arial", 20));


        PieChart.Data slice1 = new PieChart.Data("Réussite", getPourcentageReussiteCarte());
        PieChart.Data slice2 = new PieChart.Data("Echec", getPourcentageEchecCarte());

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(slice1, slice2);
        graphCarte.setData(pieChartData);
        for (PieChart.Data data : graphCarte.getData()) {
            data.getNode().setStyle("-fx-pie-color: " + getColor(data.getName()) + ";");
        }
        statsboxCarte.getChildren().clear();
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        Label Reussite = new Label("Pourcentage de réussite: "+numberFormat.format(getPourcentageReussiteCarte())+"%");
        Label echec = new Label("Pourcentage d'échec: "+numberFormat.format(getPourcentageEchecCarte())+"%");
        Label nbvue = new Label("Nombre d'essai: "+carteActuelle.getStatsCarte().getNbEssaie());
        Label etat = new Label("Etat de la carte: "+nom.get(EtatCarte.valueOf(carteActuelle.getStatsCarte().getEtatCarte().toString()).ordinal()));
        Reussite.setPadding(new Insets(0, 0, 0, 10));
        echec.setPadding(new Insets(0, 0, 0, 10));
        nbvue.setPadding(new Insets(8, 0, 0, 10));
        etat.setPadding(new Insets(0, 0, 0, 10));

        nbvue.setWrapText(true);
        echec.setWrapText(true);
        Reussite.setWrapText(true);
        statsboxCarte.getChildren().addAll(label,nbvue,echec,Reussite,etat);
        statsboxCarte.setSpacing(2);
        statsboxCarte.setPadding(new Insets(10,10,10,10));





    }

    public void MajStatsPaquet(){
        statsboxPaquet.getChildren().clear();
        Label label = new Label("Stats du Paquet:");
        label.setFont(new Font("Arial", 20));
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        Label Reussite = new Label("Pourcentage de réussite: "+numberFormat.format(getPourcentageReussitePaquet())+"%");
        Label echec = new Label("Pourcentage d'échec: "+numberFormat.format(getPourcentageEchecPaquet())+"%");
        Label nbvue = new Label("Nombre d'essai: "+getNbEssaiPaquet());
        Reussite.setPadding(new Insets(0, 0, 0, 10));
        echec.setPadding(new Insets(0, 0, 0, 10));
        nbvue.setPadding(new Insets(8, 0, 0, 10));
        nbvue.setWrapText(true);
        echec.setWrapText(true);
        Reussite.setWrapText(true);
        statsboxPaquet.getChildren().addAll(label,nbvue,echec,Reussite);
        statsboxPaquet.setSpacing(2);
        statsboxPaquet.setPadding(new Insets(10,10,10,10));

    }

    public void MajStatsEntrainement(){
        statsboxEntrainement.getChildren().clear();
        Label label = new Label("Stats de l'Entrainement:");
        label.setFont(new Font("Arial", 20));
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        Label Reussite = new Label("Pourcentage de réussite: "+numberFormat.format(getPourcentageReussiteEntrainement())+"%");
        Label echec = new Label("Pourcentage d'échec: "+numberFormat.format(getPourcentageEchecEntrainement())+"%");
        Label nbvue = new Label("Nombre d'essai: "+(nbReussite+nbEchec));
        Reussite.setPadding(new Insets(0, 0, 0, 10));
        echec.setPadding(new Insets(0, 0, 0, 10));
        nbvue.setPadding(new Insets(8, 0, 0, 10));
        nbvue.setWrapText(true);
        echec.setWrapText(true);
        Reussite.setWrapText(true);
        statsboxEntrainement.getChildren().addAll(label,nbvue,echec,Reussite);
        statsboxEntrainement.setSpacing(2);
        statsboxEntrainement.setPadding(new Insets(10,10,10,10));

        PieChart.Data slice1 = new PieChart.Data("Réussite", getPourcentageReussiteEntrainement());
        PieChart.Data slice2 = new PieChart.Data("Echec", getPourcentageEchecEntrainement());
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(slice1, slice2);
        graphPaquet.setData(pieChartData);
        for (PieChart.Data data : graphPaquet.getData()) {
            data.getNode().setStyle("-fx-pie-color: " + getColor(data.getName()) + ";");
        }


    }

    public void majPaquetGlobalControleur(PaquetDeCartes paquetActuel) throws IOException {
        globalControleur.sauvegarder();
        this.globalControleur.setPaquet(paquetActuel);
    }
    public void majCarteGlobalControleur(Carte carteActuelle) throws IOException {
        globalControleur.sauvegarder();
        this.globalControleur.setCarte(carteActuelle);
    }
    @FXML
    public void reussite() throws Exception {
        majPaquetGlobalControleur(paquet);
        nbReussite++;
        carteActuelle.getStatsCarte().MajStatsCarteReussite();
        this.carteActuelle = futurCartes.poll();
        futurCartes.add(paquet.getApprentissageStrategie().getCarte(this.paquet, index));
        index++;
        initialize(null, null);
        toggleFlashCard.setSelected(false);
        majFlashCard();
    }
    @FXML
    public void echec() throws Exception {
        majPaquetGlobalControleur(paquet);
        nbEchec++;
        carteActuelle.getStatsCarte().MajStatsCarteEchec();
        this.carteActuelle = futurCartes.poll();
        futurCartes.add(paquet.getApprentissageStrategie().getCarte(this.paquet, index));
        index++;
        initialize(null, null);
        toggleFlashCard.setSelected(false);
        majFlashCard();
    }

    public double getPourcentageReussitePaquet(){
        double nombreReussite = 0;
        double nombreEchec = 0;
        for (Carte carte : paquet.getCartes()) {
            nombreReussite += carte.getStatsCarte().getNbReussite();
            nombreEchec += carte.getStatsCarte().getNbEchec();
        }
        return (nombreReussite/(nombreReussite+nombreEchec))*100;
    }

    public double getPourcentageEchecPaquet(){
        double nombreReussite = 0;
        double nombreEchec = 0;
        for (Carte carte : paquet.getCartes()) {
            nombreReussite += carte.getStatsCarte().getNbReussite();
            nombreEchec += carte.getStatsCarte().getNbEchec();
        }
        return (nombreEchec/(nombreReussite+nombreEchec))*100;
    }

    public int getNbEssaiPaquet(){
        int nbEssai = 0;
        for (Carte carte : paquet.getCartes()) {
            nbEssai += carte.getStatsCarte().getNbEssaie();
        }
        return nbEssai;
    }

    public double getPourcentageReussiteEntrainement(){
        if (nbReussite+nbEchec == 0){
            return 0;
        }
        return ( ((double)nbReussite/ (double)(nbReussite+nbEchec))*100);
    }

    public double getPourcentageEchecEntrainement(){
        if (nbReussite+nbEchec == 0){
            return 0;
        }
        return ( ((double)nbEchec/ (double)(nbReussite+nbEchec))*100);
    }

    public double getPourcentageReussiteCarte(){
        if (carteActuelle.getStatsCarte().getNbEssaie() == 0){
            return 0;
        }
        return ((double)carteActuelle.getStatsCarte().getNbReussite()/(double)(carteActuelle.getStatsCarte().getNbReussite()+carteActuelle.getStatsCarte().getNbEchec()))*100;
    }

    public double getPourcentageEchecCarte(){
        if (carteActuelle.getStatsCarte().getNbEssaie() == 0){
            return 0;
        }
        return ( ((double)carteActuelle.getStatsCarte().getNbEchec()/(double)(carteActuelle.getStatsCarte().getNbReussite()+carteActuelle.getStatsCarte().getNbEchec()))*100);
    }

    public String getColor(String name){
        if (name.equals("Réussite")){
            return "green";
        }
        else{
            return "red";
        }
    }

    public void showcarte(){
        if (statsboxCarte.isVisible()){
            carteVisibility.setText("    Stats carte");
        }
        else{
            carteVisibility.setText("✔ Stats carte");
        }
        statsboxCarte.setVisible(!statsboxCarte.isVisible());
        graphCarte.setVisible(!graphCarte.isVisible());
    }
    public void showPaquet(){
        if (statsboxPaquet.isVisible()){
            paquetVisibility.setText("    Stats paquet");
        }
        else{
            paquetVisibility.setText("✔ Stats paquet");
        }
        statsboxPaquet.setVisible(!statsboxPaquet.isVisible());
    }
    public void showEntrainement(){
        if (statsboxEntrainement.isVisible()){
            entrainementVisibility.setText("    Stats entrainement");
        }
        else{
            entrainementVisibility.setText("✔ Stats entrainement");
        }
        statsboxEntrainement.setVisible(!statsboxEntrainement.isVisible());
        graphPaquet.setVisible(!graphPaquet.isVisible());
    }
}
