package com.example.cd.commande;

import com.example.cd.controleurs.GlobalControleur;
import com.example.cd.modele.PaquetDeCartes;
import javafx.scene.control.TextInputDialog;

public class ModifTitreCommande extends Commande {

    private GlobalControleur globalControleur;

    public ModifTitreCommande(GlobalControleur globalControleur, PaquetDeCartes paquet) {
        super(null, paquet, null);
        this.globalControleur = globalControleur;
    }

    @Override
    public void execute() throws Exception {
        dialogBoxNouveauTitre();
        new MajPaquetGlobalCommande(globalControleur, paquetDeCartes).execute();
        new AllerGestionCommande(globalControleur, paquetDeCartes).execute();
    }
    public void dialogBoxNouveauTitre() {
        TextInputDialog infoTitre = new TextInputDialog();
        infoTitre.setTitle("Modification Titre");
        infoTitre.setHeaderText("Renseigne ton nouveau titre");
        infoTitre.showAndWait();
        paquetDeCartes.setTitre(infoTitre.getEditor().getText());
    }

}
