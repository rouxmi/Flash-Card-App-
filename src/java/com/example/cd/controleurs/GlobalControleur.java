package com.example.cd.controleurs;

import com.example.cd.Observateur;
import com.example.cd.Sauvegarde;
import com.example.cd.commande.ChangeurScene;
import com.example.cd.modele.Carte;
import com.example.cd.modele.PaquetDeCartes;

import java.io.IOException;
import java.util.ArrayList;

public class GlobalControleur implements Observateur {
    private ArrayList<PaquetDeCartes> paquets;

    private PaquetDeCartes paquet=null;
    private Carte carte=null;

    private static ChangeurScene changeurScene;
    private AccueilControleur accueil;
    private CreationControleur creation;
    private EntrainementControleur entrainement;
    private GestionControleur gestion;

    public GlobalControleur() throws IOException {
        this.paquets = Sauvegarde.chargerTousPaquets();
        for (PaquetDeCartes paquet : paquets) {
            System.out.println(paquet.getTitre());
        }
        /*ArrayList<PaquetDeCartes> paquets = new ArrayList<PaquetDeCartes>();
        for (int i=0;i<10;i++){
            PaquetDeCartes paquet = new PaquetDeCartes("paquet"+i,"auteur"+i);
            for (int j=0;j<10;j++){
                paquet.ajouterCarte(new Carte("question"+j,"reponse"+j));
            }
            paquets.add(paquet);
        }
        Sauvegarde.sauvegardeToutPaquets(paquets);*/
         this.accueil = new AccueilControleur(paquets);
         this.accueil.ajouterObservateur(this);
        InitialisationChangeurScene();

    }

    public void InitialisationChangeurScene() throws IOException {
        changeurScene=new ChangeurScene(paquets, null, null);
        changeurScene.changeSceneAcceuil();
        changeurScene.execute();
    }

    public static void changeSceneVersCreation() throws Exception {
        changeurScene.changeSceneCreation();
        changeurScene.execute();
    }

    public static void changeSceneVersEntrainement() throws Exception {
        changeurScene.changeSceneEntrainement();
        changeurScene.execute();
    }

    public static void changeSceneVersGestion() throws Exception {

        changeurScene.changeSceneGestion();
        changeurScene.execute();
    }

    public static void changeSceneVersAccueil() throws Exception {
        changeurScene.changeSceneAcceuil();
        changeurScene.execute();
    }

    @Override
    public void reagir() {

    }

    public GestionControleur getGestion(){
        return this.gestion;
    }
    public AccueilControleur getAccueil(){
        return this.accueil;
    }
}
