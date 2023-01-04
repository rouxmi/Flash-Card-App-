package com.example.cd.controleurs;

import com.example.cd.Observateur;
import com.example.cd.SujetObserve;
import com.example.cd.commande.*;
import com.example.cd.modele.Carte;
import com.example.cd.modele.PaquetDeCartes;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class CreationControleur extends SujetObserve implements Initializable, Observateur {
    private PaquetDeCartes paquet;

    private GlobalControleur globalControleur;
    private int indice;

    @FXML
    private TextArea question;
    @FXML
    private TextArea reponse;

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
    public void validerCarte() throws Exception {
        try{
            this.paquet.getCarte(this.indice).setQuestion(question.getText());
            this.paquet.getCarte(this.indice).setReponse(reponse.getText());
            System.out.println(this.indice);
        }catch (Exception e){
            e.printStackTrace();
        }
        majPaquetGlobalControleur(paquet);
        globalControleur.changeSceneVersCreation();
    }

    public void majPaquetGlobalControleur(PaquetDeCartes paquetActuel) {
        this.globalControleur.setPaquet(paquetActuel);
    }
    public void majCarteGlobalControleur(Carte carteActuelle) {
        this.globalControleur.setCarte(carteActuelle);
    }

}
