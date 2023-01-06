package com.example.cd.controleurs;

import com.example.cd.Observateur;
import com.example.cd.SujetObserve;
import com.example.cd.commande.*;
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
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;

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
    private int index;

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
    @FXML
    private MenuItem carteVisibility;
    @FXML
    private MenuItem paquetVisibility;
    @FXML
    private MenuItem entrainementVisibility;
    @FXML
    private TextField taReponse;
    @FXML
    private Button valideReponse;
    @FXML
    private Button ecouterSonBouton;

    public EntrainementControleur(PaquetDeCartes paquet, GlobalControleur globalControleur, String typeEntrainement){
        this.paquet=paquet;
        this.globalControleur=globalControleur;
        this.futurCartes = new LinkedList<Carte>();
        this.carteActuelle = paquet.getApprentissageStrategie().getCarte(this.paquet, futurCartes);
        futurCartes.add(this.carteActuelle);
        for(index=1; index< this.paquet.taillePaquet()/2 ;index++){
            futurCartes.add(paquet.getApprentissageStrategie().getCarte(this.paquet, futurCartes));
        }
        futurCartes.poll();
        this.typeEntrainement=typeEntrainement;
     // paquet.ajouterObservateur(this);
    }

    public void notifierObservateur() {
        observateur.reagir();
    }
    @Override
    public void reagir() {
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        decompte = globalControleur.getPaquet().getDecompte();
        // Compteur
        compteurLabel.setText(String.valueOf(decompte));
        toggleFlashCard.setText(carteActuelle.getQuestion());
        taReponse.setText("");
        if(!this.carteActuelle.getImageQuestion().equals("")){
            Image image = new Image(carteActuelle.getImageQuestion());
            ImageView icon = new ImageView(image);
            icon.setFitHeight(100);
            icon.setFitWidth(90);
            toggleFlashCard.setGraphic(icon);
        }
        else {
            toggleFlashCard.setGraphic(null);
        }
        Timeline compteur = new Timeline((new KeyFrame(javafx.util.Duration.seconds(1), event -> {
            compteurLabel.setText(String.valueOf(decompte));
            compteurLabel.getStyleClass().add("texte");
            decompte--;
        })));
        compteur.setCycleCount(decompte+1);
        ecouterSonBouton.setVisible(false);
        if ( !carteActuelle.getAudioQuestion().equals("") ) {
            ecouterSonBouton.setVisible(true);
        }

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
            taReponse.setVisible(false);
            valideReponse.setVisible(false);
        } else if ( typeEntrainement.equals("entrainement") ) {
            compteurLabel.setVisible(false);
            taReponse.setVisible(false);
            valideReponse.setVisible(false);
        }
        else if(typeEntrainement.equals("ecriture")){
            compteurLabel.setVisible(false);
            valideReponse.setOnAction(event -> {
                if (!toggleFlashCard.isSelected()) {
                    toggleFlashCard.setSelected(true);
                    majFlashCard();
                    compteurLabel.setText("");
                };
            });
        }

        majStats();

        toggleFlashCard.setSelected(false);
        questionLoupeeBouton.setVisible(false);
        questionReussieBouton.setVisible(false);
    }

    public void majStats(){
        majStatsPaquet();
        majStatsCarte();
        majStatsEntrainement();
    }
    public void majStatsCarte(){

        List<String> nom = Arrays.asList("Non Vue","Debut Apprentissage","à Revoir","Fin Apprentissage","Acquise Parfaite");
        Label label = new Label("Statistiques de la carte:");
        label.getStyleClass().add("texte");
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
    public void majStatsPaquet(){
        statsboxPaquet.getChildren().clear();
        Label label = new Label("Statistiques du Paquet:");
        label.getStyleClass().add("texte");
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
    public void majStatsEntrainement(){
        statsboxEntrainement.getChildren().clear();
        Label label = new Label("Statistiques de l'Entrainement:");
        label.getStyleClass().add("texte");
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
    public boolean compareReponses(String reponse){
        if(reponse.equals(carteActuelle.getReponse())){
            return true;
        }
        return false;
    }

    // FXML boutons fonctions
    @FXML
    public void quitterAppli() {
        (new QuitterApplicationCommande()).execute();
    }
    @FXML
    public void allerAccueil() throws Exception {
        new AllerAccueilCommande(globalControleur, paquet).execute();
    }
    @FXML
    public void voirPaquet() throws Exception {
        new VoirPaquetCommande(globalControleur, paquet).execute();
    }
    @FXML
    public void ecouterSon() throws Exception {
        if ( toggleFlashCard.isSelected() ) {
            new JouerSonCommande(globalControleur, carteActuelle, "reponse").execute();
        } else if ( !toggleFlashCard.isSelected() ) {
            new JouerSonCommande(globalControleur, carteActuelle, "question").execute();
        }
    }
    // too complicated to put in command pattern
    @FXML
    public void majFlashCard() {
        toggleFlashCard.setText("");
        toggleFlashCard.setGraphic(null);
        toggleFlashCard.setOnAction(null);
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
            toggleFlashCard.setOnAction(event1 -> majFlashCard());
            RotateTransition bonSensRotate = new RotateTransition();
            bonSensRotate.setNode(toggleFlashCard);
            bonSensRotate.setDuration(javafx.util.Duration.millis(1));
            bonSensRotate.setByAngle(180);
            bonSensRotate.play();
            bonSensRotate.setOnFinished(event2 -> {
                if (toggleFlashCard.isSelected()) {
                    toggleFlashCard.setText(carteActuelle.getReponse());
                    if (taReponse.isVisible()) {
                        if (compareReponses(taReponse.getText())) {
                            Alert gagne = new Alert(Alert.AlertType.INFORMATION);
                            gagne.setTitle("Gagné ou perdu ?");
                            gagne.setHeaderText(null);
                            gagne.setContentText("Mot Correct");
                            gagne.show();
                            try {
                                reussite();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            Alert perdu = new Alert(Alert.AlertType.INFORMATION);
                            perdu.setTitle("Gagné ou perdu ?");
                            perdu.setHeaderText(null);
                            perdu.setContentText("Perdu ! Le mot correct était : " + this.carteActuelle.getReponse());
                            perdu.show();
                            try {
                                echec();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                    } else {
                        toggleFlashCard.setGraphic(null);
                        questionLoupeeBouton.setVisible(true);
                        questionReussieBouton.setVisible(true);
                        compteurLabel.setText("");
                    }



                } else {
                    toggleFlashCard.setText(carteActuelle.getQuestion());
                    if (!this.carteActuelle.getImageQuestion().equals("")) {
                        Image image = new Image(carteActuelle.getImageQuestion());
                        ImageView icon = new ImageView(image);
                        icon.setFitHeight(100);
                        icon.setFitWidth(90);
                        toggleFlashCard.setGraphic(icon);
                    } else {
                        toggleFlashCard.setGraphic(null);
                    }
                    if (!carteActuelle.getAudioQuestion().equals("")) {
                        ecouterSonBouton.setVisible(true);
                    } else {
                        ecouterSonBouton.setVisible(false);
                    }
                    questionLoupeeBouton.setVisible(false);
                    questionReussieBouton.setVisible(false);
                }
            });
        });
    }
    @FXML
    public void reussite() throws Exception {
        new MajPaquetGlobalCommande(globalControleur, paquet).execute();
        nbReussite++;
        carteActuelle.getStatsCarte().MajStatsCarteReussite();
        futurCartes.add(paquet.getApprentissageStrategie().getCarte(this.paquet, futurCartes));
        this.carteActuelle = futurCartes.poll();
        index++;
        initialize(null, null);
        toggleFlashCard.setSelected(false);
        majFlashCard();
    }
    @FXML
    public void echec() throws Exception {
        new MajPaquetGlobalCommande(globalControleur, paquet).execute();
        nbEchec++;
        carteActuelle.getStatsCarte().MajStatsCarteEchec();
        futurCartes.add(paquet.getApprentissageStrategie().getCarte(this.paquet, futurCartes));
        this.carteActuelle = futurCartes.poll();
        index++;
        initialize(null, null);
        toggleFlashCard.setSelected(false);
        majFlashCard();
    }
    @FXML
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
    @FXML
    public void showPaquet(){
        if (statsboxPaquet.isVisible()){
            paquetVisibility.setText("    Stats paquet");
        }
        else{
            paquetVisibility.setText("✔ Stats paquet");
        }
        statsboxPaquet.setVisible(!statsboxPaquet.isVisible());
    }
    @FXML
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
