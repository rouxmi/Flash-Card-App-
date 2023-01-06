package com.example.cd.commande;

import com.example.cd.controleurs.GlobalControleur;
import com.example.cd.modele.PaquetDeCartes;
import javafx.scene.control.TextInputDialog;

public class ajouterPaquetCommande extends Commande {

    private GlobalControleur globalControleur;

    public ajouterPaquetCommande(GlobalControleur globalControleur) {
        super(globalControleur.getPaquets(), null, null);
        this.globalControleur = globalControleur;
    }

    @Override
    public void execute() throws Exception {
        paquets.add(new PaquetDeCartes());
        paquetDeCartes = paquets.get(paquets.size()-1);
        new majPaquetGlobalCommande(this.globalControleur, paquetDeCartes).execute();
     //   majPaquetGlobalControleur(paquetActuel);
        dialogBoxNouveauPaquet();
        if ( !paquetDeCartes.getTitre().equals("") ) {
            dialogBoxNouvelleDescription();
            globalControleur.changeSceneVersGestion();
        } else {
            globalControleur.supprimerPaquet(paquetDeCartes);
            new majPaquetGlobalCommande(this.globalControleur, null).execute();
        }
    }

    public void dialogBoxNouveauPaquet() {
        TextInputDialog infoTitre = new TextInputDialog();
        infoTitre.setTitle("Création nouveau paquet");
        infoTitre.setHeaderText("Renseigne le titre");
        infoTitre.showAndWait();
        paquetDeCartes.setTitre(infoTitre.getEditor().getText());
    }
    public void dialogBoxNouvelleDescription(){
        TextInputDialog infoDescription = new TextInputDialog();
        infoDescription.setTitle("Création nouveau paquet");
        infoDescription.setHeaderText("Renseigne la description");
        infoDescription.showAndWait();
        paquetDeCartes.setDescription(infoDescription.getEditor().getText());
    }


}
