package com.example.cd.commande;

import com.example.cd.controleurs.GlobalControleur;
import com.example.cd.modele.PaquetDeCartes;
import javafx.scene.control.TextInputDialog;

public class ModifDescriptionCommande extends Commande {

    private GlobalControleur globalControleur;

    public ModifDescriptionCommande(GlobalControleur globalControleur, PaquetDeCartes paquet) {
        super(null, paquet, null);
        this.globalControleur = globalControleur;
    }

    @Override
    public void execute() throws Exception {
        dialogBoxNouvelleDescription();
        new MajPaquetGlobalCommande(globalControleur, paquetDeCartes).execute();
        new AllerGestionCommande(globalControleur, paquetDeCartes).execute();
    }
    public void dialogBoxNouvelleDescription() {
        TextInputDialog infoDescription = new TextInputDialog();
        infoDescription.setTitle("Modification Description");
        infoDescription.setHeaderText("Renseigne ta nouvelle description");
        infoDescription.showAndWait();
        paquetDeCartes.setDescription(infoDescription.getEditor().getText());
    }

}
