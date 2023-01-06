package com.example.cd.modele;

import com.example.cd.SujetObserve;
import com.example.cd.modele.apprentissage.ApprentissageStrategie;
import com.example.cd.modele.apprentissage.RandomApprentissage;
import com.example.cd.statistiques.EtatCarte;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.Objects;

public class PaquetDeCartes extends SujetObserve {

    @Expose
    private String titre;
    @Expose
    private String description;
    @Expose
    private ArrayList<Carte> cartes;
    private ApprentissageStrategie apprentissageStrategie;
    @Expose
    private int decompte;

    @Expose
    private String tag;

    public PaquetDeCartes(ArrayList<Carte> cartes, String titre, String description){
        this.cartes=cartes;
        this.description=description;
        this.titre=titre;
        this.apprentissageStrategie = new RandomApprentissage();
        this.decompte=3;
    }

    public PaquetDeCartes(String titre, String description){
        this.description=description;
        this.titre=titre;
        this.cartes = new ArrayList<>();
        this.apprentissageStrategie = new RandomApprentissage();
    }

    public PaquetDeCartes(){
        this.titre = " ";
        this.description ="";
        this.cartes =new ArrayList<>();
        this.apprentissageStrategie = new RandomApprentissage();
    }

    //getters
    public String getTitre() {
        return titre;
    }

    public String getDescription() {
        return description;
    }

    public ApprentissageStrategie getApprentissageStrategie() {
        return apprentissageStrategie;
    }

    public ArrayList<Carte> getCartes(){
        return cartes;
    }

    public Carte getCarte(int indice){ return cartes.get(indice);}

    public int getDecompte() {return decompte;}

    //setters
    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCartes(ArrayList<Carte> cartes){ this.cartes = cartes;}

    public void setApprentissageStrategie(ApprentissageStrategie apprentissageStrategie) {
        this.apprentissageStrategie = apprentissageStrategie;
    }

    public void setDecompte(int decompte) {this.decompte = decompte;}

    //methodes concernant la liste de cartes
    public void ajouterCarte(Carte carte){
        cartes.add(carte);
        notifierObservateur();
    }

    public void supprimerCarte(Carte carte){
        cartes.remove(carte);
    }

    public void supprimerCarteSelonIndex(int index){
        cartes.remove(index);
    }

    public int taillePaquet(){
        return this.cartes.size();
    }

    public ArrayList<Carte> getCartesARevoir(){
        ArrayList<Carte> cartesARevoir = new ArrayList<>();
        for(Carte carte : cartes){
            if(carte.getStatsCarte().getEtatCarte() == EtatCarte.ARevoir){
                cartesARevoir.add(carte);
            }
        }
        return cartesARevoir;
    }
    public ArrayList<Carte> getCartesNonVue(){
        ArrayList<Carte> cartesNonVue = new ArrayList<>();
        for(Carte carte : cartes){
            if(carte.getStatsCarte().getEtatCarte() == EtatCarte.NonVue){
                cartesNonVue.add(carte);
            }
        }
        return cartesNonVue;
    }

    public ArrayList<Carte> getCartesDebutApprentissage(){
        ArrayList<Carte> cartesDebutApprentissage = new ArrayList<>();
        for(Carte carte : cartes){
            if(carte.getStatsCarte().getEtatCarte() == EtatCarte.DebutApprentissage){
                cartesDebutApprentissage.add(carte);
            }
        }
        return cartesDebutApprentissage;
    }

    public ArrayList<Carte> getCartesFinApprentissage (){
        ArrayList<Carte> cartesBienMaitrisees = new ArrayList<>();
        for(Carte carte : cartes){
            if(carte.getStatsCarte().getEtatCarte() == EtatCarte.FinApprentissage){
                cartesBienMaitrisees.add(carte);
            }
        }
        return cartesBienMaitrisees;
    }

    public ArrayList<Carte> getCartesAcquiseParfaite(){
        ArrayList<Carte> cartesAcquiseParfaite = new ArrayList<>();
        for(Carte carte : cartes){
            if(carte.getStatsCarte().getEtatCarte() == EtatCarte.AcquiseParfaite){
                cartesAcquiseParfaite.add(carte);
            }
        }
        return cartesAcquiseParfaite;
    }

    public int getIndiceOfCartes(Carte carte){
        for(int i=0; i<cartes.size()-1; i++){
            if(carte==cartes.get(i)){
                return i;
            }
        }
        return -1;
    }

    public ArrayList<Carte> getCartesSansMedia(){
        ArrayList<Carte> cartesSansMedia = new ArrayList<>();
        for(Carte carte : cartes){
            if(Objects.equals(carte.getAudioQuestion(), "") && Objects.equals(carte.getImageQuestion(), "") ){
                cartesSansMedia.add(carte);
            }
        }
        return cartesSansMedia;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        if (tag == null) {
            return "";
        }
        return tag;
    }

    public String[] getlistTags(){
        return tag.split("/");
    }

    public int getEtatMoyenPaquet(){
        int etatMoyen = 0;
        for(Carte carte : cartes){
            etatMoyen += carte.getStatsCarte().getEtatCarte().ordinal();
        }
        return Math.round(etatMoyen)/cartes.size();
    }

}
