package com.example.cd.commande;

import com.example.cd.controleurs.GlobalControleur;
import com.example.cd.modele.PaquetDeCartes;
import javafx.scene.control.Alert;

public class AllerMiniJeuCommande extends Commande {

    private GlobalControleur globalControleur;

    public AllerMiniJeuCommande(GlobalControleur globalControleur, PaquetDeCartes paquet) {
        super(null, paquet, null);
        this.globalControleur = globalControleur;
    }

    @Override
    public void execute() throws Exception {
        if(paquetDeCartes.getCartesSansMedia().size()>=8){
            new MajPaquetGlobalCommande(globalControleur, paquetDeCartes).execute();
            globalControleur.changeSceneVersMiniJeu();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Il n'y a pas assez de cartes sans média");
            alert.setContentText("Il faut au moins 8 cartes sans média pour jouer au mini jeu");
            alert.showAndWait();
        }
    }
}
