package com.example.cd.commande;

import com.example.cd.controleurs.GlobalControleur;
import com.example.cd.modele.Carte;
import com.example.cd.modele.PaquetDeCartes;

public class AjouterCarteCommande extends Commande {

    private GlobalControleur globalControleur;
    private String question, reponse;

    public AjouterCarteCommande(GlobalControleur globalControleur, Carte carte, String question, String reponse) {
        super(null, null, carte);
        this.globalControleur = globalControleur;
        this.question = question;
        this.reponse = reponse;
    }

    @Override
    public void execute() throws Exception {
        carte.setQuestion(question);
        carte.setReponse(reponse);
        paquetDeCartes = globalControleur.getPaquet();
        new MajCarteGlobalCommande(globalControleur, carte).execute();
        new MajPaquetGlobalCommande(globalControleur, paquetDeCartes).execute();
        new AllerCreationCommande(globalControleur, paquetDeCartes, carte).execute();
    }
}
