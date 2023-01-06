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

import java.awt.desktop.SystemSleepEvent;
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
    private Button audioGauche;
    @FXML
    private Button ecouterQuestionBouton;


    public CreationControleur(PaquetDeCartes paquet,GlobalControleur globalControleur){
        this.paquet=paquet;
        if(globalControleur.getCarte()==null){
            this.indice= paquet.taillePaquet()-1;
        }
        else{
            this.indice=globalControleur.findIndice(globalControleur.getPaquet(),globalControleur.getCarte());
        }
        this.globalControleur=globalControleur;
    }


    @Override
    public void reagir() {
        if(globalControleur.getCarte()!=null){
            this.question.setText(globalControleur.getCarte().getQuestion());
            this.reponse.setText(globalControleur.getCarte().getReponse());
            ecouterQuestionBouton.setVisible(false);
            if ( !globalControleur.getCarte().getImageQuestion().equals("") ) {
                this.imageQuestion.setImage(new Image(globalControleur.getCarte().getImageQuestion()));
            }
            if ( !globalControleur.getCarte().getAudioQuestion().equals("") ) {
                ecouterQuestionBouton.setVisible(true);
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

        //icone ajouter audio
        audioGauche.setBackground(new Background(new BackgroundImage(new javafx.scene.image.Image("utiles/audio.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(imageGauche.getPrefWidth(), imageGauche.getPrefHeight(), false, false, false, false))));

        int indicePrec = this.globalControleur.findIndice(globalControleur.getPaquet(),globalControleur.getCarte())-1;
        if(indicePrec<0){
            prec.setVisible(false);
        }
        int indiceSuiv = this.globalControleur.findIndice(globalControleur.getPaquet(),globalControleur.getCarte())+1;
        if(indiceSuiv>globalControleur.getPaquet().taillePaquet()-1){
            suiv.setText("Ajouter une carte");
        }
    }

    public boolean isCarteValide() throws Exception {
        try {
            this.paquet.getCarte(this.indice).setQuestion(question.getText());
            this.paquet.getCarte(this.indice).setReponse(reponse.getText());
        }catch (Exception e){
            e.printStackTrace();
        }
        if ( ((this.paquet.getCarte(this.indice)).getReponse().equals("")  )
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

    // FXML Bouton functions
    @FXML
    public void quitterAppli() {
        (new QuitterApplicationCommande()).execute();
    }
    @FXML
    public void allerAccueil() throws Exception {
        new AllerAccueilCommande(globalControleur, paquet).execute();
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
                new AllerCreationCommande(globalControleur, paquet, paquet.getCarte(indicePrec)).execute();
            }
        }
    }
    @FXML
    public void voirPaquet() throws Exception {
        if (isCarteValide()) {
            new AllerGestionCommande(globalControleur, paquet).execute();
        }
    }
    @FXML
    public void allerSuiv() throws Exception{
        int indiceSuiv = this.globalControleur.findIndice(globalControleur.getPaquet(),globalControleur.getCarte())+1;
        if (indiceSuiv < this.globalControleur.getPaquet().taillePaquet()) {
            if (isCarteValide()) {
                new MajCarteGlobalCommande(globalControleur, carteActuelle).execute();
                new AllerCreationCommande(globalControleur, paquet, paquet.getCarte(indiceSuiv)).execute();
            }
        }
        else{
            if (isCarteValide()) {
                new MajCarteGlobalCommande(globalControleur, carteActuelle).execute();
                new AllerCreationCommande(globalControleur, paquet, null).execute();
            }
        }
    }
    @FXML
    public void copierCarte() throws Exception {
        new CopierCarteCommande(globalControleur).execute();
    }
    @FXML
    public void ajouterCarte() throws Exception{
        if (isCarteValide()) {
            new AjouterCarteCommande(globalControleur, globalControleur.getCarte(), question.getText(), reponse.getText()).execute();
        }
    }
    @FXML
    public void ecouterQuestion() throws Exception {
        new JouerSonCommande(globalControleur, globalControleur.getCarte(), "question").execute();
    }
    @FXML
    public void ecouterReponse() throws Exception {
        new JouerSonCommande(globalControleur, globalControleur.getCarte(), "reponse").execute();
    }

    // try to command pattern this
    @FXML
    public void ajouterImageQuestion() throws Exception {
        globalControleur.sauvegarderImageQuestion();
        reagir();
    }

    @FXML
    public void ajouterAudioQuestion() throws Exception {
        globalControleur.sauvegarderAudioQuestion();
        reagir();
    }

}
