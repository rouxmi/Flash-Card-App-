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

    private  ChangeurScene changeurScene;

    private  AccueilControleur accueil;
    private CreationControleur creation;
    private EntrainementControleur entrainement;
    private GestionControleur gestion;

    public GlobalControleur() throws IOException {
        this.paquets = Sauvegarde.chargerTousPaquets();
        /*ArrayList<PaquetDeCartes> paquets = new ArrayList<PaquetDeCartes>();
        for (int i=0;i<10;i++){
            PaquetDeCartes paquet = new PaquetDeCartes("paquet"+i,"auteur"+i);
            for (int j=0;j<10;j++){
                paquet.ajouterCarte(new Carte("question"+j,"reponse"+j));
            }
            paquets.add(paquet);
        }
        Sauvegarde.sauvegardeToutPaquets(paquets);*/
         this.accueil = new AccueilControleur(paquets,this);
         this.accueil.ajouterObservateur(this);
        InitialisationChangeurScene();

    }

    public void InitialisationChangeurScene() throws IOException {
        changeurScene=new ChangeurScene(paquets, paquet, carte,this);
        changeurScene.changeSceneAcceuil();
        changeurScene.execute();
    }

    public  void changeSceneVersCreation() throws Exception {
        changeurScene.changeSceneCreation();
        changeurScene.execute();
    }


    public  void changeSceneVersEntrainement() throws Exception {
        changeurScene.changeSceneEntrainement();
        changeurScene.execute();
    }

    public void changeSceneVersGestion() throws Exception {
        changeurScene.changeSceneGestion();
        changeurScene.execute();
    }

    public void changeSceneVersAccueil() throws Exception {
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

    public void setCarte(Carte carte) {
        this.carte = carte;
    }

    public void setPaquet(PaquetDeCartes paquet) {
        this.paquet = paquet;
    }

    public void setPaquetActuelAccueil(){
        changeurScene.setPaquet(accueil.getPaquetActuel());

    }

    public void setAccueil(AccueilControleur accueil) {
        this.accueil = accueil;
    }

    public CreationControleur getCreation() {
        return creation;
    }

    public void setCreation(CreationControleur creation) {
        this.creation = creation;
    }

    public EntrainementControleur getEntrainement() {
        return entrainement;
    }

    public void setEntrainement(EntrainementControleur entrainement) {
        this.entrainement = entrainement;
    }

    public void setGestion(GestionControleur gestion) {
        this.gestion = gestion;
    }
}
