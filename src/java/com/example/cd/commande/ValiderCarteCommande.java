package com.example.cd.commande;

import com.example.cd.controleurs.GlobalControleur;
import com.example.cd.modele.Carte;
import javafx.scene.control.*;

import java.util.Optional;

public class ValiderCarteCommande extends Commande{

    GlobalControleur globalControleur;
    private int indice;
    private String question, reponse;


    public ValiderCarteCommande(GlobalControleur globalControleur, Carte carte, String question, String reponse) {
        super(null, globalControleur.getPaquet(), carte);
        this.globalControleur = globalControleur;
        this.question = question;
        this.reponse = reponse;
        this.indice = globalControleur.findIndice(globalControleur.getPaquet(), carte);
    }

    @Override
    public void execute() throws Exception {
        try {
            this.paquetDeCartes.getCarte(this.indice).setQuestion(question);
            this.paquetDeCartes.getCarte(this.indice).setReponse(reponse);
        }catch (Exception e){
            e.printStackTrace();
        }
        if ( ((this.paquetDeCartes.getCarte(this.indice)).getReponse().equals("") && (this.paquetDeCartes.getCarte(this.indice)).getImageReponse().equals(""))
                || ((this.paquetDeCartes.getCarte(this.indice)).getQuestion().equals("") && (this.paquetDeCartes.getCarte(this.indice)).getImageQuestion().equals("")) ){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alerte");
            alert.setHeaderText("La carte n'a pas de question ou de réponse, elle va être supprimée");
            alert.setContentText("Êtes vous sur ?");

            Optional<ButtonType> result = alert.showAndWait();
            if ( !(result.get() == ButtonType.OK) ) {
                new SupprimerCarteCommande(globalControleur).execute();
            }
        }
        new MajPaquetGlobalCommande(globalControleur, paquetDeCartes).execute();
        globalControleur.changeSceneVersCreation();

    }
}
