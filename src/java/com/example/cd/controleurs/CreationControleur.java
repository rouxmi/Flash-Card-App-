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
    private Label numCarte;

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

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(globalControleur.getCarte()!=null){
            this.question.setText(globalControleur.getCarte().getQuestion());
            this.reponse.setText(globalControleur.getCarte().getReponse());
        }
        else {
            this.question.setPromptText("Ecrire une question");
            this.reponse.setPromptText("Ecrire une réponse");
        }
        numCarte.setText("Carte n°"+this.globalControleur.findIndice(globalControleur.getPaquet(),globalControleur.getCarte()));
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
        if ( (this.paquet.getCarte(this.indice)).getReponse().equals("") || (this.paquet.getCarte(this.indice)).getQuestion().equals("") ) {
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
    public void ajouterImage() throws IOException {
        globalControleur.sauvegarderImage();
        System.out.println(globalControleur.getCarte().getMediaQuestion());
        Image image = new Image(globalControleur.getCarte().getMediaQuestion());
        imageQuestion.setImage(image);
    }
}
