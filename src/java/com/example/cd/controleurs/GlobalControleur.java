package com.example.cd.controleurs;

import com.example.cd.Observateur;
import com.example.cd.commande.SceneChanger;
import com.example.cd.modele.PaquetDeCartes;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GlobalControleur{
    private ArrayList<PaquetDeCartes> paquets;
    private AccueilControleur accueil;
    private CreationControleur creation;
    private EntrainementControleur entrainement;
    private GestionControleur gestion;

    public GlobalControleur() throws IOException {
        this.paquets = new ArrayList<PaquetDeCartes>();
        this.accueil = new AccueilControleur(paquets);
        new SceneChanger(paquets, null, null).changeSceneAcceuil();
    }

}
