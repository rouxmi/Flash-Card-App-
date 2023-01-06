package com.example.cd.controleurs;

import com.example.cd.Ankiloader;
import com.example.cd.Observateur;
import com.example.cd.Sauvegarde;
import com.example.cd.commande.ChangeurScene;
import com.example.cd.modele.Carte;
import com.example.cd.modele.PaquetDeCartes;

import java.io.IOException;
import java.util.ArrayList;

public class GlobalControleur extends Sauvegarde implements Observateur {
    private ArrayList<PaquetDeCartes> paquets;

    private PaquetDeCartes paquet=null;
    private Carte carte=null;

    private  ChangeurScene changeurScene;

    private  AccueilControleur accueil;
    private CreationControleur creation;
    private EntrainementControleur entrainement;
    private GestionControleur gestion;
    private MiniJeuControleur minijeu;
    public GlobalControleur() throws Exception {
        this.paquets = Sauvegarde.chargerTousPaquets();
        /*ArrayList<PaquetDeCartes> paquets = new ArrayList<PaquetDeCartes>();
        for (int i=0;i<2;i++){
            PaquetDeCartes paquet = new PaquetDeCartes("paquet"+i,"auteur"+i);
            for (int j=0;j<10;j++){
                paquet.ajouterCarte(new Carte("question"+j,"reponse"+j));
            }
            paquets.add(paquet);
        }
        Sauvegarde.sauvegardeTousPaquets(paquets);
        this.paquets=paquets;*/
         this.accueil = new AccueilControleur(paquets,this);
         this.accueil.ajouterObservateur(this);
        InitialisationChangeurScene();

    }

    public void sauvegarder() throws Exception {
         Sauvegarde.sauvegardeTousPaquets(paquets);
    }
    public void importerPaquets() throws Exception {
        paquets.addAll(Sauvegarde.chargerPaquets()) ;
        sauvegarder();
    }
    public void sauvegarder1paquet() throws IOException{
        Sauvegarde.sauvegarde(paquet);
    }
    public void sauvegarderImageQuestion() throws Exception {
        String nouvelleImagePath = Sauvegarde.choisirFichierImage();
        carte.setImageQuestion(nouvelleImagePath);
        sauvegarder();
    }

    public void sauvegarderAudioQuestion() throws Exception {
        String nouvelleVideoPath = Sauvegarde.choisirFichierAudio();
        carte.setAudioQuestion(nouvelleVideoPath);
        sauvegarder();
    }


    public void InitialisationChangeurScene() throws IOException {
        changeurScene=new ChangeurScene(paquets, paquet, carte,this);
        changeurScene.changeSceneAcceuil();
        changeurScene.execute();
    }

    public  void changeSceneVersCreation() throws Exception {
        changeurScene.setPaquet(paquet);
        changeurScene.setCarte(carte);
        changeurScene.changeSceneCreation();
        changeurScene.execute();
    }


    public  void changeSceneVersEntrainement(String typeEntrainement) throws Exception {
        changeurScene.setPaquet(paquet);
        changeurScene.changeSceneEntrainement(typeEntrainement);
        changeurScene.execute();
    }

    public void changeSceneVersGestion() throws Exception {
        changeurScene.setPaquet(paquet);
        changeurScene.changeSceneGestion();
        changeurScene.execute();
    }

    public void changeSceneVersAccueil() throws Exception {
        changeurScene.setPaquet(paquet);
        changeurScene.changeSceneAcceuil();
        changeurScene.execute();
    }

    public void changeSceneVersMiniJeu() throws Exception {
        changeurScene.setPaquet(paquet);
        changeurScene.changeSceneMiniJeu();
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

    public Carte getCarte() {return carte;}

    public PaquetDeCartes getPaquet() {return paquet;}

    public void setCarte(Carte carte) {
        this.carte = carte;
    }

    public void setPaquet(PaquetDeCartes paquet) {
        this.paquet = paquet;
    }
    public ArrayList<PaquetDeCartes> getPaquets() {
        return paquets;
    }
    public void setPaquetActuelAccueil(){
        changeurScene.setPaquet(accueil.getPaquetActuel());
    }

    public void setAccueil(AccueilControleur accueil) {
        this.accueil = accueil;
    }

    public void setMiniJeu(MiniJeuControleur minijeu) {
        this.minijeu = minijeu;
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

    public int findIndice(PaquetDeCartes paquet, Carte carte){
        if (paquet !=null){
            for(int i=0;i< paquet.taillePaquet();i++){
                if(paquet.getCarte(i)==carte){
                    return i;
                }
            }

        }
        return 0;
    }

    public void supprimerPaquet(PaquetDeCartes paquet) {
        paquets.remove(paquet);
    }
    public void supprimerCarte(Carte carteActuelle) {
        paquet.supprimerCarte(carteActuelle);
    }

}
