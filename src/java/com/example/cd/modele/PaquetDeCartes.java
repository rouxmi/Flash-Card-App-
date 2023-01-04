package com.example.cd.modele;

import com.example.cd.SujetObserve;
import com.example.cd.modele.apprentissage.ApprentissageStrategie;
import com.example.cd.modele.apprentissage.RandomApprentissage;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;

public class PaquetDeCartes extends SujetObserve {

    @Expose
    private String titre;
    @Expose
    private String description;
    @Expose
    private ArrayList<Carte> cartes;
    private ApprentissageStrategie apprentissageStrategie;

    public PaquetDeCartes(ArrayList<Carte> cartes, String titre, String description){
        this.cartes=cartes;
        this.description=description;
        this.titre=titre;
        this.apprentissageStrategie = new RandomApprentissage();
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
