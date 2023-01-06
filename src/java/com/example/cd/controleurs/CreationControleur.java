package com.example.cd.controleurs;

import com.example.cd.Observateur;
import com.example.cd.SujetObserve;
import com.example.cd.commande.*;
import com.example.cd.modele.Carte;
import com.example.cd.modele.PaquetDeCartes;
import com.example.cd.statistiques.StatsCarte;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class CreationControleur extends SujetObserve implements Initializable, Observateur {
    private PaquetDeCartes paquet;
    private Carte carteActuelle;
    private GlobalControleur globalControleur;
    private int indice;

    @FXML
    private TextArea question;
    @FXML
    private TextArea reponse;
    @FXML
    private Button prec;
    @FXML
    private Button suiv;
    @FXML
    private ImageView imageQuestion;
    @FXML
    private ImageView imageReponse;
    @FXML
    private Label numCarte;
    @FXML
    private Label nomPaquet;
    @FXML
    private Button imageGauche;
    @FXML
    private Button imageDroit;
    @FXML
    private Button audioGauche;
    @FXML
    private Button audioDroit;
    @FXML
    private Button ecouterQuestionBouton, ecouterReponseBouton;


    public CreationControleur(PaquetDeCartes paquet,GlobalControleur globalControleur){
        this.paquet=paquet;
        if(globalControleur.getCarte()==null){
            this.indice= paquet.taillePaquet()-1;
        }
        else{
            this.indice=globalControleur.findIndice(globalControleur.getPaquet(),globalControleur.getCarte());
        }
        this.globalControleur=globalControleur;
        paquet.ajouterObservateur(this);
    }


    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    @Override
    public void reagir() {
        if(globalControleur.getCarte()!=null){
            this.question.setText(globalControleur.getCarte().getQuestion());
            this.reponse.setText(globalControleur.getCarte().getReponse());
            ecouterQuestionBouton.setVisible(false);
            ecouterReponseBouton.setVisible(false);
            if ( !globalControleur.getCarte().getImageQuestion().equals("") ) {
                this.imageQuestion.setImage(new Image(globalControleur.getCarte().getImageQuestion()));
            }
            if ( !globalControleur.getCarte().getImageReponse().equals("") ) {
                this.imageReponse.setImage(new Image(globalControleur.getCarte().getImageReponse()));
            }
            if ( !globalControleur.getCarte().getAudioQuestion().equals("") ) {
                ecouterQuestionBouton.setVisible(true);
            }
            if ( !globalControleur.getCarte().getAudioReponse().equals("") ) {
                ecouterReponseBouton.setVisible(true);
            }
        }
        else {
            this.question.setPromptText("Ecrire une question");
            this.reponse.setPromptText("Ecrire une réponse");
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reagir();
        numCarte.setText("carte n°"+(this.globalControleur.findIndice(globalControleur.getPaquet(),globalControleur.getCarte())+1));
        nomPaquet.setText(this.globalControleur.getPaquet().getTitre());

        //icone ajouter image
        imageGauche.setBackground(new Background(new BackgroundImage(new javafx.scene.image.Image("utiles/appareil.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(imageGauche.getPrefWidth(), imageGauche.getPrefHeight(), false, false, false, false))));
        imageDroit.setBackground(new Background(new BackgroundImage(new javafx.scene.image.Image("utiles/appareil.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(imageDroit.getPrefWidth(), imageDroit.getPrefHeight(), false, false, false, false))));

        //icone ajouter audio
        audioGauche.setBackground(new Background(new BackgroundImage(new javafx.scene.image.Image("utiles/audio.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(imageGauche.getPrefWidth(), imageGauche.getPrefHeight(), false, false, false, false))));
        audioDroit.setBackground(new Background(new BackgroundImage(new javafx.scene.image.Image("utiles/audio.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(imageDroit.getPrefWidth(), imageDroit.getPrefHeight(), false, false, false, false))));

        int indicePrec = this.globalControleur.findIndice(globalControleur.getPaquet(),globalControleur.getCarte())-1;
        if(indicePrec<0){
            prec.setVisible(false);
        }
        int indiceSuiv = this.globalControleur.findIndice(globalControleur.getPaquet(),globalControleur.getCarte())+1;
        if(indiceSuiv>globalControleur.getPaquet().taillePaquet()-1){
            suiv.setText("Ajouter une carte");
        }
    }

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
        if (isCarteValide()) {
            majPaquetGlobalControleur(paquet);
            globalControleur.changeSceneVersGestion();
        }
    }
    @FXML
    public void supprimerCarte() throws Exception {
        new SupprimerCarteCommande(globalControleur).execute();
    }
    @FXML
    public void allerPrec() throws Exception{
        int indicePrec = this.globalControleur.findIndice(globalControleur.getPaquet(),globalControleur.getCarte())-1;
        if (indicePrec>=0) {
            if (isCarteValide()) {
                majCarteGlobalControleur(this.globalControleur.getPaquet().getCarte(indicePrec));
                globalControleur.changeSceneVersCreation();
            }
        }
    }
    @FXML
    public void allerSuiv() throws Exception{
        int indiceSuiv = this.globalControleur.findIndice(globalControleur.getPaquet(),globalControleur.getCarte())+1;
        if(indiceSuiv<this.globalControleur.getPaquet().taillePaquet()) {
            if (isCarteValide()) {
                majCarteGlobalControleur(this.globalControleur.getPaquet().getCarte(indiceSuiv));
                majPaquetGlobalControleur(paquet);
                globalControleur.changeSceneVersCreation();
            }
        }
        else{
            versCreation();
        }
    }
    @FXML
    public void versCreation() throws Exception{
        paquet.ajouterCarte(new Carte());
        carteActuelle = paquet.getCarte(paquet.taillePaquet()-1);
        if (isCarteValide()) {
            new MajPaquetGlobalCommande(globalControleur, paquet).execute();
            new MajCarteGlobalCommande(globalControleur, carteActuelle).execute();
            globalControleur.changeSceneVersCreation();
        }
    }
    @FXML
    public void validerCarte() throws Exception {
        if (isCarteValide()){
            new ValiderCarteCommande(globalControleur,carteActuelle).execute();
        }
    }

    public boolean isCarteValide() throws Exception {
        try {
            this.paquet.getCarte(this.indice).setQuestion(question.getText());
            this.paquet.getCarte(this.indice).setReponse(reponse.getText());
        }catch (Exception e){
            e.printStackTrace();
        }
        if ( ((this.paquet.getCarte(this.indice)).getReponse().equals("") && (this.paquet.getCarte(this.indice)).getImageReponse().equals("") && (this.paquet.getCarte(this.indice)).getAudioReponse().equals("") )
                || ((this.paquet.getCarte(this.indice)).getQuestion().equals("") && (this.paquet.getCarte(this.indice)).getImageQuestion().equals("")) && (this.paquet.getCarte(this.indice)).getAudioQuestion().equals("") ){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alerte");
            alert.setHeaderText("La carte n'a pas de question ou de réponse, elle va être supprimée");
            alert.setContentText("Êtes vous sur ?");

            Optional<ButtonType> result = alert.showAndWait();
            if ( result.get() == ButtonType.OK){
                new SupprimerCarteCommande(globalControleur).execute();
                carteActuelle = paquet.getCarte(paquet.taillePaquet()-1);
                return true;
            } else {
                return false;
            }
        }
        else {
            return true;
        }
    }

    public void majPaquetGlobalControleur(PaquetDeCartes paquetActuel) throws Exception {
        globalControleur.sauvegarder();
        this.globalControleur.setPaquet(paquetActuel);
    }
    public void majCarteGlobalControleur(Carte carteActuelle) throws Exception {
        globalControleur.sauvegarder();
        this.globalControleur.setCarte(carteActuelle);
    }

    @FXML
    public void ajouterImageQuestion() throws Exception {
        globalControleur.sauvegarderImageQuestion();
        reagir();
    }
    @FXML
    public void ajouterImageReponse() throws Exception {
        globalControleur.sauvegarderImageReponse();
        reagir();
    }
    @FXML
    public void ajouterAudioQuestion() throws Exception {
        globalControleur.sauvegarderAudioQuestion();
        reagir();
    }
    @FXML
    public void ajouterAudioReponse() throws Exception {
        globalControleur.sauvegarderAudioReponse();
        reagir();
    }
    @FXML
    public void ecouterQuestion() throws Exception {
        new JouerSonCommande(globalControleur, "question").execute();
    }
    @FXML
    public void ecouterReponse() throws Exception {
        new JouerSonCommande(globalControleur, "reponse").execute();
    }

    @FXML
    public void copierCarte() throws IOException {
        Alert choixPaquet = new Alert(Alert.AlertType.INFORMATION);
        choixPaquet.setTitle("Vers quels paquets copier ?");
        choixPaquet.setHeaderText(null);
        ObservableList<String> paquets = FXCollections.observableArrayList();
        for(int i=0;i<globalControleur.getPaquets().size();i++){
            paquets.add(globalControleur.getPaquets().get(i).getTitre());
        }
        ListView<String> listView= new ListView<>(paquets);
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                for(int i=0; i<paquets.size();i++){
                    if(t1.equals(paquets.get(i))){
                        Carte carteAColler = globalControleur.getCarte();
                        carteAColler.setStatsCarte(new StatsCarte());
                        globalControleur.getPaquets().get(i).ajouterCarte(carteAColler);
                    }
                }
            }
        });
        choixPaquet.getDialogPane().setContent(listView);
        choixPaquet.showAndWait();
    }
}
