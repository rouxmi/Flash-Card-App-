package com.example.cd.modele;

import com.example.cd.SujetObserve;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;

public class PaquetDeCartes extends SujetObserve {

    @Expose
    private String titre;
    @Expose
    private String auteur;
    @Expose
    private ArrayList<Carte> cartes;

    public PaquetDeCartes(ArrayList<Carte> cartes, String titre, String auteur){
        this.cartes=cartes;
        this.auteur=auteur;
        this.titre=titre;
    }

    public PaquetDeCartes(String titre, String auteur){
        this.auteur=auteur;
        this.titre=titre;
        this.cartes = new ArrayList<>();
    }

    public PaquetDeCartes(){
        this.titre = "";
        this.auteur ="";
        this.cartes =new ArrayList<>();
    }



    //getters
    public String getTitre() {
        return titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public ArrayList<Carte> getCartes(){
        return cartes;
    }

    public Carte getCarte(int indice){ return cartes.get(indice);}

    //setters
    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public void setCartes(ArrayList<Carte> cartes){ this.cartes = cartes;}

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


}
