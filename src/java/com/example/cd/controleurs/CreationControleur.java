package com.example.cd.controleurs;

import com.example.cd.Observateur;
import com.example.cd.SujetObserve;
import com.example.cd.commande.*;
import com.example.cd.modele.Carte;
import com.example.cd.modele.PaquetDeCartes;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;

import java.io.File;
import java.io.IOException;
import java.net.URL;
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
        numCarte.setText("Carte n°"+(this.globalControleur.findIndice(globalControleur.getPaquet(),globalControleur.getCarte())+1));
        nomPaquet.setText(this.globalControleur.getPaquet().getTitre());

        //icone ajouter image
        imageGauche.setPrefSize(50, 50);
        imageGauche.setMinSize(50,50);
        imageGauche.setBackground(new Background(new BackgroundImage(new javafx.scene.image.Image("utiles/appareil.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(imageGauche.getPrefWidth(), imageGauche.getPrefHeight(), false, false, false, false))));
        imageDroit.setPrefSize(50, 50);
        imageDroit.setMinSize(50,50);
        imageDroit.setBackground(new Background(new BackgroundImage(new javafx.scene.image.Image("utiles/appareil.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(imageDroit.getPrefWidth(), imageDroit.getPrefHeight(), false, false, false, false))));

        //icone ajouter audio
        audioGauche.setPrefSize(50, 50);
        audioGauche.setMinSize(50,50);
        audioGauche.setBackground(new Background(new BackgroundImage(new javafx.scene.image.Image("utiles/audio.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(imageGauche.getPrefWidth(), imageGauche.getPrefHeight(), false, false, false, false))));
        audioDroit.setPrefSize(50, 50);
        audioDroit.setMinSize(50,50);
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
        (new quitterApplicationCommande()).execute();
    }
    @FXML
    public void allerAccueil() throws Exception {
        majPaquetGlobalControleur(paquet);
        globalControleur.changeSceneVersAccueil();
    }
    @FXML
    public void voirPaquet() throws Exception {
        validerCarte();
        majPaquetGlobalControleur(paquet);
        globalControleur.changeSceneVersGestion();
    }
    @FXML
    public void supprimerCarte() throws Exception {
        int indicePrec = this.globalControleur.findIndice(globalControleur.getPaquet(),globalControleur.getCarte())-1;
        this.globalControleur.getPaquet().supprimerCarte(this.globalControleur.getCarte());
        majPaquetGlobalControleur(paquet);
        globalControleur.sauvegarder();
        if (indicePrec>=0) {
            majCarteGlobalControleur(this.globalControleur.getPaquet().getCarte(indicePrec));
            globalControleur.changeSceneVersCreation();
        } else {
            majPaquetGlobalControleur(paquet);
            globalControleur.changeSceneVersGestion();
        }
    }
    @FXML
    public void allerPrec() throws Exception{
        int indicePrec = this.globalControleur.findIndice(globalControleur.getPaquet(),globalControleur.getCarte())-1;
        if (indicePrec>=0) {
            validerCarte();
            majCarteGlobalControleur(this.globalControleur.getPaquet().getCarte(indicePrec));
            globalControleur.changeSceneVersCreation();
        }
    }

    @FXML
    public void allerSuiv() throws Exception{
        int indiceSuiv = this.globalControleur.findIndice(globalControleur.getPaquet(),globalControleur.getCarte())+1;
        if(indiceSuiv<this.globalControleur.getPaquet().taillePaquet()) {
            validerCarte();
            majCarteGlobalControleur(this.globalControleur.getPaquet().getCarte(indiceSuiv));
            majPaquetGlobalControleur(paquet);
            globalControleur.changeSceneVersCreation();
        }
        else{
            versCreation();
        }
    }
    @FXML
    public void versCreation() throws Exception{
        paquet.ajouterCarte(new Carte());
        carteActuelle = paquet.getCarte(paquet.taillePaquet()-1);
        validerCarte();
        majPaquetGlobalControleur(paquet);
        majCarteGlobalControleur(carteActuelle);
        globalControleur.changeSceneVersCreation();
    }
    @FXML
    public void validerCarte() throws Exception {
        try {
            this.paquet.getCarte(this.indice).setQuestion(question.getText());
            this.paquet.getCarte(this.indice).setReponse(reponse.getText());
        }catch (Exception e){
            e.printStackTrace();
        }
        if ( ((this.paquet.getCarte(this.indice)).getReponse().equals("") && (this.paquet.getCarte(this.indice)).getImageReponse().equals(""))
                || ((this.paquet.getCarte(this.indice)).getQuestion().equals("") && (this.paquet.getCarte(this.indice)).getImageQuestion().equals("")) ){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alerte");
            alert.setHeaderText("La carte n'a pas de question ou de réponse, elle va être supprimée");
            alert.setContentText("Êtes vous sur ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                supprimerCarte();
            } else {
                result.get();
            }
        }
        majPaquetGlobalControleur(paquet);
        globalControleur.changeSceneVersCreation();
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
    public void ajouterImageQuestion() throws IOException {
        globalControleur.sauvegarderImageQuestion();
        reagir();
    }
    @FXML
    public void ajouterImageReponse() throws IOException {
        globalControleur.sauvegarderImageReponse();
        reagir();
    }
    @FXML
    public void ajouterAudioQuestion() throws IOException {
        globalControleur.sauvegarderAudioQuestion();
        reagir();
    }
    @FXML
    public void ajouterAudioReponse() throws IOException {
        globalControleur.sauvegarderAudioReponse();
        reagir();
    }
    @FXML
    public void ecouterQuestion() {
        AudioClip player = new AudioClip(getClass().getResource(globalControleur.getCarte().getAudioQuestion()).toExternalForm());
        player.play();
    }
    @FXML
    public void ecouterReponse() {
        AudioClip player = new AudioClip(getClass().getResource(globalControleur.getCarte().getAudioReponse()).toExternalForm());
        player.play();
    }
}
