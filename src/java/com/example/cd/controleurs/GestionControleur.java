package com.example.cd.controleurs;

import com.example.cd.Observateur;
import com.example.cd.SujetObserve;
import com.example.cd.commande.quitterApplicationCommande;
import com.example.cd.modele.Carte;
import com.example.cd.modele.PaquetDeCartes;
import com.example.cd.modele.apprentissage.FreeApprentissage;
import com.example.cd.modele.apprentissage.ClassiqueApprentissage;
import com.example.cd.modele.apprentissage.MasterStrategie;
import com.example.cd.modele.apprentissage.RandomApprentissage;
import com.example.cd.statistiques.EtatCarte;
import javafx.beans.binding.DoubleBinding;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
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
        majCarteGlobalControleur(paquet.getCarte(paquet.getCartes().size()-1));
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
    public void miniJeu() throws Exception {
        majPaquetGlobalControleur(paquet);
        globalControleur.changeSceneVersMiniJeu();
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
        globalControleur.sauvegarder1paquet();
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
                if(!paquet.getCarte(i).getMediaQuestion().equals("")){
                    final Image image = new Image("utiles/image1.png");

                    final ImageView icon = new ImageView(image);
                    icon.setFitHeight(50);
                    icon.setFitWidth(50);
                    button.setGraphic(icon);
                    button.setText("");
                    button.setPrefSize(50, 50);
                    button.setMinSize(50,50);
                    //button.setBackground(new Background(new BackgroundImage(new javafx.scene.image.Image("utiles/appareil.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(button.getPrefWidth(), button.getPrefHeight(), false, false, false, false))));


                }
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
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Choisissez le pourcentage d'apparition des cartes");
        VBox vBox = new VBox();
        for(int i=0; i<5; i++){
            HBox hBox = new HBox();
            Slider slider = new Slider(1, 100, 50);
            slider.setId("slider "+ EtatCarte.values()[i].toString());
            slider.setShowTickLabels(true);
            slider.setShowTickMarks(true);
            Label label = new Label(EtatCarte.values()[i].toString());
            hBox.getChildren().add(label);
            hBox.getChildren().add(slider);
            vBox.getChildren().add(hBox);
        }
        DoubleBinding sumBinding = ((Slider)((HBox)vBox.getChildren().get(0)).getChildren().get(1)).valueProperty().add(((Slider)((HBox)vBox.getChildren().get(1)).getChildren().get(1)).valueProperty())
                .add(((Slider)((HBox)vBox.getChildren().get(2)).getChildren().get(1)).valueProperty()).add(((Slider)((HBox)vBox.getChildren().get(3)).getChildren().get(1)).valueProperty())
                .add(((Slider)((HBox)vBox.getChildren().get(4)).getChildren().get(1)).valueProperty());

        sumBinding.addListener((obs, oldValue, newValue) -> {
            double sum = newValue.doubleValue();
            if (sum > 100) {
                double excess = sum - 100;
                ((Slider)((HBox)vBox.getChildren().get(0)).getChildren().get(1)).setValue(((Slider)((HBox)vBox.getChildren().get(0)).getChildren().get(1)).getValue() - excess / 5);
                ((Slider)((HBox)vBox.getChildren().get(1)).getChildren().get(1)).setValue(((Slider)((HBox)vBox.getChildren().get(1)).getChildren().get(1)).getValue() - excess / 5);
                ((Slider)((HBox)vBox.getChildren().get(2)).getChildren().get(1)).setValue(((Slider)((HBox)vBox.getChildren().get(2)).getChildren().get(1)).getValue() - excess / 5);
                ((Slider)((HBox)vBox.getChildren().get(3)).getChildren().get(1)).setValue(((Slider)((HBox)vBox.getChildren().get(3)).getChildren().get(1)).getValue() - excess / 5);
                ((Slider)((HBox)vBox.getChildren().get(4)).getChildren().get(1)).setValue(((Slider)((HBox)vBox.getChildren().get(4)).getChildren().get(1)).getValue() - excess / 5);
            }
        });

        dialog.getDialogPane().setContent(vBox);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.showAndWait();

        double nonVue = ((Slider)((HBox)vBox.getChildren().get(0)).getChildren().get(1)).getValue();
        double debutApprentissage = ((Slider)((HBox)vBox.getChildren().get(1)).getChildren().get(1)).getValue();
        double aRevoir = ((Slider)((HBox)vBox.getChildren().get(2)).getChildren().get(1)).getValue();
        double finApprentissage = ((Slider)((HBox)vBox.getChildren().get(3)).getChildren().get(1)).getValue();
        double acquiseParfaite = ((Slider)((HBox)vBox.getChildren().get(4)).getChildren().get(1)).getValue();
        paquet.setApprentissageStrategie(new FreeApprentissage(nonVue, debutApprentissage, aRevoir, finApprentissage, acquiseParfaite));
    }

    @FXML
    public void master(){
        paquet.setApprentissageStrategie(new MasterStrategie());
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
